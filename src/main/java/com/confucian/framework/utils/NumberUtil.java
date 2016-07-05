package com.confucian.framework.utils;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * NumberUtil
 * @author ice
 */
public class NumberUtil extends NumberUtils {
	
	/**
	 * min radix
	 */
	public static final int MIN_RADIX = 2;
	
	/**
	 * max radix
	 */
	public static final int MAX_RADIX = 30;
	
	/**
	 * digits
	 */
	static final char[] DIGITS = {
	        '2' , '3' , '4' , '5' ,
	        '6' , '7' , '8' , 'a' , 'b' ,
	        'c' , 'd' , 'e' , 'f' , 'h' ,
	        'i' , 'j' , 'k' , 'm' , 'n' ,
	        'p' , 'q' , 'r' , 's' , 't' ,
	        'u' , 'v' , 'w' , 'x' , 'y' , 'z'
	    };
	
	/**
	 * 进制转换
	 * @param i
	 * @param radix
	 * @return 
	 */
    public static String toString(long i, int radix) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX){
            radix = 10;
        }
        if (radix == 10){
            return Long.valueOf(i).toString();
        }
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);

        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = DIGITS[(int)(-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = DIGITS[(int)(-i)];

        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, (65 - charPos));
    }
}
