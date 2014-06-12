package demos
import pl.edu.mimuw.crawler.ck337256._
import scala.collection.mutable.Map

/**
 * An app demonstrating the crawler library. Given a file path crawls over
 * local links and returns the number of visited pages.
 *
 * @author Cezary Kosko
 */
object Demo2 extends App {
  /**
   * main performs the crawling. It visits only pages with relative
   * paths and adds a 1 to the counter every time it encounters a page
   */
  override def main(args: Array[String]): Unit = {
    val path = args(0);
    val depth = args(1).toInt;
    var counter = 0;
    def check(page: Page): Boolean = {
      counter += 1;
      true
    }
    val analyzer = PageAnalyzer(check, (_ => Unit), (_ => Unit), _.isRelative)
    val crawler = Crawler(path)
    crawler.crawl(analyzer, depth)
    println(counter)
  }
}