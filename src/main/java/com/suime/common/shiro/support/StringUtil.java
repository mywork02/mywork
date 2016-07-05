package com.suime.common.shiro.support;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.util.Random;

/**
 * 生成随机字符串
 * @author ice
 */
public final class StringUtil {
	/**
	 * 定义大写字符，除去O,I字母
	 */
	public static final String UPPER = "ABCDEFGHJKLMNPQRSTUVWXYZ";
	/**
	 * 定义所有小写字母，除去小写o,i字母
	 */
	public static final String LOWER = "abcdefghijkmnpqrstuvwxyz";
	/**
	 * 定义数字字符串，除去0,1
	 */
	public static final String DIGITAL = "0123456789";
	/**
	 * 字符串最大长度
	 */
	private static final int STRING_MAX_LENGTH = 100;

	/**
	 * randomGenerator
	 */
	private static Random randomGenerator;
	/**
	 * timeBasedUuidGenerator
	 */
	private static TimeBasedGenerator timeBasedUuidGenerator;

	/**
	 * sizeTable
	 */
	private static final int[] SIZE_TABLE = { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE };

	static {
		randomGenerator = new Random();
		// need to pass Ethernet address; can either use real one (shown here)
		EthernetAddress nic = EthernetAddress.fromInterface();
		// or bogus which would be gotten with: EthernetAddress.constructMulticastAddress()
		timeBasedUuidGenerator = Generators.timeBasedGenerator(nic);

	}

	/**
	 * 默认构造器
	 */
	private StringUtil() {
	}

	/**
	 * getRandomString
	 * @param length 生成的字符串长度，小于100
	 * @return randomString
	 */
	public static String getRandomString(int length) {
		return getRandomString(length, true, true, true);
	}

	/**
	 *生成大写的长度小于100的随机字符串
	 * @param length
	 * @return upperRandomString
	 */
	public static String getUpperRandomString(int length) {
		return getRandomString(length, true, false, false);
	}

	/**
	 * 生成小写的长度小于100的随机字符串
	 * @param length
	 * @return lower random string
	 */
	public static String getLowerRandomString(int length) {
		return getRandomString(length, false, true, false);
	}

	/**
	 * 生成纯数字组成的长度小于100的随机字符串
	 * @param length
	 * @return digital random string
	 */
	public static String getDigitalRandomString(int length) {
		return getRandomString(length, false, false, true);
	}

	/**
	 * 生成一个随机字符串
	 * @return 图片名称
	 */
	public static String getUUID() {
		return timeBasedUuidGenerator.generate().toString();
	}

	/**
	 * sizeOfInt
	 * @param x value
	 * @return value in sizeTable
	 */
	private static int sizeOfInt(int x) {
		for (int i = 0;; i++) {
			if (x <= SIZE_TABLE[i]) {
				return i + 1;
			}
		}
	}

	/**
	 * getRandomNumber
	 * @param length
	 * @return randomNumber
	 */
	private static String getRandomNumber(int length) {
		int max = (int) Math.pow(10, length);
		int result = randomGenerator.nextInt(max);
		int size = sizeOfInt(result);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < length - size; i++) {
			stringBuilder.append('0');
		}
		stringBuilder.append(result);
		return stringBuilder.toString();
	}

	/**
	 * 获取随机的数字字符串，最大支持11位
	 * @param length
	 * @param prefix
	 * @return randomNumberWithPrefix
	 */
	public static String getRandomNumberWithPrefix(int length, String prefix) {
		return prefix + getRandomNumber(length - 1);
	}

	/**
	 * 生成随机字符串
	 * @param length 字符串长度
	 * @param includeUpper 是否包含大写，true 包含
	 * @param includeLower 是否包含小写，true 包含
	 * @param includeDigital 是否包含数字 true 包含
	 * @return random string
	 */
	public static String getRandomString(int length, boolean includeUpper, boolean includeLower, boolean includeDigital) {
		int targetLength = length;
		if (targetLength > STRING_MAX_LENGTH) {
			targetLength = STRING_MAX_LENGTH;
		}
		StringBuilder stringBuilder = new StringBuilder();
		if (includeUpper) {
			stringBuilder.append(UPPER);
		}
		if (includeDigital) {
			stringBuilder.append(DIGITAL);
		}
		if (includeLower) {
			stringBuilder.append(LOWER);
		}
		StringBuffer sb = new StringBuffer();
		Random ran = new Random();
		int max = stringBuilder.length();
		for (int i = 0; i < targetLength; i++) {
			sb.append(stringBuilder.charAt(ran.nextInt(max)));
		}
		return sb.toString();
	}
}
