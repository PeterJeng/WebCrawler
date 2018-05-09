package redditsearch;

import java.util.ArrayList;
import googleSearch.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RedditSearch {
	public static void main(String[] args) {
		
		final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
		
		// ArrayList to Hold the data
		ArrayList<Data> DataSet = new ArrayList<Data>();
		
		String URL = "https://redditsearch.io/?term=Cryptocurrency&dataviz=true&aggs=false&subreddits=&searchtype=posts,stats,dataviz&search=true&start=1357016400&end=1388466000&size=100";
		int count = 0;
		System.out.println("hello");
		try {
			Document doc = Jsoup.connect(URL).userAgent(USER_AGENT).get();
			Data newData;
			
			//stores previous dates in case we have results with no dates
			String tmpDate = "";
			// Traverse the results
			for (Element result : doc.select("div.score-container")) {
				String title = result.select("div.title").text();
				String url = result.select("div.data-url").attr("data-url");
				String date = result.select("div.description").attr("title");
				
				if (date.isEmpty()) {
					date = result.select("div.slp.f").text();
					if(date.length() > 13)
						date = date.substring(0, 13);
				}
				
				//fills in empty dates
				if(date.isEmpty()) {
					date = tmpDate;
				}
				else {
					tmpDate = date;
				}

				newData = new Data();
				if(!((title.contains(";")) || (title.contains("#")))) {
					newData.setTitle(title);
				}
				newData.setUrl(url);
				newData.setDate(date);
				DataSet.add(newData);
				// there are video links too, so this gets the date for video links
				

				System.out.println(date + " | " + title + " | " + url);
				count++;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		System.out.println("Pages found: " + count);

		// Wanted to make sure that the data is actually getting stored in the arraylist
		// and it is so now I will look for a way to organize this data into a nice
		// graph
		System.out.println("Printing data from arrayList");
		System.out.println("----------------------------");
		for (Data p : DataSet) {
			System.out.println(p.getDate() + " | " + p.getTitle() + " | " + p.getUrl());
		}
		
		writeToFile(DataSet);

		System.out.println("DONE");
	}
	
	public static void writeToFile(ArrayList<Data>DataSet) {
		BufferedWriter outputWriter = null;
		try {
			outputWriter = new BufferedWriter(new FileWriter("dataset.txt"));
			
			for (Data p : DataSet) {
			outputWriter.write(p.getDate() + ";" + p.getTitle() + ";" + p.getUrl());
			outputWriter.newLine();
			}
			
			outputWriter.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
