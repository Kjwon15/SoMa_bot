package com.soma.twitter_bot;

import java.util.Calendar;
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
		TimerTask jobAutoTeet = new ScheduledAutoTweet();
		TimerTask jobBreakfast = new ScheduledFoodTime();
		TimerTask jobLunch = new ScheduledFoodTime();
		TimerTask jobDinner = new ScheduledFoodTime();
		scheduler.scheduleAtFixedRate(jobAutoTeet, 30 * MINUTE, 30 * MINUTE);

		Calendar firstTime = Calendar.getInstance();
		firstTime.set(Calendar.HOUR_OF_DAY, 8);
		firstTime.set(Calendar.MINUTE, 0);
		firstTime.set(Calendar.SECOND, 0);
		firstTime.set(Calendar.MILLISECOND, 0);

		//breakfast.
		scheduler.scheduleAtFixedRate(jobBreakfast, firstTime.getTime(), 24 * HOUR);
		
		//Lunch.
		firstTime.set(Calendar.HOUR_OF_DAY, 12);
		scheduler.scheduleAtFixedRate(jobLunch, firstTime.getTime(), 24 * HOUR);
		
		//Dinner.
		firstTime.set(Calendar.HOUR_OF_DAY, 18);
		scheduler.scheduleAtFixedRate(jobDinner, firstTime.getTime(), 24 * HOUR);
		
	}

	public static void CancleSchedule() {
		scheduler.cancel();
	}

	private static class ScheduledAutoTweet extends TimerTask {
		@Override
		public void run() {
			String[] autoTweets = {
					"우리는 장학금을 원합니다.",
					"수면실 지금 비었나요?",
					"으이이 샤워하고싶당",
					"집에 가고싶어요..ㅠㅠ",
					"SW_Maestro_ 지원 절차는 다음과 같습니다. http://swmaestro.kr/homepage/default/page/subLocation.do?menu_no=20010301&up_menu_no=200103",
					"SW_Maestro_ 는 총 3단계의 인증과정을 통해 SW 인재를 양성하는 프로그램입니다.",
					"SW_Maestro_ 홈페이지는 http://swmaestro.kr/ 이곳입니다.", };
			tw.tweet(choice(autoTweets));
		}
	}

	private static class ScheduledFoodTime extends TimerTask {
		@Override
		public void run() {
			String choose = Answer.getFood("뭐 먹을까?");
			tw.tweet(choose);
		}
	}

	private static String choice(String[] array) {
		if (array.length < 1) {
			return null;
		}
		return array[random.nextInt(array.length)];
	}
}
