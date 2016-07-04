package com.suime.library.helper;

/**
 * Created by ice on 14/1/2016.
 */
public class PoissonHelper {

	/**
	 * lamda单位时间内随机事件的平均发生率
	 */
	private static double lamda = 8;

	/**
	 * 阅读量放大系数
	 */
	private static final double READ_FACTOR = 30000;

	/**
	 * 收藏量放大系数
	 */
	@SuppressWarnings("unused")
	private static final double COLLECT_FACTOR = 2800;

	/**
	 * 打印量放大系数
	 */
	private static final double PRINT_FACTOR = 2000;

	/**
	 * 阅读增量
	 * @param interval
	 * @return read increment
	 */
	public static int getReadIncrement(int interval, int adjust) {
		int result = (int) ((READ_FACTOR + adjust) * getPoissonIncrement(interval, lamda));
		return result;
	}

	/**
	 * 阅读增量和
	 * @param interval
	 * @return read increment sum
	 */
	public static int getReadIncrementSum(int interval, int adjust) {
		double result = 0;
		for (int i = 1; i <= interval; i++) {

			result += getReadIncrement(i, adjust);
		}
		return (int) result;
	}

	// /**
	// * 收藏增量
	// *
	// * @param interval
	// * @return
	// */
	// public static int getCollectIncrement(int interval) {
	// int result = (int) (collectFactor * getPoissonIncrement(interval, lamda));
	// return result;
	// }
	//
	// /**
	// * 收藏增量和
	// *
	// * @param interval
	// * @return
	// */
	// public static int getCollectIncrementSum(int interval) {
	// double result = 0;
	// for (int i = 1; i <= interval; i++) {
	//
	// result += getCollectIncrement(i);
	// }
	// return (int) result;
	// }

	/**
	 * 打印增量
	 * @param interval
	 * @return print increment
	 */
	public static int getPrintIncrement(int interval, int adjust) {
		int result = (int) ((PRINT_FACTOR + adjust) * getPoissonIncrement(interval, lamda));
		return result;
	}

	/**
	 * 打印增量和
	 * @param interval
	 * @return print increment sum
	 */
	public static int getPrintIncrementSum(int interval, int adjust) {
		double result = 0;
		for (int i = 1; i <= interval; i++) {

			result += getPrintIncrement(i, adjust);
		}
		return (int) result;
	}

	/**
	 * 泊松概率
	 *
	 * @param k     单位时间
	 * @param lamda 单位时间内随机事件的【平均发生率】
	 * @return 单位时间的泊松概率
	 */
	public static double getPoissonIncrement(int k, double lamda) {

		double factorial = 1; // k的阶乘
		for (int i = 1; i <= k; i++) {
			factorial *= i;
		}
		double poisson = Math.pow(lamda, k) / (factorial * Math.pow(Math.E, lamda));
		return poisson;
	}

	/**
	 * 测试函数
	 *
	 * @param args
	 */
	/*
	public static void main(String[] args) {
	    //最近30天
	    System.out.println("时间段序号          阅读增量          阅读增量和          打印增量          打印增量和          泊松概率");
	    for (int i = 1; i <= 30; i++) {
	        System.out.println(i + "          " +
	                PoissonHelper.getReadIncrement(i, 0) + "          " +
	                PoissonHelper.getReadIncrementSum(i, 0) + "          " +
	                PoissonHelper.getPrintIncrement(i, 0) + "          " +
	                PoissonHelper.getPrintIncrementSum(i, 0) + "          " +
	                PoissonHelper.getPoissonIncrement(i, 10));
	    }
	}
	*/
}
