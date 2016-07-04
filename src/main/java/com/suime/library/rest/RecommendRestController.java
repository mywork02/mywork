package com.suime.library.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractRestController;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.service.DocRecommendService;
import com.suime.library.service.UserRecommendService;
import com.suime.library.shiro.BaseUserHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 推荐相关
 * @author ice
 */
@Api(description = "推荐相关", tags = "recommend")
@RestController
@RequestMapping("/recommend")
public class RecommendRestController extends AbstractRestController {

	/**
	 * docRecommendService
	 */
	@Autowired
	private DocRecommendService docRecommendService;

	/**
	 * userRecommendService
	 */
	@Autowired
	private UserRecommendService userRecommendService;

	/**
	 * 猜你喜欢
	 * @param pageSize
	 * @return resultBean
	 */
	@ApiOperation(value = "猜你喜欢")
	@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header")
	@RequestMapping(path = "/guessulike", method = { RequestMethod.GET })
	public Object guessYouLike(@RequestParam(name = "pageSize", defaultValue = "3") final Integer pageSize) {
		Long userId = BaseUserHelper.getInstance().getUserId();
		if (userId == null) {
			userId = 0L;
		}
		int tempPageSize = pageSize;
		if (pageSize.intValue() > 10) {
			tempPageSize = 10;
		}
		List<StudentDocumentDto> list = userRecommendService.fetchRecommendDocuments(userId, tempPageSize);
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		resultBean.setBody(list);
		return resultBean;
	}

	/**
	 * 相关推荐文档，代码整理，从stdoc 移植过来的，后续产品中调用该接口
	 * @return resultBean
	 */
	@ApiOperation(value = "相关推荐文档，代码整理，从stdoc 移植过来的，后续产品中调用该接口")
	@RequestMapping(path = "/recommend/{id}", method = { RequestMethod.GET })
	public Object recommend(@PathVariable("id") Long id, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long userId = BaseUserHelper.getInstance().getUserId();
		List<StudentDocumentDto> list = docRecommendService.fetchRecommendDocuments(id, userId, pageSize);
		resultBean.setBody(list);
		return resultBean;
	}
}
