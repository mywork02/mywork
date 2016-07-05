package me.sui.framework.hessian;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * @author david.cui
 * 
 */

public class HessianServerClientHolder {

	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> c, String url) throws Exception {
		HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
		hessianProxyFactory.setOverloadEnabled(true);
		T t = (T) hessianProxyFactory.create(c, url);
		return t;
	}

}
