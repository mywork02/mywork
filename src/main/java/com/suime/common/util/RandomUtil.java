package com.suime.common.util;

import java.util.Random;

/**
 * 随机数工具类
 * @author ice
 */
public class RandomUtil {

	/**
	 * 生成数字随机数
	 * @param length
	 * @return 随机数
	 */
	public static String genrateDigits(int length) {
		StringBuffer buffer = new StringBuffer();
		int len = (length > 0) ? length : 0;
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			buffer.append(random.nextInt(10));
		}
		return buffer.toString();
	}

	/**
	 * 生成随机字符串
	 * @param length
	 * @return 随机字符串
	 */
	public static String generateRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
