package demos
import pl.edu.mimuw.crawler.ck337256._
import scala.collection.mutable.Map
import java.net.URL

/**
 * An app demonstrating the crawler library. Given a path, it crawls
 * the page and linked pages on the same host and returns a list of other hosts
 * linked with the number of links.
 *
 * @author Cezary Kosko
 */
object Demo1 extends App {
  /**
   * checks whether a page has the same host as an URL
   */
  def isLocal(url: String)(page: Page): Boolean =
    ((new URL(url)).getHost() == page.getHost)

  /**
   * inputs a link information into a map
   */
  def mapInput(map: Map[String, Int])(page: Page): Unit = {
    val host = page.getHost
    map put (host, 1 + (map getOrElse (host, 0)))
  }

  /**
   * main reads the path from the stdin and performs the crawling.
   * Information about the hosts is stored in a Map[String, Int]
   * and before output is being sorted by occurence and alphabetical
   * order.
   * 
   * @param args standard input
   */
  override def main(args: Array[String]): Unit = {
    val startURL = args(0);
    var stateMap = Map[String, Int]()
    val analyzer = PageAnalyzer(isLocal(startURL), (_ => Unit),
      mapInput(stateMap), isLocal(startURL));
    val crawler = Crawler(startURL);
    crawler.crawl(analyzer, 700);
    val output = stateMap.toArray.sortWith((a, b) =>
      if (a._2 != b._2) a._2 > b._2 else a._1 < b._1)
    for (site <- output) {
      println(site._1 + " " + site._2)
    }
  }
}