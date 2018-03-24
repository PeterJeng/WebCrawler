package googleSearch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 
 * @author Peter
 *
 *         Create the connection string is as following: On the google search
 *         bar: Cryptocurrency (Or anything you want) Tools -> Any time ->
 *         Custom Range... -> (Set your personal date you'd like) For maximum
 *         efficiency, load 100 results per page
 * 
 *         Code found at:
 *         http://mph-web.de/web-scraping-with-java-top-10-google-search-results/
 *         Credits go to Patrick Meier
 */
public class GoogleSearchResults {

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";

	public static void main(String[] args) throws Exception {
		// After some testing, the url follows the format as follows:
		// ccd_min is start date, A(1) refers to month, 1 (January), F(1) refers to date
		// (1), and F2017 is year
		// modifying date to 365 changes it to december
		// ccd_max is end date, keep end and start date the same for best results
		String URL = "https://www.google.com/search?q=cryptocurrency&num=100&biw=1536&bih=759&source=lnt&tbs=sbd%3A1%2Ccdr%3A1%2Ccd_min%3A1%2F1%2F2017%2Ccd_max%3A1%2F1%2F2017&tbm=";
		// Fetch the page

		int count = 0;
		for (int i = 1; i < 10; i++) {
			URL = "https://www.google.com/search?q=cryptocurrency&num=100&biw=1536&bih=759&source=lnt&tbs=sbd%3A1%2Ccdr%3A1%2Ccd_min%3A1%2F"
					+ i + "%2F2017%2Ccd_max%3A1%2F" + i + "%2F2017&tbm=";
			Document doc = Jsoup.connect(URL).userAgent(USER_AGENT).get();

			// Traverse the results
			for (Element result : doc.select("div.g")) {
				String title = result.select("h3.r a").text();
				String url = result.select("h3.r a").attr("href");
				String date = result.select("span.f").text();

				// there are video links too, so this gets the date for video links
				if (date.isEmpty()) {
					date = result.select("div.slp.f").text();
				}

				System.out.println(date + " | " + title + " | " + url);
				count++;
			}
			
			//hopefully prevents google from blocking the crawler
			Thread.sleep(5000);

		}

		// TODO: find how to do next page
		System.out.println("Pages found: " + count);

	}

}
