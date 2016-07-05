package com.suime.common.cache.support;

import net.spy.memcached.CASValue;

/**
 * casResult 参考自atman
 * CAS(Check and Set)协议
 * @author ice
 * @param <T>
 */
public class CASResult<T> {
	/**
	 * casId
	 */
	private long casId;
	/**
	 * value
	 */
	private Object value;

	/**
	 * constructor
	 * @param value
	 */
	public CASResult(CASValue<T> value) {
		if (value == null) {
			return;
		}
		casId = value.getCas();
		this.value = value.getValue();
	}

	/**
	 * constructor
	 * @param cas cas
	 * @param value value
	 */
	public CASResult(long cas, Object value) {
		this.casId = cas;
		this.value = value;
	}

	public long getCas() {
		return casId;
	}

	public void setCasId(long casId) {
		this.casId = casId;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
