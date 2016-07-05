package com.confucian.framework.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 读/写动态数据库 决策者
 * 根据DataSourceType是write/read 来决定是使用读/写数据库
 * 通过ThreadLocal绑定实现选择功能
 * </pre>
 *
 * @author Zhang Kaitao
 */
public class ReadWriteDataSourceDecision {

	/**
	 * DataSourceType
	 * @author ice
	 */
	public enum DataSourceType {
		/**
		 * 读写数据源
		 */
		write,
		/**
		 * 只读数据源
		 */
		read
	}

	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadWriteDataSourceDecision.class);

	/**
	 * holder
	 */
	private static final ThreadLocal<DataSourceType> DATASOURCE_HOLDER = new ThreadLocal<DataSourceType>();

	/**
	 * 切换读写数据源
	 */
	public static void markWrite() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("-------:DataSourceType.write");
		}
		DATASOURCE_HOLDER.set(DataSourceType.write);
	}

	/**
	 * 切换到只读数据源
	 */
	public static void markRead() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("-------:DataSourceType.read");
		}
		DATASOURCE_HOLDER.set(DataSourceType.read);
	}

	/**
	 * 重置数据源为空
	 */
	public static void reset() {
		DATASOURCE_HOLDER.set(null);
	}

	public static boolean isChoiceNone() {
		return null == DATASOURCE_HOLDER.get();
	}

	public static boolean isChoiceWrite() {
		return DataSourceType.write == DATASOURCE_HOLDER.get();
	}

	public static boolean isChoiceRead() {
		return DataSourceType.read == DATASOURCE_HOLDER.get();
	}

}
