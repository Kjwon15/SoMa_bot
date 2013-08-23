package com.soma.twitter_bot;

import java.util.Random;

import twitter4j.User;

public class Answer {

	private static final Random random = new Random();

	public static class InformationException extends Exception {
		private static final long serialVersionUID = 7845651816707719328L;

		public InformationException(String message) {
			super(message);
		}
	}

	/**
	 * Get answer based on message
	 * 
	 * @param msg
	 *            users tweet excluding {@literal @}username
	 * @return a String that is an answer for the user
	 */
	public static String getAnswer(User user, String msg) {

		String answer = null;

		if (answer == null) {
			answer = getInfo(msg);
		}

		if (answer == null) {
			answer = getFood(msg);
		}

		return answer;
	}

	static public String getInfo(String msg) {
		String[] headers = { "알아봤더니", "알아보니까", "찾아봤더니", "음.. 그게..", "그거 나 알아." };
		String[] footers = { "이렇게 나왔어!", "라고 하는데", "대충 이래", "여깄다!" };

		String res = null;

		if (msg.contains("위치") || msg.contains("장소")) {
			res = "서울특별시 강남구 테헤란로 311(역삼동) 아남타워 2층, 또는 7층";
		} else if (msg.contains("번호")) {
			res = "02-6933-0701~0705";
		}

		String answer = null;
		if (res != null) {
			answer = String.format("%s %s %s", choice(headers), res,
					choice(footers));
		}

		return answer;
	}

	private static String getFood(String msg) {
		String[] foods = { "햄버거 먹어", "푸드코트 가", "짜장면 먹어", "초밥같은거 먹을래?", "족발",
				"치킨!", "피자!", "분식", "고기고기!", "스파게티", "안먹고 살 빼는게 어떨까?", "먹지마", };

		String[] headers = { "오늘은..", "음..", "그냥", "그걸 왜 나한테 물어보는 지는 모르겠지만", };

		String answer = null;

		if (msg.matches("^.*(뭐|뭘)\\s*(먹지|먹을까)?.*$")) {
			answer = String.format("%s %s", choice(headers), choice(foods));
		}

		return answer;
	}

	private static String choice(String[] array) {
		if (array.length < 1) {
			return null;
		}
		return array[random.nextInt(array.length)];
	}

	public static String getInformation(String text) {
		String res = null;

		if (text.matches(".*(^|\\s)소마.*$") || text.matches("소프트웨어\\s?마에스트로")) {
			res = getInfo(text);
		}

		return res;
	}

	public static String getHelloDawn(String text) {
		String[] hellos = { "이 새벽에 혹시 코딩중?", "이 새벽에 안자고 뭐하냐...", "빨리 잠이나 자",
				"너 혹시 트잉여?" };
		return choice(hellos);
	}

	public static String getHelloDaytime(String text) {
		String[] hellos = { "안녕?", "おはよう", "오늘은 뭘 할거야?", "하이!" };
		return choice(hellos);
	}

	public static String getHelloNight(String text) {
		String[] hellos = { "저녁인데 뭐해?", "밥은 먹고 다니냐..", "저녁 먹었어?" };
		return choice(hellos);
	}

}