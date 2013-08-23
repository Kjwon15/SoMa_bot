package com.soma.twitter_bot;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Schedule {
	private static final int SECOND = 1000;
	private static final int MINUTE = 60 * SECOND;
	private static final int HOUR = 60 * MINUTE;
	private static Timer scheduler = new Timer();
	private static Random random = new Random();

	private static TwitterWrapper tw = TwitterWrapper.getInstance();

	public static void SetSchedule() {
		// First sleep 30 min. and every 6 hour
		TimerTask jobPlace = new ScheduledAutoTweet();
		scheduler.scheduleAtFixedRate(jobPlace, 30 * MINUTE, 2 * HOUR);
	}

	public static void CancleSchedule() {
		scheduler.cancel();
	}

	private static class ScheduledAutoTweet extends TimerTask {
		@Override
		public void run() {
			String[] autoTweets = { "우리는 장학금을 원합니다.", "수면실 지금 비었나요?",
					"으이이 샤워하고싶당", "집에 가고싶어요..ㅠㅠ" };
			tw.tweet(choice(autoTweets));
		}
	}

	private static String choice(String[] array) {
		if (array.length < 1) {
			return null;
		}
		return array[random.nextInt(array.length)];
	}
}
