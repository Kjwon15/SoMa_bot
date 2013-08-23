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
		
		String[] headers = {"�˾ƺô���", "�˾ƺ��ϱ�", "ã�ƺô���"};
		String[] footers = {"�̷��� ���Ծ�!", "��� �ϴµ�", "���� �̷�"};
		
		String[] foods =  { "�ܹ���","Ǫ����Ʈ","¥���", "�ʹ�", "������", "����", "ġŲ", "����", "�н�", "���", "���İ�Ƽ", "�� ��", };
		
		String res = "�� �� ����.";
		if (msg.contains("��ġ") || msg.contains("���"))
		{
			res = "�츮�� ����Ư���� ������ ������� 311(���ﵿ) �Ƴ�Ÿ�� 2��, �Ǵ� 7���� �־�.";
		}
		else if(msg.contains("��ȣ"))
		{
			res = "��ȭ��ȣ�� 02-6933-0701~0705";
		}
		else if(msg.matches("^.*��\\s*(����|������).*$"))
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