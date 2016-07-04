package com.suime.library.rest;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.support.Page;
import com.confucian.framework.utils.StringUtils;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.common.shiro.support.StringUtil;
import com.suime.constants.PermissionType;
import com.suime.constants.SourceType;
import com.suime.context.model.Directory;
import com.suime.context.model.DirectoryContentRels;
import com.suime.library.dto.DirectoryDto;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.dto.pack.DirectoryBean;
import com.suime.library.dto.pack.DocPermissionBean;
import com.suime.library.dto.pack.SearchBean;
import com.suime.library.error.DirectoryErrors;
import com.suime.library.service.DirectoryContentRelsService;
import com.suime.library.service.DirectoryService;
import com.suime.library.service.StudentDocumentService;
import com.suime.library.shiro.BaseUserHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.web.AbstractRestController;

import java.util.List;

/**
 * directoryRestController
 *
 * @author ice
 */
@Api(description = "目录接口", tags = "directory")
@RestController
@RequestMapping("/directory")
public class DirectoryRestController extends AbstractRestController {

	@Autowired
	private DirectoryService directoryService;

	@Autowired
	private StudentDocumentService studentDocmentService;

	@Autowired
	private DirectoryContentRelsService directoryContentRelsService;

	@ApiOperation(value = "仅获取子目录")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/subdirectory", method = { RequestMethod.GET })
	public Object subdirectory(@RequestParam(name = "parent", required = false) Long parentId) {
		Long studentId = BaseUserHelper.getInstance().getUserId();
		// boolean permission = false;
		// if (studentId == null && studentToId == null) {
		// throw BusinessErrors.getInstance().userOfflineError();
		// }
		// if (studentToId == null && studentId != null) {
		// studentToId = studentId;
		// permission = true;//有权限查询所有，否则只可查看公开的目录或文档
		// }
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Sort sort = new Sort("name", OrderType.ASC);
		if(parentId==null || StringUtils.isBlank(parentId.toString())){
			Conds condsc = new Conds();
			condsc.equal("actived", 1);
			condsc.equal("student_id", studentId);
			condsc.equal("parent_id", 0);
			List<DirectoryDto> directoryChilds = this.directoryService.fetchDtoSearchByPage(condsc, sort, 1, 0);
			DirectoryDto directoryDto = new DirectoryDto();
			directoryDto.setSubDirectorys(directoryChilds);
			resultBean.setBody(directoryDto);
		}else{
			Conds conds = new Conds();
			conds.equal("actived", 1);
			conds.equal("student_id", studentId);
			// if (!permission) {
			// conds.equal("permission", PermissionType.PERMISSION_PUBLIC);
			// }
			conds.equal("id", parentId);// 传入parent参数查询此目录信息及下面的目录与文档
			List<DirectoryDto> directoryDtos = this.directoryService.fetchDtoSearchByPage(conds, sort, 1, 0);// 获取此目录的信息
			if (directoryDtos == null || directoryDtos.isEmpty()) {
				throw BusinessErrors.getInstance().paramsError();
			}
			DirectoryDto directoryDto = directoryDtos.get(0);
			Conds condsc = new Conds();
			condsc.equal("actived", 1);
			condsc.equal("student_id", studentId);
			condsc.equal("parent_id", parentId);
			List<DirectoryDto> directoryChilds = this.directoryService.fetchDtoSearchByPage(condsc, sort, 1, 0);
			if (directoryChilds != null && directoryChilds.size() > 0) {
				directoryDto.setSubDirectorys(directoryChilds);
			}
			resultBean.setBody(directoryDto);
		}
		return resultBean;
	}

	/**
	 * 分页获取目前下的内容
	 * */
	@ApiOperation(value = "分页获取目录下的内容")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/pageList", method = { RequestMethod.GET })
	public Object list(@RequestParam(name = "page", defaultValue = "1") Integer curPage, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam(name = "directoryId", required = false) Long directoryId) {
		Long studentId = BaseUserHelper.getInstance().getUserId();
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Page direPage =directoryContentRelsService.fetchSearchByPageIntro(studentId,curPage, pageSize, directoryId);
		resultBean.setBody(direPage);
		return resultBean;
	}

	/**
	 *获取目录信息
	 */
	@ApiOperation(value = "获取目录信息")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/info/{id}", method = { RequestMethod.GET })
	public Object info(@PathVariable("id") Long id) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		if (id == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		DirectoryBean directoryBean = new DirectoryBean();
		directoryBean.setId(id);
		directoryBean.setStudentId(studentId);
		DirectoryDto dire = directoryService.directoryDtoInfo(directoryBean);
		resultBean.setBody(dire);
		return resultBean;
	}

	@ApiOperation(value = "添加目录")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/add", method = { RequestMethod.POST })
	public Object add(@RequestBody DirectoryBean directoryBean) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		directoryBean.setStudentId(studentId);
		directoryBean.setSource(SourceType.USER_CREATED_DIRECTOR);
		directoryService.addByItem(directoryBean);
		return resultBean;
	}

	@ApiOperation(value = "删除目录")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/delete/{id}", method = { RequestMethod.DELETE })
	public Object delete(@PathVariable("id") Long id) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		DirectoryBean directoryBean = new DirectoryBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		directoryBean.setId(id);
		directoryBean.setStudentId(studentId);
		directoryService.removeById(directoryBean);
		return resultBean;
	}
	
	@ApiOperation(value = "仅删除目录")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/deleteDire/{id}", method = { RequestMethod.DELETE })
	public Object deleteDire(@PathVariable("id") Long id){
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		DirectoryBean directoryBean = new DirectoryBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		directoryBean.setId(id);
		directoryBean.setStudentId(studentId);
		directoryService.removeByDirectory(directoryBean);
		return resultBean;
	}

	@ApiOperation(value = "修改目录名称")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public Object updateName(@RequestBody DirectoryBean directoryBean) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long studentId = BaseUserHelper.getInstance().getUserId();
		directoryBean.setStudentId(studentId);
		directoryService.updateNameByItem(directoryBean);
		return resultBean;
	}

	/**
	 * 目录公开
	 *
	 * @param docPermissionBean 
	 * @return
	 */
	@ApiOperation(value = "公开目录")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/publicDire", method = { RequestMethod.POST })
	public Object changePermission(@RequestBody DocPermissionBean docPermissionBean) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long studentId = BaseUserHelper.getInstance().getUserId();
		docPermissionBean.setStudentId(studentId);
		directoryService.changePermission(docPermissionBean);
		return resultBean;
	}

	/***
	 * 取消目录公开
	 */
	@ApiOperation(value = "私有目录(取消公开)")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/privateDire", method = { RequestMethod.POST })
	public Object privateDire(@RequestBody DocPermissionBean docPermissionBean) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long studentId = BaseUserHelper.getInstance().getUserId();
		docPermissionBean.setStudentId(studentId);
		directoryService.changePrivateDire(docPermissionBean);
		return resultBean;
	}
}
