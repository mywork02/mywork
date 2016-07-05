/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.suime.common.shiro;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;

/**
 * Shiro {@code CacheManager} implementation utilizing the Ehcache framework for all cache functionality.
 * <p/>
 * This class can {@link #setCacheManager(net.sf.ehcache.CacheManager) accept} a manually configured
 * {@link net.sf.ehcache.CacheManager net.sf.ehcache.CacheManager} instance,
 * or an {@code ehcache.xml} path location can be specified instead and one will be constructed. If neither are
 * specified, Shiro's failsafe  code <a href="./ehcache.xml">ehcache.xml</a>} file will be used by default.
 * <p/>
 * This implementation requires EhCache 1.2 and above. Make sure EhCache 1.1 or earlier
 * is not in the classpath or it will not work.
 * <p/>
 * Please see the <a href="http://ehcache.sf.net" target="_top">Ehcache website</a> for their documentation.
 *
 * @see <a href="http://ehcache.sf.net" target="_top">The Ehcache website</a>
 * @since 0.2
 */
public class BaseCacheManager implements CacheManager, Initializable, Destroyable {

	/**
	 * Default no argument constructor
	 */
	public BaseCacheManager() {
	}

	/**
	 * Loads an existing EhCache from the cache manager, or starts a new cache if one is not found.
	 *
	 * @param name the name of the cache to load/create.
	 * @return baseCache
	 */
	@SuppressWarnings("unchecked")
	public final <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return BaseCache.getInstance();
	}

	@Override
	public void destroy() throws Exception {
	}

	@Override
	public void init() throws ShiroException {
	}

}
