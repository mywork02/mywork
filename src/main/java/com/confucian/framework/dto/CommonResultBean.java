package com.confucian.framework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 通用返回结果dto
 *
 * @author ice
 */
public class CommonResultBean {

    /**
     * 标识,1为成功,0:为失败
     */
    private int result;

    /**
     * 返回的数据
     */
    @JsonProperty("body")
    private Object body;

    /**
     * request id,只有在抛异常的情况下会使用到的.
     */
    private String rqid;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    /**
     * getBody
     *
     * @return body
     */
    public Object getBody() {
        if (body == null) {
            body = "";
        }
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getRqid() {
        return rqid;
    }

    public void setRqid(String rqid) {
        this.rqid = rqid;
    }
}
