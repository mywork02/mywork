package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.StudentDocument;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.dto.pack.DocPermissionBean;
import com.suime.library.dto.pack.DocumentMoveBean;
import com.suime.library.dto.pack.SearchAdminBean;
import com.suime.library.dto.pack.SearchBean;
import com.suime.library.dto.pack.StudentDocumentBean;
import com.suime.library.service.support.OrderFieldEnum;

import java.util.List;

/**
 * studentDocumentService
 * Created by ice 17/02/2016.
 */
public interface StudentDocumentService extends GenericService<StudentDocument> {
	
	
	
	/**
	 * 更新文档及关联表
	 */
	void updateByRels(StudentDocument studentDocument);

    /**
     * 文档查询 返回Page 对象
     *
     * @param searchBean searchBean
     * @param curPage    curPage
     * @param pageSize   pageSize
     * @return page
     */
    Page pageByItem(SearchBean searchBean, int curPage, int pageSize);

    /**
     * 文档查询
     *
     * @param searchBean pageSize
     * @param pageSize   pageSize
     * @return page
     */
    List<StudentDocumentDto> limitListByItem(SearchBean searchBean, int pageSize);

    /**
     * info
     *
     * @param id        id
     * @param studentId studentId
     * @param isAdmin   isAdmin
     * @return studentDto
     */
    StudentDocumentDto updateAndGetInfo(Long id, Long studentId, Boolean isAdmin);

    /**
     * 添加用户上传记录
     *
     * @param studentDocumentBean 包装类
     * @return studentDocument
     */
    StudentDocumentDto addStudentDocument(StudentDocumentBean studentDocumentBean);

    /**
     * 更新文档信息
     *
     * @param studentDocumentBean 包装类
     * @return studentDocument
     */
    StudentDocumentDto updateStudentDocument(StudentDocumentBean studentDocumentBean);

    /**
     * 分页获取用户个人文档
     *
     * @param studentDocumentBean studentDocumentBean
     * @param page                page
     * @param pageSize            pageSize
     * @return page StudentDocumentDto
     */
    Page pagePersonalDocumentByItem(StudentDocumentBean studentDocumentBean, int page, int pageSize);

    /**
     * 分页获取用户个人文档,包含简介 intro
     *
     * @param studentDocumentBean studentDocumentBean
     * @param page                page
     * @param pageSize            pageSize
     * @return page StudentDocumentDto
     */
    Page pagePersonalDocumentWidthIntro(StudentDocumentBean studentDocumentBean, int page, int pageSize);

    /**
     * 个人文档，包含目录结构
     *
     * @param studentDocumentBean studentDocumentBean
     * @param page                page
     * @param pageSize            pageSize
     * @return page StudentDocumentDto
     */
    Page personalDocumentDir(StudentDocumentBean studentDocumentBean, int page, int pageSize);

    /**
     * 分页获取我的文档，包括上传和收藏
     *
     * @param studentDocumentBean studentDocumentBean
     * @param page                page
     * @param pageSize            pageSize
     * @return page StudentDocumentDto
     */
    Page pagePersonalDocumentAll(StudentDocumentBean studentDocumentBean, int page, int pageSize, String sortField, OrderType orderType);

    /**
     * 相关推荐文档
     *
     * @param id             id
     * @param orderFieldEnum orderFieldEnum
     * @param pageSize       pageSize
     * @return list studentDocumentDto
     */
    List<StudentDocumentDto> fetchRecommendDocuments(Long id, OrderFieldEnum orderFieldEnum, int pageSize);

    /**
     * 根据id更改文档审核状态
     *
     * @param id
     */
    void changeReviewStateById(Long id, Byte reviewState);

    /**
     * 根据idList更改文档审核状态
     *
     * @param ids
     */
    void changeReviewStateByList(List<Long> ids, Byte reviewState);

    /**
     * 分页获取所有文档
     *
     * @param studentDocumentBean
     * @param page
     * @param pageSize
     * @return page
     */
    Page pageAllDocumentByItem(StudentDocumentBean studentDocumentBean, int page, int pageSize, String sortField, OrderType orderType);

    /**
     * 管理员搜索文档,根据日期或文档名模糊搜索
     *
     * @param searchAdminBean
     * @param page
     * @param pageSize
     * @return page
     */
    Page adminSearchByItem(SearchAdminBean searchAdminBean, int page, int pageSize);
    
    /**
     * 更新文档所在目录
     * @param documentBean
     */
    void changeDocsMove(DocumentMoveBean documentBean);
    
    /**
     * 批量修改文档权限
     * @param docPermissionBean
     */
    void changePermission(DocPermissionBean docPermissionBean);

    /**
     * 获取所有文档
     */
    List<StudentDocumentDto> getAllStudentDocument();
    
    /**
     * 专题获取文档
     */
     public Page pageByItemDissertation(SearchBean searchBean, int curPage, int pageSize);
}
