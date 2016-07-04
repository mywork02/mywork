package com.suime.library.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.support.Page;
import com.confucian.framework.utils.StringUtils;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.library.dao.DirectoryContentRelsMapper;
import com.suime.library.dto.DirectoryContentDto;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.context.model.DirectoryContentRels;
import com.suime.library.service.DirectoryContentRelsService;

/**
 * directoryContentRelsService
 * Created by ice 26/05/2016.
 */
@Service("directoryContentRelsService")
public class DirectoryContentRelsServiceImpl extends GenericServiceImpl<DirectoryContentRels> implements DirectoryContentRelsService {

	/**
	 * directoryContentRelsMapper
	 */
	@Autowired
	@Qualifier("directoryContentRelsMapper")
	private DirectoryContentRelsMapper directoryContentRelsMapper;

	@Override
	public GenericMapper<DirectoryContentRels> getGenericMapper() {
		return directoryContentRelsMapper;
	}

	@Override
	public void saveBatch(List<DirectoryContentRels> list) {
		directoryContentRelsMapper.saveBatch(list);
	}

	@Override
	public Page fetchSearchByPageIntro(Long studentId,Integer curPage,Integer pageSize,Long directoryId) {
		Conds conds = new Conds();
		if (directoryId != null) {
			conds.equal("t.directory_id", directoryId);
		} else {
			conds.equal("t.directory_id", '0');
		}
		conds.equal("t.student_id", studentId);
		conds.equal("t.actived", "1");
		Sort sort = new Sort("t.type", OrderType.DESC);
		Map<String, Object> params = this.generateParamsMap(conds, sort, curPage, pageSize);
		int totalCount = directoryContentRelsMapper.countIntro(params);
		List<DirectoryContentDto> list = directoryContentRelsMapper.fetchSearchByPageIntro(params);
		return generatePage(curPage, pageSize, totalCount, list);
	}
}
