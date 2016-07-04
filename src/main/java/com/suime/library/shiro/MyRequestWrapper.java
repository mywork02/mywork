package com.suime.library.shiro;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by ice on 8/1/2016.
 */
public class MyRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * body
	 */
    private final String body;

    /**
     * constructor
     * @param request
     * @throws IOException
     */
    public MyRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        Charset encoding = Charset.forName("UTF-8");
        byte[] bytes = IOUtils.toByteArray(request.getReader(), encoding);
        body = new String(bytes);
    }

    /**
     * constructor
     * @param request
     * @param encoding
     * @throws IOException
     */
    public MyRequestWrapper(HttpServletRequest request, Charset encoding) throws IOException {
        super(request);
        byte[] bytes = IOUtils.toByteArray(request.getReader(), encoding);
        body = new String(bytes);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return this.body;
    }
}
