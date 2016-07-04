package com.suime.library.rest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.support.Page;
import com.confucian.framework.utils.StringUtils;
import com.confucian.framework.web.AbstractRestController;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.Favorite;
import com.suime.library.dto.pack.FavoriteBean;
import com.suime.library.dto.pack.MarkRecordBean;
import com.suime.library.service.FavoriteService;
import com.suime.library.service.MarkRecordService;
import com.suime.library.service.StudentDocumentService;
import com.suime.library.shiro.BaseUserHelper;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * favoriteRestController
 *
 * @author ice
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteRestController extends AbstractRestController {

    @Autowired
    private FavoriteService favoriteService;
    
    @Autowired
    private StudentDocumentService studentDocumentService;
    
    @Autowired
    private MarkRecordService markRecordService;

    @ApiOperation(value = "添加收藏夹")
    @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header", dataType = "String")
    @RequiresAuthentication
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody FavoriteBean favoriteBean) {

        Long studentId = BaseUserHelper.getInstance().getUserId();
        if (studentId == null){
            throw BusinessErrors.getInstance().userOfflineError();
        }
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        favoriteBean.setStudentId(studentId);
        resultBean.setBody(favoriteService.add(favoriteBean));
        return resultBean;
    }

    @ApiOperation(value = "修改名称")
    @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header", dataType = "String")
    @RequiresAuthentication
    @RequestMapping(path = "/changeName", method = RequestMethod.POST)
    public Object updateName(@RequestBody FavoriteBean favoriteBean) { 
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Long studentId = BaseUserHelper.getInstance().getUserId();
        if (studentId == null) {
            throw BusinessErrors.getInstance().userOfflineError();
        }
        favoriteBean.setStudentId(studentId);
        resultBean.setBody(favoriteService.updateByItem(favoriteBean));
        return resultBean;
    }

    @ApiOperation(value = "获取所有收藏夹")
    @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header", dataType = "String")
    @RequiresAuthentication
    @RequestMapping(path = "/personal", method = RequestMethod.GET)
    public Object personal(@RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize",defaultValue = "20") Integer pageSize) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Long studentId = BaseUserHelper.getInstance().getUserId();
        if (studentId == null) {
            throw BusinessErrors.getInstance().userOfflineError();
        }
        resultBean.setBody(favoriteService.fetchPersonal(studentId, page, pageSize));
        return resultBean;
    }


    @ApiOperation(value = "删除收藏夹")
    @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header", dataType = "String")
    @RequiresAuthentication
    @RequestMapping(path = "/remove/{id}", method = RequestMethod.DELETE)
    public Object remove(@PathVariable(value = "id") Long id) {
        CommonResultBean resultBean = new CommonResultBean();
        Long studentId = BaseUserHelper.getInstance().getUserId();
        if (studentId == null) {
            throw BusinessErrors.getInstance().userOfflineError();
        }
        favoriteService.remove(id, studentId);
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        return resultBean;
    }
    
    /**
     * 
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "获取收藏夹信息")
    @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header", dataType = "String")
    @RequiresAuthentication
    @RequestMapping(path = "/info/{id}", method = RequestMethod.GET)
    public Object info(@PathVariable(value = "id") Long id){
    	 CommonResultBean resultBean = new CommonResultBean();
         Long studentId = BaseUserHelper.getInstance().getUserId();
         if (studentId == null) {
             throw BusinessErrors.getInstance().userOfflineError();
         }
         Byte actived = 1;
         Conds conds = new Conds();
         conds.equal("actived",actived);
         conds.equal("student_id",studentId);
         conds.equal("id",id);
         Favorite favorite= favoriteService.fetchSearchByConds(conds);
         resultBean.setBody(favorite);
         resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
         return resultBean;
    }
    
    
    @ApiOperation(value = "获取收藏夹下的内容")
    @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header", dataType = "String")
    @RequiresAuthentication
    @RequestMapping(path = "/list",method = RequestMethod.GET)
    public Object listInFavorite(@RequestParam(name = "id",required=false) Long id,
                                 @RequestParam(name = "page",defaultValue = "1") Integer page,
                                 @RequestParam(name = "pageSize",defaultValue = "20") Integer pageSize){
    CommonResultBean resultBean = new CommonResultBean();
    resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
    Long studentId = BaseUserHelper.getInstance().getUserId();
    if(studentId==null){
    	throw BusinessErrors.getInstance().userOfflineError();
    }
   
	MarkRecordBean markRecordBean = new MarkRecordBean();
	if(id!=null){
		Favorite favorite=favoriteService.fetchById(id);
			if(favorite==null || favorite.getActived().intValue()!=1){
				   throw BusinessErrors.getInstance().paramsError();   
		}
		markRecordBean.setFavoriteId(id);
		
	}
	markRecordBean.setStudentId(studentId);
	Page pageList = markRecordService.fetchMarkStudentDocumentPage(markRecordBean, page, pageSize);
    //Page pagelist = favoriteService.fetchfavoritedoc(id, studentId, page, pageSize);
    resultBean.setBody(pageList);
    return resultBean;
    }
}
