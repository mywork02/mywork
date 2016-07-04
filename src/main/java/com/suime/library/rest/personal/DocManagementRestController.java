package com.suime.library.rest.personal;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.support.Page;
import com.confucian.framework.web.AbstractRestController;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.constants.PermissionType;
import com.suime.context.model.DirectoryContentRels;
import com.suime.library.dto.pack.DocPermissionBean;
import com.suime.library.dto.pack.DocumentMoveBean;
import com.suime.library.dto.pack.StudentDocumentBean;
import com.suime.library.service.DirectoryContentRelsService;
import com.suime.library.service.DirectoryService;
import com.suime.library.service.StudentDocumentService;
import com.suime.library.shiro.BaseUserHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SortOrder;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.jdom.DocType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hexu on 2016/1/23.
 */
@Api(description = "个人文档管理", tags = "my,doc,management")
@RestController
@RequestMapping("/my/docManagement")
public class DocManagementRestController extends AbstractRestController {

    /**
     * studentDocumentService
     */
    @Autowired
    private StudentDocumentService studentDocumentService;
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private DirectoryContentRelsService directoryContentRelsService;

    /**
     * 更改文档权限，包括公开和私有
     * 用户公开文档  所有人可以看到文档
     * 楼长公开文档 会将文档显示在楼长的推荐文档里面
     * @param documentId
     * @param permissionStr  including "public" and "private"
     * created by jason he
     * created at 2016/1/22
     */
    @ApiOperation(value="修改文档公开私有的权限")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path="/changePermission", method= {RequestMethod.POST})
    public Object changePermission(@RequestParam(name="documentId") Long documentId, @RequestParam(name="permission") String permissionStr) {
        Byte permission = 1;
        // 1 is public, 0 is private
        switch (permissionStr)
        {
            case PermissionType.DOCUMENT_PUBLIC:
                permission = PermissionType.PERMISSION_PUBLIC;
                break;
            case PermissionType.DOCUMENT_PRIVATE:
                permission = PermissionType.PERMISSION_PRIVATE;
                break;
            default:
                throw BusinessErrors.getInstance().paramsError();
        }

        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        StudentDocumentBean studentDocumentBean = new StudentDocumentBean();
        //Byte permission = 1;
        Long userId = BaseUserHelper.getInstance().getUserId();
        studentDocumentBean.setStudentId(userId);
        studentDocumentBean.setId(documentId);
        studentDocumentBean.setPermission(permission);
        //TODO
        resultBean.setBody(studentDocumentService.updateStudentDocument(studentDocumentBean));
        return resultBean;
    }

    /**
     * 置顶某个文档
     * 文档置顶，用户查看文档的时候置顶的文档显示在最前面，如果是楼长置顶的话，置顶的文档也会显示在推荐文档的最上面
     * @param documentId
     * @param top
     * @return
     * created by jason he
     * created at 2016/1/22
     */
    @ApiOperation(value = "置顶某个文档")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path="/top", method = {RequestMethod.POST})
    public Object top(@RequestParam(name="documentId") Long documentId, @RequestParam(name="top") boolean top) {
    	
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        StudentDocumentBean studentDocumentBean = new StudentDocumentBean();

        // TODO
        Long userId = BaseUserHelper.getInstance().getUserId();
        studentDocumentBean.setStudentId(userId);
        studentDocumentBean.setId(documentId);
        //studentDocumentBean.setTop(top ? 1 : 0);
        resultBean.setBody(studentDocumentService.updateStudentDocument(studentDocumentBean));
        return resultBean;
    }

    /**
     * 转存文档到我的文档
     * created by jason he
     * created at 2016/1/23
     */

    @ApiOperation(value = "保存到我的文档")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path="/saveAsMy", method={RequestMethod.POST})
    public Object saveAsMy(@RequestParam(name = "otherDocumentId") Long otherDocumentId) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        StudentDocumentBean myDocumentBean = new StudentDocumentBean();

        Long userId = BaseUserHelper.getInstance().getUserId();
        myDocumentBean.setStudentId(userId);

        StudentDocumentBean otherDocumentBean = new StudentDocumentBean();
        otherDocumentBean.setId(otherDocumentId);
        return resultBean;
    }
    
    /**
     * 公开文档
     */
    @ApiOperation(value="公开文档")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path="/publicDocPermission", method= {RequestMethod.POST})
    public Object publicDocPermission(@RequestBody DocPermissionBean docPermissionBean){
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long studentId = BaseUserHelper.getInstance().getUserId();
		docPermissionBean.setStudentId(studentId);
		docPermissionBean.setPermission(PermissionType.PERMISSION_PUBLIC);
		studentDocumentService.changePermission(docPermissionBean);
		return resultBean;
    }
    
    /**
     * 私有文档
     */
    @ApiOperation(value="私有文档")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path="/privateDocPermission", method= {RequestMethod.POST})
    public Object privateDocPermission(@RequestBody DocPermissionBean docPermissionBean){
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long studentId = BaseUserHelper.getInstance().getUserId();
		docPermissionBean.setStudentId(studentId);
		docPermissionBean.setPermission(PermissionType.PERMISSION_PRIVATE);
		studentDocumentService.changePermission(docPermissionBean);
		return resultBean;
    }
    
    /**
	 * 将文档移动到目录下面
	 *
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@ApiOperation(value = "将文档移动到目录下面")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/docMove", method = RequestMethod.POST)
	public Object docMove(@RequestBody DocumentMoveBean documentMoveBean) {
		if(documentMoveBean.getIds()==null || documentMoveBean.getIds().length<=0 || documentMoveBean.getDirectoryId()==null){
			throw BusinessErrors.getInstance().paramsError();
		}
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long studentId = BaseUserHelper.getInstance().getUserId();
		documentMoveBean.setStudentId(studentId);
		//查询所移动的文件是否已在目录下面
//		Conds conds = new Conds();
//		conds.equal("directory_id",documentMoveBean.getDirectoryId());
//		conds.in("associated_id",documentMoveBean.getDocs());
//		Sort sort = new Sort("id", OrderType.DESC);
//		List<DirectoryContentRels> dires = directoryContentRelsService.fetchSearchByPage(conds, sort, 0, 0);
//		List<Object> object = Arrays.asList(documentMoveBean.getDocs());  
//		if(dires!=null && dires.size()>0){
//			for(DirectoryContentRels dire : dires){
//				for(Object o : object){
//					if(o.toString().equals(dire.getAssociatedId().toString())){
//						object.remove(o);
//					}
//				}
//			}
//		}
//		documentMoveBean.setDocs(object.toArray());
		studentDocumentService.changeDocsMove(documentMoveBean);
		return resultBean;
	}
	/**
	 * 将目录移动到某目录下面
	 */
	@ApiOperation(value = "将目录移动到目录下面")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/directoryMove", method = RequestMethod.POST)
	public Object direMove(@RequestBody DocumentMoveBean documentMoveBean) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long studentId = BaseUserHelper.getInstance().getUserId();
		documentMoveBean.setStudentId(studentId);
		directoryService.changeDireMove(documentMoveBean);
		return resultBean;
	}
	
}
