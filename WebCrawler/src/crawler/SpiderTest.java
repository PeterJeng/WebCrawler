package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderTest {

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";

	public static void main(String[] args) throws Exception {
		Spider spider = new Spider();
		spider.search(
				"https://www.google.com/search?q=cryptocurrency&source=lnt&tbs=cdr%3A1%2Ccd_min%3A1%2F1%2F2016%2Ccd_max%3A1%2F1%2F2017&tbm=",
				"cryptocurrency");

	}

}
