package com.suime.common.helper;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * 加密helper类
 * @author ice
 */
public final class DigestHelper {

	/**
	 * md5加密
	 * @param source 待加密对象
	 * @return md5加密后的字符串
	 */
	public static String md5(String source) {
		String value = source;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(source.getBytes());
			BigInteger bi = new BigInteger(1, digest.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * md5加密
	 * @param byteBuffer 待加密对象
	 * @return md5加密后的字符串
	 */
	public static String md5(ByteBuffer byteBuffer) {
		String value = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(byteBuffer);
			BigInteger bi = new BigInteger(1, digest.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * sha1加密
	 * @param source 待加密对象
	 * @return sha1加密后的字符串
	 */
	public static String sha1(String source) {
		String value = source;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(source.getBytes());
			BigInteger bi = new BigInteger(1, digest.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * sha1加密
	 * @param byteBuffer 待加密对象
	 * @return sha1加密后的字符串
	 */
	public static String sha1(ByteBuffer byteBuffer) {
		String value = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(byteBuffer);
			BigInteger bi = new BigInteger(1, digest.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}
