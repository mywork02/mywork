package com.suime.common.shiro;

import com.suime.common.support.Configure;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * shiro里默认的sessionIdgenrator是使用UUID.randomUUID生成，效率不高
 *
 * @author ice
 */
public class DefaultSessionIdGenerator implements SessionIdGenerator {

    /**
     * 使用 UUID 和 项目代码结合生成id
     *
     * @see SessionIdGenerator#generateId(Session)
     */
    @Override
    public Serializable generateId(Session arg0) {
        if (arg0.getId() == null) {
            String generatedId = UUID.randomUUID().toString().replaceAll("-", "");
            return Configure.getPropertyValue("project.token.prefix", "") + generatedId;
        } else {
            return arg0.getId();
        }
    }

//    /**
//     * 使用StringUtil中的uuid的生成算法来生成id
//     *
//     * @see SessionIdGenerator#generateId(Session)
//     */
//    @Override
//    public Serializable generateId(Session arg0) {
//        if (arg0.getId() == null) {
//            return StringUtil.getUUID().replaceAll("-", "");
//        } else {
//            return arg0.getId();
//        }
//    }

}
