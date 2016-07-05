package com.suime.common.cache.support;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * stringAsSetCacheHelper
 * @author ice
 */
public class StringAsSetCacheHelper {

	/**
	 * 空格字符串
	 */
	private static final String STRING_SPLIT_CHAR = " ";

	/**
	 * 空格字符
	 */
	private static final char WHITE_SPACE_CHAR = ' ';
	/**
	 * +字符
	 */
	private static final char OPERATOR_ADD_CHAR = '+';
	/**
	 * -字符
	 */
	private static final char OPERATOR_MINUS_CHAR = '-';
	/**
	 * threashold vilocity
	 */
	private static final int THREASHOLD_VILOCITY = 1000;

	/**
	 * 将String重新解析并且生成set数组，只支持Long, Integer, String, Float, Double类型
	 * @param input
	 * @param clazz
	 * @return boolean
	 */
	public static <T> boolean convertStringToSet(String input, Class<T> clazz, Set<T> output) {
		if (StringUtils.isBlank(input)) {
			return false;
		}
		int vilocity = 0;
		String[] elements = input.split(STRING_SPLIT_CHAR);
		if (elements != null && elements.length > 0) {
			for (int pos = 0; pos < elements.length; pos++) {
				String temp = elements[pos];
				if (temp != null && temp.length() > 1) {
					char op = temp.charAt(0);
					if (OPERATOR_ADD_CHAR == op) {
						setAdd(clazz, output, temp);
					} else if (OPERATOR_MINUS_CHAR == op) {
						setRemove(clazz, output, temp);
						++vilocity;
					}
				}
			}
		}
		return vilocity >= THREASHOLD_VILOCITY;
	}

	/**
	 * setRemove
	 * @param clazz
	 * @param output
	 * @param temp
	 */
	@SuppressWarnings("unchecked")
	private static <T> void setRemove(Class<T> clazz, Set<T> output, String temp) {
		if (clazz.equals(Long.class)) {
			output.remove((T) Long.decode(temp.substring(1)));
		} else if (clazz.equals(String.class)) {
			output.remove((T) temp.substring(1));
		} else if (clazz.equals(Integer.class)) {
			output.remove((T) Integer.decode(temp.substring(1)));
		} else if (clazz.equals(Float.class)) {
			output.remove((T) Float.valueOf(temp.substring(1)));
		} else if (clazz.equals(Double.class)) {
			output.remove((T) Double.valueOf(temp.substring(1)));
		}
	}

	/**
	 * set add
	 * @param clazz
	 * @param output
	 * @param temp
	 */
	@SuppressWarnings("unchecked")
	private static <T> void setAdd(Class<T> clazz, Set<T> output, String temp) {
		if (clazz.equals(Long.class)) {
			output.add((T) Long.decode(temp.substring(1)));
		} else if (clazz.equals(String.class)) {
			output.add((T) temp.substring(1));
		} else if (clazz.equals(Integer.class)) {
			output.add((T) Integer.decode(temp.substring(1)));
		} else if (clazz.equals(Float.class)) {
			output.add((T) Float.valueOf(temp.substring(1)));
		} else if (clazz.equals(Double.class)) {
			output.add((T) Double.valueOf(temp.substring(1)));
		}
	}

	/**
	 * 将String重新解析并且生成set数组，只支持Long, Integer, String, Float, Double类型
	 * @param input
	 * @param clazz
	 * @return boolean
	 */
	public static <T> boolean convertStringToList(String input, Class<T> clazz, List<T> output) {
		if (StringUtils.isBlank(input)) {
			return false;
		}
		int vilocity = 0;
		String[] elements = input.split(STRING_SPLIT_CHAR);
		if (elements != null && elements.length > 0) {
			for (int pos = 0; pos < elements.length; pos++) {
				String temp = elements[pos];
				if (temp != null && temp.length() > 1) {
					char op = temp.charAt(0);
					if (OPERATOR_ADD_CHAR == op) {
						add(clazz, output, temp);
					} else if (OPERATOR_MINUS_CHAR == op) {
						remove(clazz, output, temp);
						++vilocity;
					}
				}
			}
		}
		return vilocity >= THREASHOLD_VILOCITY;
	}

	/**
	 * remove
	 * @param clazz
	 * @param output
	 * @param temp
	 */
	@SuppressWarnings("unchecked")
	private static <T> void remove(Class<T> clazz, List<T> output, String temp) {
		if (clazz.equals(Long.class)) {
			output.remove((T) Long.decode(temp.substring(1)));
		} else if (clazz.equals(String.class)) {
			output.remove((T) temp.substring(1));
		} else if (clazz.equals(Integer.class)) {
			output.remove((T) Integer.decode(temp.substring(1)));
		} else if (clazz.equals(Float.class)) {
			output.remove((T) Float.valueOf(temp.substring(1)));
		} else if (clazz.equals(Double.class)) {
			output.remove((T) Double.valueOf(temp.substring(1)));
		}
	}

