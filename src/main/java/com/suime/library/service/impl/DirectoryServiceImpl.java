package com.suime.library.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.utils.DateUtil;
import com.confucian.framework.utils.StringUtils;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.constants.PermissionType;
import com.suime.constants.SuimeLibraryConstants;
import com.suime.context.model.Directory;
import com.suime.context.model.DirectoryContentRels;
import com.suime.context.model.Student;
import com.suime.context.model.StudentDocument;
import com.suime.library.dao.DirectoryContentRelsMapper;
import com.suime.library.dao.DirectoryMapper;
import com.suime.library.dao.StudentDocumentMapper;
import com.suime.library.dto.DirectoryDto;
import com.suime.library.dto.pack.DirectoryBean;
import com.suime.library.dto.pack.DocPermissionBean;
import com.suime.library.dto.pack.DocumentMoveBean;
import com.suime.library.error.DirectoryErrors;
import com.suime.library.service.DirectoryService;

/**
 * directoryService
 * Created by ice 12/03/2016.
 */
@Service("directoryService")
public class DirectoryServiceImpl extends GenericServiceImpl<Directory> implements DirectoryService {

	/**
	 * student_id
	 */
	private final String studentIdColumn = "student_id";

	/**
	 * directoryMapper
	 */
	@Autowired
	@Qualifier("directoryMapper")
	private DirectoryMapper directoryMapper;

	/**
	 * studentDocumentMapper
	 */
	@Autowired
	@Qualifier("studentDocumentMapper")
	private StudentDocumentMapper studentDocumentMapper;

	@Autowired
	private DirectoryContentRelsMapper directoryContentRelsMapper;

	@Override
	public GenericMapper<Directory> getGenericMapper() {
		return directoryMapper;
	}

