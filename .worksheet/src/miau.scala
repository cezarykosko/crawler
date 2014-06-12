import org.jsoup._
import org.jsoup.nodes._
import org.jsoup.select._
import org.jsoup.safety._
import java.io.IOException
import java.net.URI
import java.net.URL
import java.io.File

object Miau {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(239); 
	for(a <- List()){
		println(a.toString)
	};$skip(51); 
	val plik = new File("E:\\dump\\1\\index.html");System.out.println("""plik  : java.io.File = """ + $show(plik ));$skip(15); val res$0 = 
	plik.exists();System.out.println("""res0: Boolean = """ + $show(res$0));$skip(60); 
	val url1 = new URL("http://www.mimuw.edu.pl/~kdr/test/1/");System.out.println("""url1  : java.net.URL = """ + $show(url1 ));$skip(40); 
	val url2 = new URL("http://java.com/");System.out.println("""url2  : java.net.URL = """ + $show(url2 ));$skip(34); val res$1 = 
	url1.getHost() == url2.getHost();System.out.println("""res1: Boolean = """ + $show(res$1));$skip(16); val res$2 = 
	url2.getHost();System.out.println("""res2: String = """ + $show(res$2))}
  }