	/**
	 * add
	 * @param clazz
	 * @param output
	 * @param temp
	 */
	@SuppressWarnings("unchecked")
	private static <T> void add(Class<T> clazz, List<T> output, String temp) {
		if (clazz.equals(Long.class)) {
			output.add((T) Long.decode(temp.substring(1)));
		} else if (clazz.equals(String.class)) {
			output.add((T) temp.substring(1));
		} else if (clazz.equals(Integer.class)) {
			output.add((T) Integer.decode(temp.substring(1)));
		} else if (clazz.equals(Float.class)) {
			output.add((T) Float.valueOf(temp.substring(1)));
		} else if (clazz.equals(Double.class)) {
			output.add((T) Double.valueOf(temp.substring(1)));
		}
	}

	/**
	 * 检查以String字符串为代表的Set是否包含某一元素
	 * @param str
	 * @param element
	 * @return boolean
	 */
	public static <T> boolean containsElementInString(String str, T element) {
		int result = 0;
		int startPos = 0;
		int endPos = 0;
		String elementString = element.toString();
		boolean isAdd = true;
		if (!StringUtils.isBlank(str)) {
			for (int i = 0; i < str.length(); i++) {
				char op = str.charAt(i);
				if (op == OPERATOR_ADD_CHAR) {
					startPos = i;
					isAdd = true;
				} else if (op == OPERATOR_MINUS_CHAR) {
					startPos = i;
					isAdd = false;
				} else if (op == WHITE_SPACE_CHAR) {
					endPos = i;
					if (str.substring(startPos + 1, endPos).equals(elementString)) {
						if (isAdd) {
							++result;
						} else {
							--result;
						}
					}
				}
			}
		}
		return result > 0;
	}

	/**
	 * This method is not good as containsElementInString by iterator. 
	 * @param s
	 * @param element
	 * @return boolean
	 */
	@SuppressWarnings("unused")
	private static <T> boolean containsElementInStringBySplit(String s, T element) {
		if (StringUtils.isBlank(s)) {
			return false;
		}
		String serializeString = serializeToString(element, false);
		if (serializeString.equals(s)) {
			return true;
		}

		String[] s1 = s.split("\\" + serializeString);
		int addNum = s1.length;
		if (s.endsWith(serializeString)) {
			addNum++;
		}
		String minusString = String.valueOf(OPERATOR_MINUS_CHAR) + element + STRING_SPLIT_CHAR;
		String[] s2 = s.split("\\" + minusString);
		int minusNum = s2.length;
		if (s.endsWith(minusString)) {
			minusNum++;
		}
		// if (addNum <= minusNum) {
		// return false;
		// } else {
		// return true;
		// }
		return addNum > minusNum;
	}

	/**
	 * countElementInString
	 * @param str
	 * @return count
	 */
	public static <T> int countElementInString(String str) {
		int result = 0;
		if (!StringUtils.isBlank(str)) {
			for (int i = 0; i < str.length(); i++) {
				char op = str.charAt(i);
				if (op == OPERATOR_ADD_CHAR) {
					++result;
				} else if (op == OPERATOR_MINUS_CHAR) {
					--result;
				}
			}
		}
		return result;
	}

	/**
	 * 将某个set序列化成字符串
	 * @param elements
	 * @return 序列化字符串
	 */
	public static <T> String serializeCollectionToString(Collection<T> elements) {
		if (elements == null || elements.size() < 0) {
			return null;
		}
		StringBuilder temp = new StringBuilder();
		for (T element : elements) {
			temp.append(OPERATOR_ADD_CHAR).append(element).append(STRING_SPLIT_CHAR);
		}
		return temp.toString();
	}

	/**
	 * 将某个元素序列化成字符串，后续可以将其加入其它字符串
	 * @param element
	 * @return 序列化字符串
	 */
	public static <T> String serializeToString(T element, boolean isRemove) {
		if (element == null) {
			return null;
		}
		char operator = OPERATOR_ADD_CHAR;
		if (isRemove) {
			operator = OPERATOR_MINUS_CHAR;
		}
		return String.valueOf(operator) + element + STRING_SPLIT_CHAR;
	}

	// public static void main(String[] args) {
	// String s = "+1 -1 +-1 --1 +-2";
	// System.out.println(containsElementInString(s, 1));
	// Set<Integer> set = new HashSet<Integer>();
	// System.out.println(convertStringToSet(s, Integer.class, set));
	// System.out.println(set);
	// System.out.println(serializeToString(true, true));
	// }
}
