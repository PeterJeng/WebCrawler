package googleSearch;

import java.util.ArrayList;

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
		String URL = "https://www.google.com/search?q=cryptocurrency&num=100&biw=1536&bih=759&source=lnt&tbs=sbd%3A1%2Ccdr%3A1%2Ccd_min%3A1%2F1%2F2017%2Ccd_max%3A1%2F1%2F2017&tbm=&google_abuse=GOOGLE_ABUSE_EXEMPTION%3DID%3D413f6cad97ec84eb:TM%3D1521932963:C%3Dr:IP%3D24.185.58.176-:S%3DAPGng0tagudTVyJtubNOOi_SBx05IpU0DQ%3B+path%3D/%3B+domain%3Dgoogle.com%3B+expires%3DSun,+25-Mar-2018+02:09:23+GMT";
		// Fetch the page
		
		// ArrayList to Hold the data
		ArrayList<Data> DataSet = new ArrayList<Data>();

		int count = 0;
		for (int i = 1; i < 3; i++) {
			

// *NOTE: If google flags you for being a robot, you can copy paste the url and submit the form confirming you're not a robot, then it will append some stuff to your previous url which you can use as the new value for the url variable
// Notice that after 'bm=' there is something called 'Google_Abuse_Exemption', I believe that allows us to continue scraping!
			
			URL = "https://www.google.com/search?q=cryptocurrency&num=100&biw=1536&bih=759&source=lnt&tbs=sbd%3A1%2Ccdr%3A1%2Ccd_min%3A1%2F"
					+ i + "%2F2017%2Ccd_max%3A1%2F" + i + "%2F2017&tbm=&google_abuse=GOOGLE_ABUSE_EXEMPTION%3DID%3D413f6cad97ec84eb:TM%3D1521932963:C%3Dr:IP%3D24.185.58.176-:S%3DAPGng0tagudTVyJtubNOOi_SBx05IpU0DQ%3B+path%3D/%3B+domain%3Dgoogle.com%3B+expires%3DSun,+25-Mar-2018+02:09:23+GMT";
			Document doc = Jsoup.connect(URL).userAgent(USER_AGENT).get();
			Data newData;

			// Traverse the results
			for (Element result : doc.select("div.g")) {
				String title = result.select("h3.r a").text();
				String url = result.select("h3.r a").attr("href");
				String date = result.select("span.f").text();
				
				newData = new Data();
				newData.setTitle(title);
				newData.setUrl(url);
				newData.setDate(date);
				DataSet.add(newData);
				// there are video links too, so this gets the date for video links
				if (date.isEmpty()) {
					date = result.select("div.slp.f").text();
				}

				System.out.println(date + " | " + title + " | " + url);
				count++;
			}
			
			//hopefully prevents google from blocking the crawler
			Thread.sleep(5000);
			
// Don't know why adding this print statement here helped continue the program execution but it did so thats cool!
			System.out.println("HERE");

		}

		// TODO: find how to do next page
		System.out.println("Pages found: " + count);
		
// Wanted to make sure that the data is actually getting stored in the arraylist and it is so now I will look for a way to organize this data into a nice graph
		System.out.println("Printing data from arrayList");
		System.out.println("----------------------------");
		for(Data p : DataSet) {
			System.out.println(p.getDate() + " | " + p.getTitle() + " | " + p.getUrl());
		}

	}
}
