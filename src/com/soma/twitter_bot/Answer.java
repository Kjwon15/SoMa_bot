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
		
		String[] headers = {"알아봤더니", "알아보니까", "찾아봤더니"};
		String[] footers = {"이렇게 나왔어!", "라고 하는데", "대충 이래"};
		
		String[] foods =  { "햄버거","푸드코트","짜장면", "초밥", "먹지마", "족발", "치킨", "피자", "분식", "고기", "스파게티", "살 빼", };
		
		String res = "알 수 없음.";
		if (msg.contains("위치") || msg.contains("장소"))
		{
			res = "우리는 서울특별시 강남구 테헤란로 311(역삼동) 아남타워 2층, 또는 7층에 있어.";
		}
		else if(msg.contains("번호"))
		{
			res = "전화번호가 02-6933-0701~0705";
		}
		else if(msg.matches("^.*뭐\\s*(먹지|먹을까).*$"))
		{
			res = Choice(foods);
		}
		
		String answer = String.format("%s %s %s",Choice(headers), res, Choice(footers));
		return answer;
		
	}

	private static String Choice(String[] array) {
		if (array.length < 1){
			return null;
		}
		return array[random.nextInt(array.length)];
	}

	public static String getInformation(String text) {
		// TODO Auto-generated method stub
		return null;
	}

}