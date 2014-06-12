package pl.edu.mimuw.crawler.ck337256

/**
 * Class containing a basic decisive engine of a crawler. Allows performing
 * two kinds of operations on sides and selective crawling
 *
 * @author Cezary Kosko
 * @constructor returns a decisive engine of a crawler
 * @param condition decides whether to use the first or second option
 * of behavior
 * @param ifTrue first option of behavior
 * @param ifFalse second option of behavior
 * @param crawlCondition selector of crawled sites
 */
class PageAnalyzer(condition: (Page => Boolean),
  ifTrue: (Page => Unit), ifFalse: (Page => Unit),
  crawlCondition: (Page => Boolean)) {
  /**
   * decides which action to perform on a page and does it
   *
   * @param page the page currently analyzed by a crawler
   */
  def conduct(page: Page): Unit =
    if (condition(page)) { ifTrue(page) }
    else { ifFalse(page) }

  /**
   * decides whether to crawl the website or not
   */
  def toCrawl = crawlCondition;
}

/**
 * factory of PageAnalyzer instances
 */
object PageAnalyzer {
  /**
   * returns a PageAnalyzer
   *
   * @param condition decides whether to use the first or second option
   * of behavior
   * @param ifTrue first option of behavior
   * @param ifFalse second option of behavior
   * @param crawlCondition selector of crawled sites
   */
  def apply(condition: (Page => Boolean),
    ifTrue: (Page => Unit), ifFalse: (Page => Unit),
    crawlCondition: (Page => Boolean)): PageAnalyzer =
    new PageAnalyzer(condition, ifTrue, ifFalse, crawlCondition)
}