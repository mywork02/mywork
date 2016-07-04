package com.suime.library.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.utils.DateUtil;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.context.model.StdocStickRemoved;
import com.suime.library.dao.StdocStickRemovedMapper;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.manager.StudentDocumentManager;
import com.suime.library.service.StdocStickRemovedService;

/**
 * stdocStickRemovedService
 * Created by ice 14/03/2016.
 */
@Service("stdocStickRemovedService")
public class StdocStickRemovedServiceImpl extends GenericServiceImpl<StdocStickRemoved> implements StdocStickRemovedService {

    /**
     * stdocStickRemovedMapper
     */
    @Autowired
    @Qualifier("stdocStickRemovedMapper")
    private StdocStickRemovedMapper stdocStickRemovedMapper;

    /**
     * studentDocumentManager
     */
    @Autowired
    @Qualifier("studentDocumentManager")
    private StudentDocumentManager studentDocumentManager;

    @Override
    public GenericMapper<StdocStickRemoved> getGenericMapper() {
        return stdocStickRemovedMapper;
    }

    @Override
    public StdocStickRemoved addRemovedRecord(Long studentId, Long studentDocumentId) {
        StdocStickRemoved stdocStickRemoved = new StdocStickRemoved();
        Timestamp sqlTimestamp = DateUtil.getSqlTimestamp();
        stdocStickRemoved.setCreatedAt(sqlTimestamp);
        stdocStickRemoved.setUpdatedAt(sqlTimestamp);
        stdocStickRemoved.setStudentDocumentId(studentDocumentId);
        stdocStickRemoved.setStudentId(studentId);
        this.save(stdocStickRemoved);
        return stdocStickRemoved;
    }

    @Override
    public StdocStickRemoved findRemovedRecord(Long studentId, Long studentDocumentId) {
        Conds conds = new Conds();
        conds.equal("student_id", studentId);
        conds.equal("student_document_id", studentDocumentId);
        StdocStickRemoved stdocStickRemoved = this.fetchSearchByConds(conds);
        return stdocStickRemoved;
    }

    @Override
    public List<Long> fetchRemovedStdocIds(Long studentId) {
        Conds conds = new Conds();
        conds.equal("student_id", studentId);
        Map<String, Object> paramsMap = this.generateParamsMap(conds, null, 0, 0);
        return this.stdocStickRemovedMapper.fetchRemovedStdocIds(paramsMap);
    }


    @Override
    public List<StudentDocumentDto> fetchStickDocumentWithoutRemoved(Long studentId) {
        List<StudentDocumentDto> list = this.studentDocumentManager.fetchStickDocument();
        List<Long> removedStdocIds = this.fetchRemovedStdocIds(studentId);
        List<StudentDocumentDto> result = new ArrayList<StudentDocumentDto>();
        if (list != null && !list.isEmpty()) {
            for (StudentDocumentDto dto : list) {
                Long id = dto.getId();
                if (!removedStdocIds.contains(id)) {
                    result.add(dto);
                } else {
                    removedStdocIds.remove(id);
                }
            }
        }
        return result;
    }
}
