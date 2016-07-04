package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.StudentDocument;
import com.suime.library.dto.StudentDocumentDto;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * studentDocumentMapper
 * Created by ice 17/02/2016.
 */
public interface StudentDocumentMapper extends GenericMapper<StudentDocument> {

	/**
	 * 搜索查询总数
	 *
	 * @return count
	 */
	int countSearch(Map<String, Object> params);

	/**
	 * 分页dto查询
	 *
	 * @param params
	 * @return list student document dto
	 */
	List<StudentDocumentDto> fetchDtoSearch(Map<String, Object> params);

	/**
	 * 分页dto查询学生上传的文档
	 *
	 * @param params
	 * @return list student document dto
	 */
	List<StudentDocumentDto> personalDtoSearch(Map<String, Object> params);

	/**
	 * 分页dto查询学生上传的文档,包含有简介
	 *
	 * @param params
	 * @return list student document dto
	 */
	List<StudentDocumentDto> personalDtoWidthIntro(Map<String, Object> params);

	/**
	 * 分页dto查询学生上传的文档,包括收藏的文档
	 *
	 * @param params
	 * @return list student document dto
	 */
	List<StudentDocumentDto> personalDtoWidthMarked(Map<String, Object> params);

	/**
	 * 查询总数,学生上传的文档,包括收藏的文档
	 *
	 * @return count student mark
	 */
	int countWidthMarked(Map<String, Object> params);

	/**
	 * 更新记录数
	 *
	 * @param id
	 * @param countField
	 * @param value
	 * @return rows
	 */
	int updateCountById(@Param("id") Long id, @Param("countField") String countField, @Param("value") Integer value);

	/**
	 * 批量更新文档审核状态
	 *
	 * @param params 条件
	 * @return rows
	 */
	int batchUpdateReviewState(Map<String, Object> params);

	/**
	 * 查询总数,管理员查询
	 *
	 * @return count
	 */
	int countAdminSearch(Map<String, Object> params);

	/**
	 * 分页dto查询,管理员查询
	 *
	 * @param params
	 * @return list student document dto
	 */
	List<StudentDocumentDto> adminSearchByPage(Map<String, Object> params);

	/**
	 * 根据目录软删除文档
	 *
	 * @param id
	 * @return count of removed
	 */
	int removeByDirectory(Serializable id);
	
	/**
	 * 批量更新文档所在目录
	 * */
	int updatedocsdore(Map<String,Object> params);
	
	/**
	 * 批量修改文档权限
	 */
	void changepermission(Map<String,Object> params);
	
	/**
	 * 专题首页搜索文档
	 * */
	List<StudentDocumentDto> fetchDtoSearchDissertation(Map<String, Object> params);
	
	/**
	 * 专题首页搜索文档数量
	 */
	int fetchDtoSearchDissertationCount(Map<String, Object> params);
}
