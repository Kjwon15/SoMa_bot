package com.soma.twitter_bot;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Main {
	private static final String CONSUMER_KEY = "Go2mnRpzVHYRDB6Qc5PQ";
	private static final String CONSUMER_SECRET = "cs3SXpx4BGHEIpetDK7rdf8erADDDo9co6628vCjWc";

	private static final String ACCESS_TOKEN = "1693562306-itqPLgypzyy9xb4GjetcI12ss48klKtY6rwjTJL";
	private static final String ACCESS_TOKEN_SECRET = "2UgjA4HsobDapBlWQbj4EmzvQiYSVqIMysAdQJ4E";

	public static void main(String[] args) {
		TwitterFactory factory = new TwitterFactory();
	    AccessToken accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
	    Twitter twitter = factory.getInstance();
	    twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
	    twitter.setOAuthAccessToken(accessToken);
	    
	    try {
			System.out.println(twitter.getScreenName());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
