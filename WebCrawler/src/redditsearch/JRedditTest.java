package redditsearch;

import java.util.ArrayList;

import reddit.Post;
import reddit.PostListener;
import reddit.Reddit;
import reddit.SortMode;

public class JRedditTest {
	public static void main(String[] args) {
		Reddit reddit = Reddit.getInstance();
		reddit.getPosts("virginiatech", SortMode.TOP, new PostListener() {
			@Override
			public void onSuccess(ArrayList<Post> posts) {
				System.out.println(posts);
				
			}
		});
	}
}