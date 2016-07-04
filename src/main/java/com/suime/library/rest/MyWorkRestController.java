package com.suime.library.rest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractRestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * SendDocRestController
 *
 * @author david
 */
@Api(description = "发送文档接口", tags = "senddoc")
@RestController
@RequestMapping("/mywork")
public class MyWorkRestController extends AbstractRestController {

	/**
	 * 测试接口
	 * @return resultBean
	 */
	@ApiOperation(value = "测试接口")
	@RequiresAuthentication
	@RequestMapping(path = "/test", method = RequestMethod.GET)
	public Object pushSendDocMsg() {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}

}
