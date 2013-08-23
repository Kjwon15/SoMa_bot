package com.soma.twitter_bot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import twitter4j.Status;
import twitter4j.User;

public class TweetParser implements Observer {

	// Constant Percents
	private static int HELLO_RATE = 70;

	private TwitterWrapper tw;
	// Contains user's id, date
	private HashMap<Long, Integer> userlog;

	private static Random random = new Random();

	public TweetParser() {
		tw = TwitterWrapper.getInstance();
		userlog = new HashMap<Long, Integer>();
	}

	private void ProcessTweet() {
		Status status = CustomListener.popStatus();
		User user = status.getUser();
		Calendar calendar = Calendar.getInstance();

		if (status.getText().toLowerCase()
				.startsWith("@" + tw.getScreenName().toLowerCase())) {
			// Mention to me
			// Twitter user name can contains a-zA-z0-9_
			// It is \\w
			String text = status.getText().replaceFirst("^@\\w+\\s*", "");
			System.out.println("Mention: @" + status.getUser().getScreenName()
					+ " - " + text);
			String answer = Answer.getAnswer(user, text);
			tw.replyTo(status, answer);

		} else if (status.getText().toLowerCase()
				.contains("@" + tw.getScreenName().toLowerCase())) {
			// Some tweet mentioned me
			String text = status.getText();
			System.out.println(String.format("@%s mentioned me - %s", status
					.getUser().getScreenName(), text));
			String answer = Answer.getAnswer(user,
					text.replaceAll("@\\S\\s?", ""));
			tw.replyTo(status, answer);
		} else if (status.getUser().getId() == tw.getID()) {
			// My tweet
			System.out.println("My Tweet: " + status.getText());
		} else if (status.getText().startsWith("@")) {
			// Mention to another user
			System.out.println("Others msg: @"
					+ status.getUser().getScreenName() + " - "
					+ status.getText());
		} else if (status.isRetweet() == true) {
			// User retweeted some tweet
			System.out.println(String.format("@%s RT @%s - %s", status
					.getUser().getScreenName(), status.getRetweetedStatus()
					.getUser().getScreenName(), status.getRetweetedStatus()
					.getText()));
		} else {
			// Just users tweet
			System.out.println("@" + status.getUser().getScreenName() + " - "
					+ status.getText());

			String answer = null;
			String text = status.getText();

			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int date = calendar.get(Calendar.DATE);
			long id = status.getUser().getId();

			// Get information
			if (answer == null) {
				answer = Answer.getInfo(text);
			}

			// Nothing found, just hello
			if (answer == null) {
				if (userlog.containsKey(id) == false || userlog.get(id) != date) {

					if (random.nextInt(100) < HELLO_RATE) {
						if (0 <= hour && hour < 5) {
							answer = Answer.getHelloDawn(text);
						} else if (hour < 18) {
							answer = Answer.getHelloDaytime(text);
						} else if (hour <= 24) {
							answer = Answer.getHelloNight(text);
						}

						System.out.println("Say hello to @"
								+ user.getScreenName());
					}
				}
			}


			if (answer != null) {
				tw.replyTo(status, answer);
			}
		}

		// Add User to log for hello msg check
		userlog.put(status.getUser().getId(), calendar.get(Calendar.DATE));
	}

	@Override
	public void update(Observable obj, Object arg) {
		if (obj instanceof CustomListener) {
			if (arg == CustomListener.Message.TWEET) {
				ProcessTweet();
			}
		}
	}
}