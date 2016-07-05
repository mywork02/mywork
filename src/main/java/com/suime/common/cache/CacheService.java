package com.suime.common.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.suime.common.cache.support.CASResult;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

/**
 * 缓存 Service
 * @author ice
 */
public interface CacheService {
	/**
	 * 缓存 memcachedClient
	 * @return memcachedClient
	 */
	MemcachedClient getMemcachedClient();

	/**
	 * 获取缓存中的value值
	 * @param key key
	 * @return 对应缓存中的value
	 */
	Object get(String key);

	/**
	 * Set an object in the cache (using the default transcoder) regardless of any
	 * existing value.
	 *
	 * <p>
	 * The {@code exp} value is passed along to memcached exactly as given,
	 * and will be processed per the memcached protocol specification:
	 * </p>
	 *
	 * <p>
	 * Note that the return will be false any time a mutation has not occurred.
	 * </p>
	 *
	 * <blockquote>
	 * <p>
	 * The actual value sent may either be Unix time (number of seconds since
	 * January 1, 1970, as a 32-bit value), or a number of seconds starting from
	 * current time. In the latter case, this number of seconds may not exceed
	 * 60*60*24*30 (number of seconds in 30 days); if the number sent by a client
	 * is larger than that, the server will consider it to be real Unix time value
	 * rather than an offset from current time.
	 * </p>
	 * </blockquote>
	 *
	 * @param key the key under which this object should be added.
	 * @param exp the expiration of this object
	 * @param o the object to store
	 * @return a future representing the processing of this operation
	 * @throws IllegalStateException in the rare circumstance where queue is too
	 *           full to accept any more requests
	 */
	OperationFuture<Boolean> set(String key, int exp, Object o);

	/**
	 * Delete the given key from the cache.
	 *
	 * @param key the key to delete
	 * @return whether or not the operation was performed
	 * @throws IllegalStateException in the rare circumstance where queue is too
	 *           full to accept any more requests
	 */
	OperationFuture<Boolean> delete(String key);

	/**
	 * Same with memcached set method
	 * set an object with the key, specifying the time
	 * @param time      有效时间，以秒为单位
	 * @param key       存放在cache中的key
	 * @param value     存放在cache中的value
	 * @return TODO
	 */
	boolean set(Integer time, String key, Object value);

	/**
	 * set an object with the key, specifying the time
	 * @param timeKey       预先定义的time的值，参考CacheConstantBase.TIME_*
	 * @param key           存放在cache中的key
	 * @param value         存放在cache中的value
	 * @return TODO
	 */
	boolean set(String timeKey, String key, Object value);

	/**
	 * add 
	 * @param time
	 * @param key
	 * @param value
	 * @return boolean
	 */
	boolean add(Integer time, String key, Object value);

	/**
	 * add 
	 * @param timeKey
	 * @param key
	 * @param value
	 * @return boolean
	 */
	boolean add(String timeKey, String key, Object value);

	/**
	 * 将某个元素加入到以String形式存储的set中，如果元素已经存在，则不作操作，
	 * 目前只支持Long, Integer, Double, Float, String,并且String中应不包含+-和空格
	 * @param timeKey
	 * @param key
	 * @param element
	 */
	<T> void addElementToSet(String timeKey, String key, T element);

	/**
	 * 删除cache中key对应的value
	 * @param key 存放在cache中的key
	 * @return 删除是否成功
	 */
	boolean remove(String key);

	/**
	 * 删除cache中key对应的value
	 * @param key 存放在cache中的key
	 */
	void removeAsync(String key);

	/**
	 * 将某个元素从以String形式存储的set中删除，如果元素不存在或者缓存中无内容，则不作操作
	 * @param key
	 * @param element
	 */
	<T> void removeFromSet(String key, T element);

	/**
	 * 统计以String形式存储的set中元素的个数
	 * @param key       缓存中的key值
	 * @return          所有元素的个数，相当于set.size()
	 */
	int countElementNoInSet(String key);

	/**
	 * 通过多个key取值
	 * @param keys
	 * @return map
	 */
	Map<String, Object> getBulk(Collection<String> keys);

	/**
	 * 将以String形式存储的Set以HashSet的方式取出
	 * @param key       缓存中的key
	 * @param clazz     set中每个元素的类型
	 * @return set
	 */
	<T> Set<T> getSet(String key, Class<T> clazz);

	/**
	* 获取cache中指定的key对应的值，以及对应的版本号。更新时，根据版本号可以控制同步
	* @param key 存放在cache中的key
	* @return CASValue 包含casId和实际的值
	*/
	CASResult<Object> getByCAS(String key);

	/**
	* 根据指定的casId来设置cache中指定key对应的内容
	* @param timeKey 参考CacheConstantBase.TIME_*
	* @param key 存放在cache中的key
	* @param value CAS, 包括事先获取的casId以及新的值
	* @return 成功或者失败
	*/
	// boolean setByCAS(String timeKey, String key, CASResult<Object> value);

	/**
	 * 根据指定的casId来设置cache中指定key对应的内容
	 * @param time time
	* @param key 存放在cache中的key
	* @param value CAS, 包括事先获取的casId以及新的值
	* @return 成功或者失败
	 */
	boolean setByCAS(Integer time, String key, CASResult<Object> value);
}
