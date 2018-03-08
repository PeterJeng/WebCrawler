package crawler;

public class SpiderTest {

	public static void main(String[] args) {
        Spider spider = new Spider();
        spider.search("https://www.reddit.com/r/CryptoCurrency/", "cryptocurrency");
	}

}
