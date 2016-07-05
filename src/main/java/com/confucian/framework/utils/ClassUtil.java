package com.confucian.framework.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * classUtil
 * @author ice
 */
public final class ClassUtil {

	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

	/**
	 * 禁止实例化
	 */
	private ClassUtil() {
	}

	/**
	 * 通过反射,获得class父类的泛型声明 
	 * 如public BookManager extends GenricManager
	 * @param clazz 输入
	 * @return 父类的第一个泛型声明，如果找不到返回Object.class
	 */
	public static Class<?> getSuperClassGenericType(Class<?> clazz) {
		return getSuperClassGenericType(clazz, 0);
	}

	/**
	 * 通过反射,获得class父类的泛型声明
	 * @param clazz 输入
	 * @param index 泛型声明的位置
	 * @return 父类的泛型声明，如果找不到返回Object.class
	 */
	public static Class<?> getSuperClassGenericType(Class<?> clazz, int index) {
		Type genType = clazz.getGenericSuperclass(); // 得到泛型父类
		if (!(genType instanceof ParameterizedType)) {
			logger.warn("superclass not ParameterizedType");
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of Parameterized Type: " + params.length);
			return Object.class;
		}
		return (Class<?>) params[index];
	}

	/**
	 * 得到Class的属性class <br>
	 * 当前类找不到,将递归到父类里找,最终找不到返回null
	 * @param clazz 输入
	 * @param name 属性名
	 * @return 属性class
	 */
	public static Class<?> getPropertyType(Class<?> clazz, String name) {
		Class<?> result = null;
		try {
			Field filed = clazz.getDeclaredField(name);
			result = filed.getType();
		} catch (Exception e) {
			Class<?> superClazz = clazz.getSuperclass();
			if (superClazz != null) {
				result = getPropertyType(superClazz, name);
				if (result != null) {
					return result;
				}
			}
		}
		return result;
	}

	/**
	 * value类型结果
	 * @param pvalue 属性string值
	 * @param plazz 属性class类型
	 * @return value类型结果
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getObjectValue(String pvalue, Class plazz) {
		try {
			Constructor<?> constructor = plazz.getConstructor(new Class[] { String.class });
			return constructor.newInstance(new Object[] { pvalue });
		} catch (Exception e) {
			Object fieldValue = pvalue;
			if (isDouble(plazz)) {
				fieldValue = new Double(pvalue);
			} else if (isInteger(plazz)) {
				fieldValue = new Integer(pvalue);
			} else if (isLong(plazz)) {
				fieldValue = new Long(pvalue);
			} else if (isBoolean(plazz)) {
				fieldValue = new Boolean(pvalue);
			} else if (isString(plazz)) {
				fieldValue = new String(pvalue);
			} else if (isEnum(plazz)) {
				fieldValue = Enum.valueOf(plazz, pvalue);
			} else {
				fieldValue = getDateValue(pvalue, plazz);
			}
			return fieldValue;
		}
	}

	/**
	 * 日期类型的 value类型结果,(包括sql.Date,sql.Timestamp,util.Date类型的)
	 * 从 getObjectValue 方法中抽取出来的方法,防止复杂度过高
	 * @param pvalue 属性string值
	 * @param plazz 属性class类型
	 * @return value类型结果
	 */
	@SuppressWarnings("rawtypes")
	private static Object getDateValue(String pvalue, Class plazz) {
		Object fieldValue = pvalue;
		if (isSqlDate(plazz)) {
			fieldValue = new java.sql.Date(new Date().getTime());
		} else if (isSqlTimestamp(plazz)) {
			String target = pvalue;
			if (!StringUtils.contains(target, " ")) { // 否则会报parse错误
				target += " 00:00:00";
			}
			fieldValue = new java.sql.Timestamp(new Date().getTime());
		} else if (isDate(plazz)) {
			try {
				fieldValue = StringUtils.isBlank(pvalue) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(pvalue);
			} catch (ParseException pe) {
				pe.printStackTrace();
			}
		}
		return fieldValue;
	}

