package pl.edu.mimuw.crawler.ck337256
import org.jsoup._
import org.jsoup.nodes._
import org.jsoup.select._
import org.jsoup.safety._
import java.net.URL
import java.net.MalformedURLException
import java.io.File
import java.io.IOException
import java.io.FileNotFoundException
import org.apache.commons.validator.UrlValidator

/**
 * Class representing a crawlable page.
 *
 * @author Cezary Kosko
 * @constructor create a Page with specified paths
 * @param path the full path of a file
 * @param relativePath the file's relative path
 */
class Page(path: String, relativePath: String) {

  /**
   * Creates a Path with only full path specified
   *
   * @param path full path of a page
   */
  def this(path: String) = {
    this(path, path)
  }

  /**
   * Checks whether a page has had a relative path specified
   */
  def isRelative: Boolean = path != relativePath

  /**
   * Returns the full path of a page
   */
  def getPath: String = path

  /**
   * Returns the page's host (if it's a local page or its URL
   * is malformed, it returns an empty path)
   */
  def getHost: String =
    try {
      (new URL(path)).getHost()
    } catch {
      case e: MalformedURLException => "http://"
    }

  /**
   * checks if an URL is valid using org.apache.commons validator
   *
   * @param path the checked URL
   */
  def validUrl(path: String): Boolean = {
    val urlValidator = new UrlValidator(Array("http", "https"))
    urlValidator.isValid(path)
  }

  /**
   * checks if a file path is valid - checks if such a file actually
   * exists
   *
   * @param path the checked path
   */
  def validFile(path: String): Boolean = {
    val file = new File(path)
    file.exists()
  }

  /**
   * Returns the page's JSoup DOC file
   */
  def getDoc: Document = {
    if (validUrl(path)) Jsoup.connect(path).get()
    else {
      val file = new File(path)
      require(file.exists())
      Jsoup.parse(file, "UTF-8")
    }
  }

  /**
   * Creates a Page of a file using given path and current path.
   * If it is impossible, returns null.
   *
   * @param pathNow current path
   * @param relPath given path (received using _.attr("href") method)
   */
  def makeFile(pathNow: String, relPath: String): Page = {
    if (validFile(relPath)) {
      Page(relPath, relPath)
    } else {
      val temp = pathNow + relPath
      if (validFile(temp)) {
        Page(temp, relPath)
      } else null
    }
  }

  /**
   * Creates a Page using given current path, file's absolute and relative paths,
   * be it a website or a local file. If it's impossible, returns null.
   *
   * @param pathNow the current path
   * @param relPath file's relative path
   * @param absPath file's absolute path
   */
  def make(pathNow: String, relPath: String, absPath: String): Page = {
    if (validUrl(relPath) || validUrl(absPath)) {
      if (validUrl(relPath)) {
        Page(relPath, relPath)
      } else {
        Page(absPath, relPath)
      }
    } else {
      makeFile(pathNow, relPath)
    }
  }

  /**
   * Returns all the links the Page contains. If an error occurs,
   * returns an empty list
   */
  def getLinks: List[Page] =
    try {
      val urlValidator = new UrlValidator(Array("http", "https"))
      val elems = getDoc.select("a");
      var parsedLinks = List[Page]();
      while (!elems.isEmpty()) {
        val temp = elems.remove(0)
        val tempRel = temp.attr("href")
        val tempAbs = temp.attr("abs:href")
        val elem = make(this.getPath, tempRel, tempAbs)
        if (elem == null) {
          parsedLinks
        } else {
          parsedLinks = elem :: parsedLinks;
        }
      }
      parsedLinks
    } catch {
      case _: Throwable => List[Page]()
    }
}

/**
 * Factory for Page instances
 */
object Page {

  /**
   * Creates a Page given its absolute and relative path
   *
   * @param path the absolute path
   * @param relPath the current relative path
   */
  def apply(path: String, relPath: String): Page = new Page(path, relPath)

  /**
   * Creates a Page given its absolute path
   *
   * @param path the absolute path
   */
  def apply(path: String): Page = new Page(path)
}