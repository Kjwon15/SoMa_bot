package com.soma.twitter_bot;

public class Main {
	private static final String CONSUMER_KEY = "Go2mnRpzVHYRDB6Qc5PQ";
	private static final String CONSUMER_SECRET = "cs3SXpx4BGHEIpetDK7rdf8erADDDo9co6628vCjWc";

	private static final String ACCESS_TOKEN = "1693562306-itqPLgypzyy9xb4GjetcI12ss48klKtY6rwjTJL";
	private static final String ACCESS_TOKEN_SECRET = "2UgjA4HsobDapBlWQbj4EmzvQiYSVqIMysAdQJ4E";

	public static void main(String[] args) {
		TwitterWrapper tw = new TwitterWrapper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		System.out.println(tw.getScreenName());
	}

}
