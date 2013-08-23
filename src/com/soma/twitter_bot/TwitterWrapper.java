package com.soma.twitter_bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import twitter4j.IDs;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UserStreamListener;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;

public class TwitterWrapper extends Observable {

	private static Configuration conf;
	private static Twitter twitter;
	private static TwitterStream twitterStream;

	private static TwitterWrapper instance = null;

	private static boolean isLoggedIn = false;
	private static String screenName = null;
	private static long id;
	private static String lastError;

	public enum Message {
		NAME, ERROR, LOGGEDIN,
	};

	public void Login(String consumer_key, String consumer_secret,
			String access_token, String access_token_secret) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumer_key)
				.setOAuthConsumerSecret(consumer_secret)
				.setOAuthAccessToken(access_token)
				.setOAuthAccessTokenSecret(access_token_secret);

		conf = cb.build();

		twitter = new TwitterFactory(conf).getInstance();
		twitterStream = new TwitterStreamFactory(conf).getInstance();

		while (isLoggedIn == false) {
			try {
				screenName = twitter.getScreenName();
				id = twitter.getId();
				setChanged();
				notifyObservers(Message.LOGGEDIN);
				isLoggedIn = true;
			} catch (IllegalStateException e) {
				SetMessage("IllegalState");
				isLoggedIn = false;
			} catch (TwitterException e) {
				SetMessage("Twitter Login error:\n" + e.getCause().toString());
				isLoggedIn = false;
			}
			if (isLoggedIn == false) {
				try {
					SetMessage("Wait 10 seconds for login");
					Thread.sleep(10000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public void setMentionListener(UserStreamListener listener) {
		if (twitterStream == null) {
			// Not logged in
			SetMessage("Not logged in");
		} else {
			twitterStream.addListener(listener);
			twitterStream.user();
		}
	}

	private void SetMessage(String msg) {
		lastError = msg;
		setChanged();
		notifyObservers(Message.ERROR);
	}

	public static TwitterWrapper getInstance() {
		if (instance == null) {
			synchronized (TwitterWrapper.class) {
				if (instance == null) {
					instance = new TwitterWrapper();
				}
			}
		}
		return instance;
	}

	public String getScreenName() {
		return screenName;
	}

	public long getID() {
		return id;
	}

	public String getLastError() {
		return lastError;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public List<User> getFollowers() throws TwitterException {
		List<User> users = new ArrayList<User>();
		try {
			long cursor = -1;
			IDs ids;
			do {
				ids = twitter.getFollowersIDs(cursor);
				for (long id : ids.getIDs()) {
					users.add(twitter.showUser(id));
				}
			} while ((cursor = ids.getNextCursor()) != 0);

		} catch (TwitterException e) {
			SetMessage("Get Follower error:\n" + e.toString());
			throw e;
		}

		return users;
	}

	public List<User> getFollowings() throws TwitterException {
		List<User> users = new ArrayList<User>();
		try {
			long cursor = -1;
			IDs ids;
			do {
				ids = twitter.getFriendsIDs(cursor);
				for (long id : ids.getIDs()) {
					users.add(twitter.showUser(id));
				}
			} while ((cursor = ids.getNextCursor()) != 0);

		} catch (TwitterException e) {
			SetMessage("Get Friend error:\n" + e.toString());
			throw e;
		}

		return users;
	}

	public void follow(User user) throws TwitterException {
		twitter.createFriendship(user.getId());
	}

	public void unfollow(User user) throws TwitterException {
		twitter.destroyFriendship(user.getId());
	}

	public void followAll() {
		try {
			List<User> followings = getFollowings();
			List<User> followers = getFollowers();
			StringBuilder sb = new StringBuilder();

			// Follow new Followers
			for (User user : followers) {
				if (followings.contains(user) == false) {
					try {
						follow(user);
						sb.append(String.format("New user: \"%s\" Success\n",
								user.getScreenName()));
					} catch (TwitterException e) {
						sb.append(String.format(
								"New user: \"%s\" Failed (%s)\n",
								user.getScreenName(), e.toString()));
					}
				}
			}
		} catch (TwitterException e) {
			System.err.println("Unabled to get Followings ans Followers");
		}
	}

	public void replyTo(Status status, String answer) {
		if (answer == null || answer.trim().length() == 0) {
			return;
		}

		long statusId = status.getId();

		String msg = String.format("@%s %s", status.getUser().getScreenName(),
				answer);

		msg = limit140(msg);

		try {
			StatusUpdate su = new StatusUpdate(msg).inReplyToStatusId(statusId);
			twitter.updateStatus(su);
		} catch (TwitterException e) {
			e.printStackTrace();
			System.err.println("Reply failed: " + msg + e.getMessage());
		}
	}

	public void tweet(String msg) {
		if (msg == null || msg.trim().length() == 0) {
			return;
		}
		msg = limit140(msg);

		try {
			twitter.updateStatus(msg);
		} catch (TwitterException e) {
			e.printStackTrace();
			System.err.println("Update failed: " + msg + e.getMessage());
		}
	}

	public void directMessage(User user, String msg) {
		if (msg == null || msg.trim().length() == 0) {
			return;
		}

		// Split when it's length is over 140
		// TODO: split by line
		Vector<String> messages = new Vector<String>();
		while (msg.length() > 140) {
			String head = msg.substring(0, 140);
			msg = msg.substring(140);
			messages.add(head);
		}
		messages.add(msg);

		try {
			for (String message : messages) {
				twitter.sendDirectMessage(user.getId(), message);
			}
		} catch (TwitterException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private static String limit140(String msg) {
		if (msg.length() > 140 - 1) {
			msg = msg.substring(0, 140 - 3 - 1) + "...";
		}

		return msg;
	}
}