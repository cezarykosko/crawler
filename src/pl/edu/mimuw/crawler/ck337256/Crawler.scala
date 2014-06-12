package pl.edu.mimuw.crawler.ck337256
import scala.collection.mutable.Queue
import scala.collection.mutable.Set
import org.jsoup._
import org.jsoup.nodes._
import org.jsoup.select._
import org.jsoup.safety._
import java.io.IOException

/**
 * A crawler starting its work at the specified path
 *
 * @author Cezary Kosko
 * @constructor creates a Crawler with a specified starting point
 * @param url starting point
 */
class Crawler(path: String) {

  /**
   * Crawls from the starting point until the specified depth is reached.
   * At each point uses the PageAnalyzer to determine its action.
   *
   * @param analyzer contains two options of behavior at each point
   * (depending on its own condition function) and function deciding
   * whether a side should be added to the processing queue
   *
   * @param depthLimit the crawling descent limit
   */
  def crawl(analyzer: PageAnalyzer, depthLimit: Int): Unit = {
    val startPage = Page(path);
    var queue = ProcessingQueue(Queue[(Page, Int)]((startPage, 0)),
      Set[String]());
    analyzer.conduct(startPage)
    while (!queue.isEmpty) {
      val temp = queue.pop;
      val tempLinks = temp._1.getLinks
      for (link <- tempLinks) {
        analyzer.conduct(link);
        if (analyzer.toCrawl(link) && (temp._2 <= depthLimit)) {
          queue.add(link, 1 + temp._2);
        }
      }
    }
  }

  /**
   * as crawl(PageAnalyzer, Int) => Unit, but with descent limit
   * set to Int.MaxValue
   */
  def crawl(analyzer: PageAnalyzer): Unit = crawl(analyzer, Int.MaxValue)

  /**
   * Returns a list of all links in a website
   */
  def getLinks: List[String] =
    Page(path).getLinks.map(_.getPath)
}

/**
 * Factory for Crawler instances
 */
object Crawler {
  /**
   * Creates a Crawler with its starting point in specified path
   */
  def apply(path: String): Crawler = new Crawler(path)
}