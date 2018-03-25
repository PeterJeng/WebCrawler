package googleSearch;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.Scanner;

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

	public static Scanner reader = new Scanner(System.in);

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";

	public static void main(String[] args) throws Exception {
		// After some testing, the url follows the format as follows:
		// ccd_min is start date, A(1) refers to month, 1 (January), F(1) refers to date
		// (1), and F2017 is year
		// modifying date to 365 changes it to december
		// ccd_max is end date, keep end and start date the same for best results
		String query = "https://www.google.com/search?q=cryptocurrency&num=100&biw=1536&bih=759&source=lnt&tbs=sbd%3A1%2Ccdr%3A1%2Ccd_min%3A1%2F1%2F2017%2Ccd_max%3A1%2F1%2F2017&tbm=&google_abuse=";
		String exemption = "GOOGLE_ABUSE_EXEMPTION%3DID%3Daa6705dfc94a9333:TM%3D1522013150:C%3Dr:IP%3D128.6.37.213-:S%3DAPGng0u3ZbIvj00KYX3VMVeEeLXy4JLeXA%3B+path%3D/%3B+domain%3Dgoogle.com%3B+expires%3DMon,+26-Mar-2018+00:25:50+GMT";
		String URL = query + exemption;
		// Fetch the page

		// ArrayList to Hold the data
		ArrayList<Data> DataSet = new ArrayList<Data>();

		int count = 0;
		for (int i = 41; i < 100; i++) {
			// *NOTE: If google flags you for being a robot, you can copy paste the url and
			// submit the form confirming you're not a robot, then it will append some stuff
			// to your previous url which you can use as the new value for the url variable
			// Notice that after 'bm=' there is something called 'Google_Abuse_Exemption', I
			// believe that allows us to continue scraping!
			query = "https://www.google.com/search?q=cryptocurrency&num=100&biw=1536&bih=759&source=lnt&tbs=sbd%3A1%2Ccdr%3A1%2Ccd_min%3A1%2F"
					+ i + "%2F2017%2Ccd_max%3A1%2F" + i + "%2F2017&tbm=&google_abuse=";
			URL = query + exemption;
			
			//if flagged for scraping, just add in the new google_abuse_exemption url and press enter
			try {
				Document doc = Jsoup.connect(URL).userAgent(USER_AGENT).get();
				Data newData;
				
				//stores previous dates in case we have results with no dates
				String tmpDate = "";
				// Traverse the results
				for (Element result : doc.select("div.g")) {
					String title = result.select("h3.r a").text();
					String url = result.select("h3.r a").attr("href");
					String date = result.select("span.f").text();
					
					//fills in empty dates
					if(date.isEmpty()) {
						date = tmpDate;
					}
					else {
						tmpDate = date;
					}

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
			} catch (Exception e) {
				System.out.println(e.toString());
				System.out.println("Enter new GOOGLE ABUSE EXEMPTION:");
				//Enter string format:
				//GOOGLE_ABUSE_EXEMPTION%3DID%3D12f9ad765298f748:TM%3D1522013520:C%3Dr:IP%3D128.6.37.213-:S%3DAPGng0vpp6dEID-VeNnGmAE8gWQe7poi8g%3B+path%3D/%3B+domain%3Dgoogle.com%3B+expires%3DMon,+26-Mar-2018+00:32:00+GMT
				exemption = reader.nextLine();
				continue;
			}
		}

		// TODO: find how to do next page
		System.out.println("Pages found: " + count);

		// Wanted to make sure that the data is actually getting stored in the arraylist
		// and it is so now I will look for a way to organize this data into a nice
		// graph
		System.out.println("Printing data from arrayList");
		System.out.println("----------------------------");
		for (Data p : DataSet) {
			System.out.println(p.getDate() + " | " + p.getTitle() + " | " + p.getUrl());
		}

		System.out.println("DONE");

	}
}
