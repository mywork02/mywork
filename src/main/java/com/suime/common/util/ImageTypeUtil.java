package com.suime.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.confucian.framework.support.Constants;

/**
 * 文件类型工具类
 * @author ice
 */
public final class ImageTypeUtil {

	/**
	 * 文件扩展名类型map
	 */
	private static Map<String, String> extensionTypes = new HashMap<String, String>();

	// /**
	// * 文件类型map
	// */
	// private static Map<String, String> contentTypes = new HashMap<String, String>();

	static {
		extensionTypes.put("jpeg", "image/jpeg");
		extensionTypes.put("jpeg", "image/pjpeg");
		extensionTypes.put("jpg", "image/jpeg");
		extensionTypes.put("jpg", "image/pjpeg");
		extensionTypes.put("png", "image/png");
	}

	/**
	 * 获取文件名称,不带文件扩展名(后缀名)
	 * @param fileName 文件全名
	 * @return 文件名称
	 */
	public static String getNameWithoutExtension(final String fileName) {
		if (StringUtils.isNotBlank(fileName)) {
			return fileName.substring(0, fileName.lastIndexOf(Constants.VALUE_POINT_CHAR));
		}
		return fileName;
	}

	/**
	 * 根据文件名获取文件扩展名(后缀名)
	 * @param fileName 文件名
	 * @return 文件扩展名(后缀名)不带(.)号
	 */
	public static String getFileExtension(final String fileName) {
		if (StringUtils.isNotBlank(fileName)) {
			return fileName.substring(fileName.lastIndexOf(Constants.VALUE_POINT_CHAR) + 1);
		}
		return fileName;
	}

	/**
	* 根据扩展名(后缀名)，获取文件类型
	 * @param extension 文件扩展名(后缀名)
	 * @return 文件类型
	 */
	public static String getContentTypeByExtension(final String extension) {
		return extensionTypes.get(extension);
	}

	// /**
	// * 根据文件类型，判断后缀名
	// * @param contentType 文件类型
	// * @return 文件扩展名(后缀名)
	// */
	// public static String getExtensionByContentType(final String contentType) {
	// return contentTypes.get(contentType);
	// }
}
