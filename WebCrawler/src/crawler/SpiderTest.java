package crawler;

public class SpiderTest {

	public static void main(String[] args) {
        Spider spider = new Spider();
        spider.search("https://www.google.com/search?q=cryptocurrency&oq=cr&aqs=chrome.0.69i59j5j69i59j0j69i64j5.2501j0j7&sourceid=chrome&ie=UTF-8", "capricious");
	}

}
