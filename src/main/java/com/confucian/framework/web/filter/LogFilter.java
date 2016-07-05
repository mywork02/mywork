package com.confucian.framework.web.filter;

import com.confucian.framework.support.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.UUID;

/**
 * 添加rqid 方便查找错误
 * Created by ice on 18/3/2016.
 */
public class LogFilter implements Filter {

    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        InetAddress local = InetAddress.getLocalHost();
        byte[] address = local.getAddress();
        String rqid = "";
        if (local instanceof Inet4Address) {
            if (address != null && address.length > 3) {
                rqid = (address[2] & 0xFF) + "-" + (address[3] & 0xFF) + "-";
            }
        }
        rqid += UUID.randomUUID().toString();
        request.setAttribute(Constants.KEY_RQID, rqid);
        this.logger.info(Constants.KEY_RQID + ":" + rqid);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
