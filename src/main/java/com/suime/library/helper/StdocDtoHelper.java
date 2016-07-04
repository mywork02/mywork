package com.suime.library.helper;

import java.sql.Timestamp;

import com.confucian.framework.utils.DateUtil;

/**
 * student document dto helper
 * @author ice
 */
public class StdocDtoHelper {

	/**
	 * zero
	 */
	private static final int INT_ZERO = 0;

	/**
	 * 一天的毫秒数
	 */
	private static final int ONE_DAY_MILLI_SECOND = 86400000;

	/**
	 * 同一天的文档调整系数
	 */
	private static final int ADJUST_NUM_500 = 500;

	/**
	 * 阅读数调整处理
	 * @param id 文档id
	 * @param readCount 阅读数
	 * @param pageCount 页数
	 * @param documentCreatedAt 文档创建时间
	 * @return 阅读数
	 */
	public static Integer processReadCount(final Long id, final Integer readCount, final Integer pageCount, final Timestamp documentCreatedAt) {
		int tempReadCount;
		if (readCount == null) {
			tempReadCount = INT_ZERO;
		}else{
			tempReadCount = readCount;
		}
		Long tempTime = 0L;
		tempTime = DateUtil.getSqlTimestamp().getTime() - documentCreatedAt.getTime();
		Long days = tempTime / ONE_DAY_MILLI_SECOND;
		Integer tempPageCount = pageCount;
		if (tempPageCount == null) {
			tempPageCount = 0;
		}
		Integer temp = PoissonHelper.getReadIncrementSum(days.intValue(), tempPageCount);
		Long idAdjust = id % ADJUST_NUM_500;
		temp += idAdjust.intValue();
		return temp + tempReadCount;
	}

	/**
	 * 打印数调整处理
	 * @param id 文档id
	 * @param printCount 打印数
	 * @param pageCount 页数
	 * @param documentCreatedAt 文档创建时间
	 * @return 打印数
	 */
	public static Integer processPrintCount(final Long id, final Integer printCount, final Integer pageCount, final Timestamp documentCreatedAt) {
		int tempPrintCount;

		if (printCount == null) {
			tempPrintCount = INT_ZERO;
		}else{
			tempPrintCount = printCount;
		}
		Long tempTime = 0L;
		tempTime = DateUtil.getSqlTimestamp().getTime() - documentCreatedAt.getTime();
		Long days = tempTime / ONE_DAY_MILLI_SECOND;
		Integer tempPageCount = pageCount;
		if (tempPageCount == null) {
			tempPageCount = 0;
		}
		Integer temp = PoissonHelper.getPrintIncrementSum(days.intValue(), tempPageCount);
		Long idAdjust = id % ADJUST_NUM_500;
		temp += idAdjust.intValue();
		return temp + tempPrintCount;
	}
}
