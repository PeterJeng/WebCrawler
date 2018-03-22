package crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Spider {
	private static final int MAX_PAGES_TO_SEARCH = 20;
	// pages we have already visited, using Set for no repeats
	private Set<String> pagesVisited = new HashSet<String>();
	// a list of links found on the page to open
	private List<String> pagesToVisit = new LinkedList<String>();

	/**
	 * Finds the next URL to visit (in the order they were found). Makes sure the
	 * URL found has not already been visited by using pagesVisited
	 * 
	 * @return nextUrl
	 */
	private String nextUrl() {
		String nextUrl;

		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl));

		return nextUrl;
	}

	public void search(String url, String searchWord){
        while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
        {
            String currentUrl;
            SpiderLeg leg = new SpiderLeg();
            if(this.pagesToVisit.isEmpty())
            {
                currentUrl = url;
                this.pagesVisited.add(url);
            }
            else
            {
                currentUrl = this.nextUrl();
            }
            leg.crawl(currentUrl, searchWord); // Lots of stuff happening here. Look at the crawl method in
                                   // SpiderLeg
            boolean success = leg.searchForWord(searchWord);
            if(success)
            {
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                System.out.println();
            }  
            this.pagesToVisit.addAll(leg.getLinks());
        }
        System.out.println(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size())) ;
    }
	
	public void start(List<String> pagesToVist) {
		
		
	}
}
