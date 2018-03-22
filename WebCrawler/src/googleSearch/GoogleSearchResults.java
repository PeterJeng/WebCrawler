package googleSearch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 
 * @author Peter
 *
 * Create the connection string is as following:
 * On the google search bar: Cryptocurrency (Or anything you want)
 * Tools -> Any time -> Custom Range... -> (Set your personal date you'd like)
 * For maximum efficiency, load 100 results per page
 * 
 * Code found at: http://mph-web.de/web-scraping-with-java-top-10-google-search-results/
 * Credits go to Patrick Meier
 */
public class GoogleSearchResults {

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	
	public static void main(String[] args) throws Exception{
		// Fetch the page
				final Document doc = Jsoup.connect(
						"https://www.google.com/search?q=cryptocurrency&num=100&tbs=cdr:1,cd_min:1/1/2017,cd_max:1/1/2018,sbd:1&ei=1BKzWvC4MuHp_Qa004DwCA&start=100&sa=N&biw=1920&bih=974")
						.userAgent(USER_AGENT).get();

				int count = 0;
				
				// Traverse the results
				for (Element result: doc.select("div.g")) {
					String title = result.select("h3.r a").text();
					String url = result.select("h3.r a").attr("href");
					String date = result.select("span.f").text();
					
					//there are video links too, so this gets the date for video links
					if(date.isEmpty()) {
						date = result.select("div.slp.f").text();
					}

					System.out.println(date + " | " + title + " | " + url);
					count++;
				}
				
				//TODO: find how to do next page
				System.out.println("Pages found: " + count);

	}

}
