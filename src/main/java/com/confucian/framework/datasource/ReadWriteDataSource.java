package com.confucian.framework.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.CollectionUtils;

/**
 * <pre>
 * 读/写动态选择数据库实现
 * 目前实现功能
 *   一写库多读库选择功能，请参考
 *      @see com.confucian.framework.datasource.ReadWriteDataSourceDecision
 *
 * @author Zhang Kaitao
 * @see com.confucian.framework.datasource.ReadWriteDataSourceDecision.DataSourceType
 * <p>
 * 默认按顺序轮询使用读库
 * 默认选择写库
 * <p/>
 * 已实现：一写多读、当写时默认读操作到写库、当写时强制读操作到读库
 * TODO 读库负载均衡、读库故障转移
 * </pre>
 */
public class ReadWriteDataSource extends AbstractDataSource implements InitializingBean {
	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadWriteDataSource.class);

	/**
	 * 读写数据源
	 */
	private DataSource writeDataSource;

	/**
	 * readDataSourceMap
	 */
	private Map<String, DataSource> readDataSourceMap;

	/**
	 * readDataSourceNames
	 */
	private String[] readDataSourceNames;

	/**
	 * readDataSources
	 */
	private DataSource[] readDataSources;
	/**
	 * 只读数据源数量
	 */
	private int readDataSourceCount;

	/**
	 * counter
	 */
	private AtomicInteger counter = new AtomicInteger(1);

	/**
	 * 设置读库（name, DataSource）
	 *
	 * @param readDataSourceMap
	 */
	public void setReadDataSourceMap(Map<String, DataSource> readDataSourceMap) {
		this.readDataSourceMap = readDataSourceMap;
	}

	public void setWriteDataSource(DataSource writeDataSource) {
		this.writeDataSource = writeDataSource;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (writeDataSource == null) {
			throw new IllegalArgumentException("property 'writeDataSource' is required");
		}
		if (CollectionUtils.isEmpty(readDataSourceMap)) {
			throw new IllegalArgumentException("property 'readDataSourceMap' is required");
		}
		readDataSourceCount = readDataSourceMap.size();

		readDataSources = new DataSource[readDataSourceCount];
		readDataSourceNames = new String[readDataSourceCount];

		int index = 0;
		for (Entry<String, DataSource> e : readDataSourceMap.entrySet()) {
			readDataSources[index] = e.getValue();
			readDataSourceNames[index] = e.getKey();
			index++;
		}

	}

	/**
	 * 选择数据源
	 * @return dataSoruce
	 */
	private DataSource determineDataSource() {
		if (ReadWriteDataSourceDecision.isChoiceWrite()) {
			LOGGER.debug("current determine write datasource");
			return writeDataSource;
		}

		if (ReadWriteDataSourceDecision.isChoiceNone()) {
			LOGGER.debug("no choice read/write, default determine write datasource");
			return writeDataSource;
		}
		return determineReadDataSource();
	}

	/**
	 * 选择只读数据源
	 * @return dataSource
	 */
	private DataSource determineReadDataSource() {
		// 按照顺序选择读库
		// TODO 算法改进
		int index = counter.incrementAndGet() % readDataSourceCount;
		if (index < 0) {
			index = -index;
		}

		String dataSourceName = readDataSourceNames[index];

		LOGGER.debug("current determine read datasource : {}", dataSourceName);

		return readDataSources[index];
	}

	@Override
	public Connection getConnection() throws SQLException {
		return determineDataSource().getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return determineDataSource().getConnection(username, password);
	}

}
