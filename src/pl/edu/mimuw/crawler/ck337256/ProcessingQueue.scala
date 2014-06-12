package pl.edu.mimuw.crawler.ck337256
import scala.collection.mutable.Queue
import scala.collection.mutable.Set

/**
 * Class responsible for outfiltering visited sites and providing unvisited
 * ones to the crawler (with their depth as well)
 *
 * @author Cezary Kosko
 * @constructor returns an instance of the queue - the user is able to prevent
 * the crawler from visiting some pages by placing them in the visited Set
 * or make crawler visit them by placing them in the toVisit Queue
 * @param toVisit Queue containing sites to be visited
 * @param visited Set containing already visited sites
 */
class ProcessingQueue(toVisit: Queue[(Page, Int)], visited: Set[String]) {
  /**
   * adds a page to the queue (if it has not yet been processed)
   *
   * @param page the page to be visited
   * @param depth the page's depth
   */
  def add(page: Page, depth: Int): Unit =
    if (!(visited contains page.getPath)) {
      toVisit += ((page, depth));
    }

  /**
   * returns the first element of the toVisit Queue and marks it as visited
   */
  def pop: (Page, Int) = {
    val temp = toVisit.dequeue;
    (visited add temp._1.getPath)
    temp
  }

  /**
   * checks whether there are no pages to visit
   */
  def isEmpty: Boolean = toVisit.isEmpty
}

/**
 * Factory of ProcessingQueues
 */
object ProcessingQueue {
  /**
   * returns a ProcessedQueue containing a given Queue and Set
   *
   * @param toVisit queue of sites (and their depths) to be visited
   * @param visited the set of visited sites
   */
  def apply(toVisit: Queue[(Page, Int)], visited: Set[String]): ProcessingQueue =
    new ProcessingQueue(toVisit, visited)
}