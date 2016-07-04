package com.suime.library.service;

import java.util.List;

import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.UserRecommend;
import com.suime.library.dto.StudentDocumentDto;

/**
 * userRecommendService
 * Created by ice 13/05/2016.
 */
public interface UserRecommendService extends GenericService<UserRecommend> {

	/**
	 * 获取用户的推荐文档
	 * @param studentId  学生id
	 * @param pageSize   获取条数
	 * @return studentDocumentDto
	 */
	List<StudentDocumentDto> fetchRecommendDocuments(Long studentId, int pageSize);

}