	@Override
	public void addByItem(DirectoryBean directoryBean) {
		if (directoryBean == null || StringUtils.isBlank(directoryBean.getName()) || directoryBean.getStudentId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		if (this.isExistDir(directoryBean)) {
			throw DirectoryErrors.getInstance().allreadyExistError(directoryBean.getName());
		}
		if (directoryBean.getParentId() != null) {
			Directory parent = this.fetchById(directoryBean.getParentId());
			if (parent == null || !parent.getStudentId().equals(directoryBean.getStudentId())) {
				throw BusinessErrors.getInstance().paramsError();
			}
		}
		if (directoryBean.getParentId() == null || directoryBean.getParentId().equals("")) {
			directoryBean.setParentId(Long.valueOf("0"));// 根目录 为0
		}
		Byte actived = 1;
		Directory directory = new Directory();
		directory.setActived(actived);
		directory.setStudentId(directoryBean.getStudentId());
		directory.setParentId(directoryBean.getParentId());
		directory.setName(directoryBean.getName());
		directory.setPermission(PermissionType.PERMISSION_PRIVATE);
		directory.setSource(directoryBean.getSource());
		Timestamp sqlTimestamp = DateUtil.getSqlTimestamp();
		directory.setCreatedAt(sqlTimestamp);
		directory.setUpdatedAt(sqlTimestamp);
		this.save(directory);

		/**
		 * 文件夹文档目录关联表添加记录
		 */
		Byte type = 2;
		DirectoryContentRels directoryContentRels = new DirectoryContentRels();
		directoryContentRels.setActived(actived);
		directoryContentRels.setAssociatedId(directory.getId());
		directoryContentRels.setDirectoryId(directoryBean.getParentId());
		directoryContentRels.setAssociatedName(directoryBean.getName());
		directoryContentRels.setType(type);
		directoryContentRels.setStudentId(directoryBean.getStudentId());
		directoryContentRelsMapper.save(directoryContentRels);
	}

	@Override
	public void removeById(DirectoryBean directoryBean) {
		if (directoryBean == null || directoryBean.getId() == null || directoryBean.getStudentId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Directory directory = this.fetchById(directoryBean.getId());
		if (directory == null || directory.getActived() != 1) {
			throw DirectoryErrors.getInstance().directoryNotExistsError();
		}
		if (directory.getStudentId() == null || !directory.getStudentId().equals(directoryBean.getStudentId())) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}
		List<Long> subIds = new ArrayList<>();
		subIds.add(directoryBean.getId());
		this.removeUnderDirectory(directoryBean.getId(), subIds);
		Byte actived = 0;
		directory.setActived(actived);
		this.update(directory);
	}

	@Override
	public void updateNameByItem(DirectoryBean directoryBean) {
		if (directoryBean == null || directoryBean.getId() == null || directoryBean.getStudentId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Directory directory = this.fetchById(directoryBean.getId());
		if (directory == null) {
			throw DirectoryErrors.getInstance().directoryNotExistsError();
		}
		// 权限判断
		if (directory.getStudentId() == null || !directory.getStudentId().equals(directoryBean.getStudentId())) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}
		// 名称未改动
		if (StringUtils.equals(directory.getName(), directoryBean.getName()) && StringUtils.equals(directory.getIntro(), directoryBean.getIntro())) {
			return;
		}
		directory.setName(directoryBean.getName() != null ? directoryBean.getName() : "");
		directory.setIntro(directoryBean.getIntro() != null ? directoryBean.getIntro() : null);
		this.update(directory);

		/**
		 *文件夹文档目录关联表记录修改
		 */
		Conds conds = new Conds();
		conds.equal("associated_id", directory.getId());
		conds.equal(studentIdColumn, directory.getStudentId());
		conds.equal("type", SuimeLibraryConstants.DIR_CONTENT_RELS_TYPE_DIRECTORY);
		Map<String, Object> direMap = new HashMap<String, Object>();
		direMap.put("conds", conds);
		List<DirectoryContentRels> directoryContentRels = directoryContentRelsMapper.fetchSearchByPage(direMap);
		if (directoryContentRels != null && directoryContentRels.size() > 0) {
			DirectoryContentRels dire = directoryContentRels.get(0);
			dire.setAssociatedName(directoryBean.getName());
			directoryContentRelsMapper.update(dire);
		}
	}

	/**
	 * 目录是否已存在
	 *
	 * @param directoryBean
	 * @return
	 */
	private boolean isExistDir(DirectoryBean directoryBean) {
		Conds conds = new Conds();
		Byte actived = 1;
		conds.equal("actived", actived);
		conds.equal("name", directoryBean.getName());
		conds.equal(studentIdColumn, directoryBean.getStudentId());
		if (directoryBean.getParentId() != null) {
			conds.equal("parent_id", directoryBean.getParentId());
		} else {
			conds.isNull("parent_id");
		}
		return this.existByConds(conds);
	}

	/**
	 * 删除文件夹下的所有内容
	 *
	 * @param id
	 */
	private void removeUnderDirectory(Long id, List<Long> subIds) {
		this.studentDocumentMapper.removeByDirectory(id);// 删除目录下文档
		List<Directory> subs = this.directoryMapper.fetchSubdirectoryIds(id);// 获取子目录
		if (subs != null && !subs.isEmpty()) {
			for (Directory sub : subs) {
				Long subId = sub.getId();
				subIds.add(subId);
				this.removeUnderDirectory(subId, subIds);
			}
			Conds conds = new Conds();
			Map<String, Object> params = new HashMap<>();
			conds.in("id", subIds.toArray());
			params.put("conds", conds);
			params.put("actived", 0);
			this.directoryMapper.removeBatch(params);
		}
		/**
		* 删除关联表中数据
		*/
		//删除所有目录下的文档关联
		Conds docConds = new Conds();
		Map<String, Object> docParams = new HashMap<>();
		docConds.in("directory_id", subIds.toArray());
		docConds.equal("type", SuimeLibraryConstants.DIR_CONTENT_RELS_TYPE_DOCUMENT);
		docParams.put("conds", docConds);
		this.directoryContentRelsMapper.removeBatch(docParams);
		// 删除所有目录下的目录关联
		Conds doc = new Conds();
		Map<String, Object> docs = new HashMap<>();
		doc.in("associated_id", subIds.toArray());
		doc.equal("type", SuimeLibraryConstants.DIR_CONTENT_RELS_TYPE_DIRECTORY);
		docs.put("conds", doc);
		this.directoryContentRelsMapper.removeBatch(docs);

	}

	/**
	 *公开目录
	 */
	@Override
	public void changePermission(DocPermissionBean docPermissionBean) {
		if (docPermissionBean == null || docPermissionBean.getIds() == null || docPermissionBean.getStudentId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		for (int i = 0; i < docPermissionBean.getIds().length; i++) {
			Long id = docPermissionBean.getIds()[i];
			List<Long> arrayList = new ArrayList<Long>();
			arrayList.add(id);
			publicUnderDirectory(id, arrayList, docPermissionBean.getStudentId(), PermissionType.PERMISSION_PUBLIC);
		}
	}

	/**
	 * 公开、私有目录下面的所有内容
	 * @param id  文件夹ID
	 * @param subIds  所有子目录ID
	 */
	private void publicUnderDirectory(Long id, List<Long> subIds, Long studentId, Byte type) {
		List<Directory> subs = this.directoryMapper.fetchSubdirectoryIds(id);// 获取子目录
		if (subs != null && !subs.isEmpty()) {
			for (Directory sub : subs) {
				Long subId = sub.getId();
				subIds.add(subId);
				this.publicUnderDirectory(subId, subIds, studentId, type);
			}
			Conds conds = new Conds();
			Map<String, Object> params = new HashMap<>();
			conds.in("directory_id", subIds.toArray());
			conds.equal(studentIdColumn, studentId);
			params.put("conds", conds);
			params.put("permission", type);
			this.studentDocumentMapper.changepermission(params);// 公开所有文件夹下面的文档

			Conds direConds = new Conds();
			Map<String, Object> direParams = new HashMap<>();
			direConds.in("id", subIds.toArray());
			direConds.equal(studentIdColumn, studentId);
			direParams.put("conds", direConds);
			direParams.put("permission", type);
			this.directoryMapper.changePermission(direParams);// 公开所有文件夹
		}
	}

	/**
	 *取消公开目录，（私有）
	 */
	@Override
	public void changePrivateDire(DocPermissionBean docPermissionBean) {
		if (docPermissionBean == null || docPermissionBean.getIds() == null || docPermissionBean.getStudentId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		for (int i = 0; i < docPermissionBean.getIds().length; i++) {
			Long id = Long.valueOf(docPermissionBean.getIds()[i].toString());
			List<Long> arrayList = new ArrayList<Long>();
			arrayList.add(id);
			publicUnderDirectory(id, arrayList, docPermissionBean.getStudentId(), PermissionType.PERMISSION_PRIVATE);
		}
	}

	@Override
	public List<DirectoryDto> fetchDtoSearchByPage(Conds conds, Sort sort, int page, int pageSize) {
		Map<String, Object> paramsMap = this.generateParamsMap(conds, sort, page, pageSize);
		return this.directoryMapper.fetchDtoSearchByPage(paramsMap);
	}

	/**
	 * 移动目录
	 * */
	@Override
	public void changeDireMove(DocumentMoveBean documentMoveBean) {
		/**
		 * 查询所有需要移动的目录
		 */
		Directory dire= this.directoryMapper.fetchById(documentMoveBean.getDirectoryId());
		if(dire!=null){
			if(Arrays.asList(documentMoveBean.getIds()).contains(dire.getParentId())){
				throw BusinessErrors.getInstance().paramsError();
			}
			Conds conds = new Conds();
			conds.in("id", documentMoveBean.getIds());
			conds.equal(studentIdColumn, documentMoveBean.getStudentId());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("conds", conds);
			params.put("directoryId", documentMoveBean.getDirectoryId());
			this.directoryMapper.updateDoresMove(params);
			/**
			*关联表随之更新
			*/
			Conds relsConds = new Conds();
			relsConds.in("associated_id", documentMoveBean.getIds());
			relsConds.equal(studentIdColumn, documentMoveBean.getStudentId());
			relsConds.equal("type", SuimeLibraryConstants.DIR_CONTENT_RELS_TYPE_DIRECTORY);
			Map<String, Object> relsParams = new HashMap<String, Object>();
			relsParams.put("conds", relsConds);
			relsParams.put("directoryId", documentMoveBean.getDirectoryId());
			directoryContentRelsMapper.updateBatch(relsParams);
		}
	}

	@Override
	public DirectoryDto directoryDtoInfo(DirectoryBean directoryBean) {
		Conds conds = new Conds();
		Map<String,Object> map = new HashMap<String,Object>();
		conds.equal("t.student_id", directoryBean.getStudentId());
		conds.equal("t.id", directoryBean.getId());
		conds.equal("t.actived", "1");
		map.put("conds",conds);
		DirectoryDto dire = directoryMapper.fetchDtoSeachById(map);
		if (dire == null) {
			throw DirectoryErrors.getInstance().directoryNotExistsError();
		}
		return dire;
	}

	@Override
	public void removeByDirectory(DirectoryBean directoryBean) {
		if(directoryBean.getStudentId()==null){
			throw BusinessErrors.getInstance().userOfflineError();
		}
		if(directoryBean.getId()==null){
			throw BusinessErrors.getInstance().paramsError();
		}
		/**
		 * 需要删除的文件夹是否存在
		 */
		Byte actived = 1;
		Conds conds = new Conds();
		Map<String,Object> map = new HashMap<String,Object>();
		conds.equal("t.student_id", directoryBean.getStudentId());
		conds.equal("t.id", directoryBean.getId());
		conds.equal("t.actived", actived);
		map.put("conds",conds);
		DirectoryDto dire = directoryMapper.fetchDtoSeachById(map);
		if (dire == null) {
			throw DirectoryErrors.getInstance().directoryNotExistsError();
		}
		/**
		 * 将此文件夹下面的目录及文档移动到此目录的父目录里面。此目录的子目录里面的文档不做变动。
		 */
		//查询下面的子目录
		Conds condsc = new Conds();
		condsc.equal("actived", actived);
		condsc.equal("student_id", directoryBean.getStudentId());
		condsc.equal("parent_id", directoryBean.getId());
		Sort sort = new Sort("name", OrderType.ASC);
		Map<String, Object> paramsMap = this.generateParamsMap(condsc, sort, 1, 0);
		List<DirectoryDto> directoryChilds = this.directoryMapper.fetchDtoSearchByPage(paramsMap);
		if(directoryChilds!=null && directoryChilds.size()>0){
			List<Long> direIds = new ArrayList<Long>();
			for(DirectoryDto d : directoryChilds){
				direIds.add(d.getId());
			}
			//移动目录
			Conds Moveconds = new Conds();
			Moveconds.in("id", direIds.toArray());
			Moveconds.equal(studentIdColumn,  directoryBean.getStudentId());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("conds", Moveconds);
			params.put("directoryId", dire.getParentId());//移动到要删除的目录的父目录里面
			this.directoryMapper.updateDoresMove(params);
			//目录文件夹关联表更新
			Conds relsConds = new Conds();
			relsConds.in("associated_id", direIds.toArray());
			relsConds.equal(studentIdColumn, directoryBean.getStudentId());
			relsConds.equal("type", SuimeLibraryConstants.DIR_CONTENT_RELS_TYPE_DIRECTORY);
			Map<String, Object> relsParams = new HashMap<String, Object>();
			relsParams.put("conds", relsConds);
			relsParams.put("directoryId", dire.getParentId());//移动到要删除的目录的父目录里面
			directoryContentRelsMapper.updateBatch(relsParams);
		}
		//查询下面的文档
		Conds condDoc = new Conds();
		condDoc.equal("actived", actived);
		condDoc.equal("student_id", directoryBean.getStudentId());
		condDoc.equal("directory_id", directoryBean.getId());
		Map<String, Object> docParamsMap = this.generateParamsMap(condDoc, sort, 1, 0);
		List<StudentDocument> docChilds = this.studentDocumentMapper.fetchSearchByPage(docParamsMap);
		if(docChilds!=null && docChilds.size()>0){
			List<Long> direDocIds = new ArrayList<Long>();
			for(StudentDocument d : docChilds){
				direDocIds.add(d.getId());
			}
			Conds condsDoc = new Conds();
			condsDoc.in("id", direDocIds.toArray());
			condsDoc.equal("student_id",directoryBean.getStudentId());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("conds", condsDoc);
			params.put("directoryId", dire.getParentId());
			this.studentDocumentMapper.updatedocsdore(params);

			/**
			 * 关联表随之更新
			 */
			Conds relsConds = new Conds();
			relsConds.in("associated_id", direDocIds.toArray());
			relsConds.equal("student_id", directoryBean.getStudentId());
			relsConds.equal("type", SuimeLibraryConstants.DIR_CONTENT_RELS_TYPE_DOCUMENT);
			Map<String, Object> relsParams = new HashMap<String, Object>();
			relsParams.put("conds", relsConds);
			relsParams.put("directoryId", dire.getParentId());
			directoryContentRelsMapper.updateBatch(relsParams);
		}
		//删除此目录
		Byte delActived=0;
		Directory delDirectory = new Directory();
		delDirectory.setId(dire.getId());
		delDirectory.setActived(delActived);
		this.update(delDirectory);
		//删除关联表数据	
		Conds doc = new Conds();
		Map<String, Object> docs = new HashMap<>();
		doc.in("associated_id", new Long[]{dire.getId()});
		doc.equal("type", SuimeLibraryConstants.DIR_CONTENT_RELS_TYPE_DIRECTORY);
		docs.put("conds", doc);
		this.directoryContentRelsMapper.removeBatch(docs);
	}
}
