package com.soma.twitter_bot;


import twitter4j.User;

public class Answer {

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
		return answer;
	}

	public static String getInformation(String text) {
		// TODO Auto-generated method stub
		return null;
	}

}