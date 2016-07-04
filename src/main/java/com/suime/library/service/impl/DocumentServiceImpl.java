package com.suime.library.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.utils.DateUtil;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.dto.pack.FileBean;
import com.suime.common.util.FileTypeUtil;
import com.suime.context.model.Document;
import com.suime.context.model.File;
import com.suime.library.dao.DocumentMapper;
import com.suime.library.dao.FileMapper;
import com.suime.library.dto.DocumentDto;
import com.suime.library.service.DocumentService;

/**
 * documentService
 * Created by ice 17/02/2016.
 */
@Service("documentService")
public class DocumentServiceImpl extends GenericServiceImpl<Document> implements DocumentService {

    /**
     * documentMapper
     */
    @Autowired
    @Qualifier("documentMapper")
    private DocumentMapper documentMapper;

    /**
     * fileMapper
     */
    @Autowired
    @Qualifier("fileMapper")
    private FileMapper fileMapper;

    @Override
    public GenericMapper<Document> getGenericMapper() {
        return documentMapper;
    }

    @Override
    public DocumentDto addDocument(FileBean fileBean) {
        if (fileBean == null || StringUtils.isBlank(fileBean.getKey())) {
            return null;
        }
        Timestamp createdAt = DateUtil.getSqlTimestamp();
        File file = addFile(fileBean, createdAt);
        Conds conds = new Conds();
        conds.equal("file_id", file.getId());
        Document tempDocument = this.fetchSearchByConds(conds);

        String fileName = fileBean.getFileName();
        String brefFileName = FileTypeUtil.getNameWithoutExtension(fileName);// 不带后缀的文件名
        Document document = null;
        if (tempDocument != null) {
            document = tempDocument;
        } else {
            document = new Document();
            Byte actived = 1;
            document.setActived(actived);
            document.setFileName(fileName);
            document.setName(brefFileName);
            document.setFileId(file.getId());
            document.setCreatedAt(createdAt);
            document.setUpdatedAt(createdAt);
            this.save(document);
        }

        DocumentDto dto = new DocumentDto();
        dto.setFileId(file.getId());
        dto.setFileKey(file.getKey());
        dto.setFileName(fileName);
        dto.setName(brefFileName);
        dto.setId(document.getId());
        dto.setExtension(fileBean.getExtension());
        return dto;
    }

    /**
     * addFile
     *
     * @param fileBean  fileBean
     * @param createdAt createdAt
     * @return file
     */
    private File addFile(FileBean fileBean, Timestamp createdAt) {
        if (fileBean == null || StringUtils.isBlank(fileBean.getKey())) {
            return null;
        }
        Conds conds = new Conds();
        conds.equal("`key`", fileBean.getKey());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("conds", conds);
        params.put("offset", 0);
        params.put("limit", 1);
        List<File> list = this.fileMapper.fetchSearchByPage(params);

        File file;
        if (list != null && !list.isEmpty()) {
            file = list.get(0);
        } else {
            file = new File();
            file.setKey(fileBean.getKey());
            Long state = 1L;
            if (fileBean.getState() != null) {
                state = fileBean.getState().longValue();
            }
            file.setState(state);
            file.setLength(fileBean.getLength());
            file.setCreatedAt(createdAt);
            file.setUpdatedAt(createdAt);
            file.setPreview(fileBean.getPreview());
            Byte hasThumbnail = 0;
            file.setHasThumbnail(hasThumbnail);
            this.fileMapper.save(file);
        }
        return file;
    }
}