	/**
	 * 是否是boolean型
	 * @param clazz 输入
	 * @return 是否是boolean型
	 */
	public static boolean isBoolean(Class<?> clazz) {
		return clazz != null && (Boolean.TYPE.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz));
	}

	/**
	 * 是否是Float型
	 * @param clazz 输入
	 * @return 是否是Float型
	 */
	public static boolean isFloat(Class<?> clazz) {
		return clazz != null && (Float.TYPE.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz));
	}

	/**
	 * 是否是Short型
	 * @param clazz 输入
	 * @return 是否是Short型
	 */
	public static boolean isShort(Class<?> clazz) {
		return clazz != null && (Short.TYPE.isAssignableFrom(clazz) || Short.class.isAssignableFrom(clazz));
	}

	/**
	 * 是否是byte型
	 * @param clazz 输入
	 * @return 是否是byte型
	 */
	public static boolean isByte(Class<?> clazz) {
		return clazz != null && (Byte.TYPE.isAssignableFrom(clazz) || Byte.class.isAssignableFrom(clazz));
	}

	/**
	 * 是否是Integer
	 * @param clazz 输入
	 * @return 是否是Integer
	 */
	public static boolean isInteger(Class<?> clazz) {
		return clazz != null && (Integer.TYPE.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz));
	}

	/**
	 * 是否是Long
	 * @param clazz 输入
	 * @return 是否是Long
	 */
	public static boolean isLong(Class<?> clazz) {
		return clazz != null && (Long.TYPE.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz));
	}

	/**
	 * 是否是double
	 * @param clazz 输入
	 * @return 是否是double
	 */
	public static boolean isDouble(Class<?> clazz) {
		return clazz != null && (Double.TYPE.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz));
	}

	/**
	 * 是否是char
	 * @param clazz 输入
	 * @return 是否是char
	 */
	public static boolean isChar(Class<?> clazz) {
		return clazz != null && (Character.TYPE.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz));
	}

	/**
	 * 是否是String型
	 * @param clazz 输入
	 * @return 是否是String型
	 */
	public static boolean isString(Class<?> clazz) {
		return clazz != null && (String.class.isAssignableFrom(clazz));
	}

	/**
	 * 是否是日期
	 * @param clazz 输入
	 * @return 是否是日期
	 */
	public static boolean isDate(Class<?> clazz) {
		return clazz != null && Date.class.isAssignableFrom(clazz);
	}

	/**
	 * 是否是日期
	 * @param clazz 输入
	 * @return 是否是日期
	 */
	public static boolean isSqlDate(Class<?> clazz) {
		return clazz != null && java.sql.Date.class.isAssignableFrom(clazz);
	}

	/**
	 * 是否是时间
	 * @param clazz 输入
	 * @return 是否是日期
	 */
	public static boolean isSqlTimestamp(Class<?> clazz) {
		return clazz != null && java.sql.Timestamp.class.isAssignableFrom(clazz);
	}

	/**
	 * 是否是数组型
	 * @param clazz 输入
	 * @return 是否是数组型
	 */
	public static boolean isArray(Class<?> clazz) {
		return clazz != null && clazz.isArray();
	}

	/**
	 * 是否是集合型
	 * @param clazz 输入
	 * @return 是否是集合型
	 */
	public static boolean isCollection(Class<?> clazz) {
		return clazz != null && Collection.class.isAssignableFrom(clazz);
	}

	/**
	 * 是否为枚举类型
	 * @param clazz 输入
	 * @return 是否为枚举类型
	 */
	public static boolean isEnum(Class<?> clazz) {
		return clazz != null && Enum.class.isAssignableFrom(clazz);
	}

	/**
	 * 判断是否为refer类
	 * @param refer 参考class
	 * @param clazz 输入
	 * @return 是否为refer类
	 */
	public static boolean isClass(Class<?> refer, Class<?> clazz) {
		return clazz != null && refer.isAssignableFrom(clazz);
	}
}
