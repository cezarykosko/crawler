import org.jsoup._
import org.jsoup.nodes._
import org.jsoup.select._
import org.jsoup.safety._
import java.io.IOException

object crawler {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(306); 
  def parseDoc(doc: Document): Array[String] = {
    val links = doc.getElementsByTag("a");
    var parsedLinks = Array[String]();
    links.toArray(parsedLinks)
  };System.out.println("""parseDoc: (doc: org.jsoup.nodes.Document)Array[String]""");$skip(255); 
  
  def crawl(f: (String => Unit))( url: String): Unit = {
    try {
      	f(url)
        val doc: Document = Jsoup.connect(url).get();
        val links = parseDoc(doc);
        for (link <- links) {
        	crawl(f)(link);
        }
      
    }
  };System.out.println("""crawl: (f: String => Unit)(url: String)Unit""")}
}

object miau {
  def funkcja(a: String): Boolean = {
    println(a);
    true;
  }
  crawler.crawl(println(_))("http://www.wikipedia.pl");
}


  /*def crawl(f: String => Boolean, url: String, depth: Integer): Unit = {
    if (depth != 0) {
      try {
        if (f(url)) {
          val doc: Document = Jsoup.connect(url).get();
          val links = parseDoc(doc);
          for (link <- links) {
            crawl(f, link, depth - 1)
          }
        }
      } catch {
        case e: IOException => Unit
        case e: Exception =>
          if (e.getMessage == null) {
            println("an unknown exception has been caught.")
          } else {
            println("an unknown exception has been caught: " + e.getMessage())
          }
      }
    }
  }
*/
