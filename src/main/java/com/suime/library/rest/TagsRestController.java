package com.suime.library.rest;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.context.model.Tags;
import com.suime.library.dto.TagsDto;
import com.suime.library.dto.pack.TagsBean;
import com.suime.library.error.TagsErrors;
import com.suime.library.service.TagsService;
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
 * tagsRestController
 *
 * @author ice
 */
@Api(description = "标签接口", tags = "tags")
@RestController
@RequestMapping("/tags")
public class TagsRestController extends AbstractRestController {

    /**
     * tagsService
     */
    @Autowired
    private TagsService tagsService;

    /**
     * 添加标签（普通用户）
     *
     * @return resultBean
     */
    @ApiOperation(value = "添加标签（普通用户）")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/user/add", method = {RequestMethod.POST})
    public Object addForUser(@RequestBody TagsBean tagsBean) {
        Byte sourceOfNormalUser = (byte) 2;
        Long normalUserId = BaseUserHelper.getInstance().getUserId();
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Byte type = 1;
        tagsBean.setType(type);
        resultBean.setBody(this.tagsService.add(tagsBean, sourceOfNormalUser, normalUserId));
        return resultBean;
    }

    /**
     * 删除标签
     *
     * @return resultBean
     */
    @ApiOperation(value = "删除标签")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/delete/{id}", method = {RequestMethod.POST})
    public Object delete(@PathVariable(value = "id") Long id) {
        Tags tag = this.tagsService.fetchById(id);
        if (tag == null) {
            throw TagsErrors.getInstance().tagsNotFoundError(); // 未找到指定的标签
        }
        this.tagsService.deleteTag(id);
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        return resultBean;
    }

    /**
     * 获取标签详情
     *
     * @return resultBean
     */
    @ApiOperation(value = "获取标签信息")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/info/{id}", method = {RequestMethod.GET})
    public Object getInfo(@PathVariable(value = "id") Long id) {
        Tags tags = this.tagsService.fetchById(id);
        if (tags == null) {
        	throw TagsErrors.getInstance().tagsNotFoundError();// 未找到指定的标签
        }
        TagsDto dto = new TagsDto(tags);
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(dto);
        return resultBean;
    }

    /**
     * 获取列表数据
     *
     * @return resultBean
     */
    @ApiOperation(value = "搜索标签")
    @RequestMapping(path = "/page", method = {RequestMethod.GET})
    public Object getList(@RequestParam(name = "text", required = false) String searchText,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(this.tagsService.getTagList(searchText, page, pageSize));
        return resultBean;
    }

    /**
     * 获取列表数据
     *
     * @return resultBean
     */
    @ApiOperation(value = "根据分类获取标签")
    @RequestMapping(path = "/listByCategory", method = {RequestMethod.GET})
    public Object listByCategory(@RequestParam(name = "categoryId") Long categoryId,
                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Conds conds = new Conds();
        // conds.equal("category_id", categoryId);
        List<TagsDto> list = this.tagsService.fetchDto(conds, null, 1, pageSize);
        resultBean.setBody(list);
        return resultBean;
    }

    /**
     * 首页的tag分类列表,用于搜索框下的list
     *
     * @return resultBean
     */
    @ApiOperation(value = "首页的tag分类列表,用于搜索框下的list")
    @RequestMapping(path = "/listIndex", method = {RequestMethod.GET})
    public Object listIndex(@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        TagsBean tagsBean = new TagsBean();
        Integer type = 9;
        tagsBean.setType(type.byteValue());
        Conds conds = new Conds();
        conds.equal("type", type);
        Byte available = 1;
        conds.equal("actived", available);
        //List<TagsDto> list = this.tagsService.findDtoByItem(tagsBean, pageSize);
        List<TagsDto> list = this.tagsService.fetchDtoSearchByPage(conds, null, 1, pageSize);
        resultBean.setBody(list);
        return resultBean;
    }

    /**
     * 用户文档使用到的标签
     *
     * @return resultBean
     */
    @ApiOperation(value = "根据用户,获取用户文档使用到的标签")
    @RequestMapping(path = "/listByStudent/{studentId}", method = {RequestMethod.GET})
    public Object listByStudent(@PathVariable("studentId") Long studentId,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        List<TagsDto> list = this.tagsService.findDtoByStudentId(studentId, pageSize);
        resultBean.setBody(list);
        return resultBean;
    }

    /**
     * 用户文档使用到的标签
     *
     * @return resultBean
     */
    @ApiOperation(value = "根据楼栋,获取楼长用户文档使用到的标签")
    @RequestMapping(path = "/listByBuilding/{buildingId}", method = {RequestMethod.GET})
    public Object listByBuilding(@PathVariable("buildingId") Long buildingId,
                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        List<TagsDto> list = this.tagsService.findDtoByBuildingId(buildingId, pageSize);
        resultBean.setBody(list);
        return resultBean;
    }
}
