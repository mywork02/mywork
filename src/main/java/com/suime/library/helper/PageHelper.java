/**
 * 
 */
package com.suime.library.helper;

import java.util.List;

import com.confucian.framework.support.Page;

/**
 * @author davidclj
 *
 */
public class PageHelper {

	public static Page generatePage(int pageNum, int pageSize, int total, List<?> data) {
		int start = (pageNum - 1) * pageSize;
		return new Page(start < 0 ? 0 : start, total < 0 ? 0 : total, pageSize < 0 ? 0 : pageSize, data);
	}

}
