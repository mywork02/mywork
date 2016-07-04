package com.suime.library.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.StringUtils;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.Student;
import com.suime.context.model.StudentDocument;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.service.PermissionService;
import com.suime.library.service.StudentDocumentService;
import com.suime.library.service.StudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "文档权限接口", tags = "docPermission")
@RestController
@RequestMapping("/docPermission")
public class PermissionRestController {
	
	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentDocumentService studentDocumentService;
	
	/**
	 * studentDocumentService
	 */
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * info
	 *
	 * @param id
	 * @return resultBean
	 */
	@ApiOperation(value = "获取文档信息")
	@RequestMapping(path = "/info", method = { RequestMethod.GET })
	public Object info(@RequestParam(name="id") Long id,
					   @RequestParam(name="invitecode") String invitecode) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		StudentDocument studentDocument= this.studentDocumentService.fetchById(id);
		if(studentDocument==null || studentDocument.getActived().intValue()!=1){
			throw BusinessErrors.getInstance().fileNotFound();
		}
		if(studentDocument.getStudentId()==null){
			throw BusinessErrors.getInstance().paramsError();
		}
		Conds conds = new Conds();
		conds.equal("invitecode", invitecode);
		Student student=studentService.fetchSearchByConds(conds);
		if(studentDocument.getPermission().intValue()==3){//私有文档判断邀请码用户与文档的studentId是否相同
			if(student==null || student.getId()==null){
				throw BusinessErrors.getInstance().userInvalidError();
			}
			if(StringUtils.equals(student.getId().toString(),studentDocument.getStudentId().toString())){
				StudentDocumentDto dto = permissionService.updateAndGetInfo(student.getId(),studentDocument);
				resultBean.setBody(dto);
			}
		}else{
			StudentDocumentDto dto = permissionService.updateAndGetInfo(student.getId(),studentDocument);
			resultBean.setBody(dto);
		}
		return resultBean;
	}

}
