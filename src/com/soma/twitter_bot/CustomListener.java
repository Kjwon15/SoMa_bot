package com.soma.twitter_bot;

import java.util.LinkedList;
import java.util.Observable;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;

public class CustomListener extends Observable implements UserStreamListener {
	
	// private static final String MSG_CANT_DM = "DM??? ???????????? ??????...";

	//private static CustomListener instance = null;
	private static LinkedList<Status> statusQueue;
	
	private static TwitterWrapper tw;
	
	public static enum Message  {
		TWEET,
	}
	
	public CustomListener(){
		statusQueue = new LinkedList<Status>();
		tw = TwitterWrapper.getInstance();
	}
	
	public static Status popStatus(){
		return statusQueue.poll();
	}


	@Override
	public void onStatus(Status status) {
		statusQueue.offer(status);
		setChanged();
		notifyObservers(Message.TWEET);
	}

	@Override
	public void onFollow(User from, User to) {
		if(from.getId() == tw.getID()){
			//I followed someone
		}else{
			//Someone follows me
			try {
				tw.follow(from);
				System.err.println("Following @" + from.getScreenName());
			} catch (TwitterException e) {
				System.err.println("Following @"+from.getScreenName() + " failed: " + e.getMessage());
			}
		}
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) { }

	@Override
	public void onScrubGeo(long arg0, long arg1) { }

	@Override
	public void onStallWarning(StallWarning warning) {
		System.err.println(String.format("Stall warning: %s", warning.getMessage()));
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		System.err.println(String.format("Limit Notice: %d", arg0));
	}

	@Override
	public void onException(Exception arg0) { }

	@Override
	public void onBlock(User arg0, User arg1) { }

	@Override
	public void onDeletionNotice(long arg0, long arg1) { }

	@Override
	public void onDirectMessage(DirectMessage dm) {
		User user = dm.getSender();
		if(user.getId() == tw.getID()){
			return;
		}

		//String answer = null;
		//tw.directMessage(user, answer);
	}

	@Override
	public void onFavorite(User arg0, User arg1, Status arg2) { }

	@Override
	public void onFriendList(long[] arg0) { }

	@Override
	public void onUnblock(User arg0, User arg1) { }

	@Override
	public void onUnfavorite(User arg0, User arg1, Status arg2) { }

	@Override
	public void onUserListCreation(User arg0, UserList arg1) { }

	@Override
	public void onUserListDeletion(User arg0, UserList arg1) { }

	@Override
	public void onUserListMemberAddition(User arg0, User arg1, UserList arg2) { }

	@Override
	public void onUserListMemberDeletion(User arg0, User arg1, UserList arg2) { }

	@Override
	public void onUserListSubscription(User arg0, User arg1, UserList arg2) { }

	@Override
	public void onUserListUnsubscription(User arg0, User arg1, UserList arg2) { }

	@Override
	public void onUserListUpdate(User arg0, UserList arg1) { }

	@Override
	public void onUserProfileUpdate(User arg0) { }
}