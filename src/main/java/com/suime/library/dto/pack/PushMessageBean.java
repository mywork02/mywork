package com.suime.library.dto.pack;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

/**
 * Created by ice on 11/3/2016.
 */
@ApiModel
public class PushMessageBean {

    /**
     * 类型
     */
    @ApiParam(value = "类型")
    private String type;

    /**
     * 推送内容
     */
    @ApiParam(value = "推送内容")
    private String body;

    /**
     * deviceToken
     */
    @ApiParam(value = "deviceToken")
    private String deviceToken;

    /**
     * extra
     */
    @ApiParam(value = "extra")
    private JSONObject extra;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public JSONObject getExtra() {
        return extra;
    }

    public void setExtra(JSONObject extra) {
        this.extra = extra;
    }
}
