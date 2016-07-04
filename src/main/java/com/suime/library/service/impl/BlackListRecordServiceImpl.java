package com.suime.library.service.impl;

import com.confucian.framework.utils.DateUtil;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.Student;
import com.suime.context.model.support.StudentStatusEnum;
import com.suime.library.dao.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.BlackListRecordMapper;
import com.suime.context.model.BlackListRecord;
import com.suime.library.service.BlackListRecordService;

import java.sql.Timestamp;

/**
 * blackListRecordService
 * Created by ice 17/02/2016.
 */
@Service("blackListRecordService")
public class BlackListRecordServiceImpl extends GenericServiceImpl<BlackListRecord> implements BlackListRecordService {

    /**
     * blackListRecordMapper
     */
    @Autowired
    @Qualifier("blackListRecordMapper")
    private BlackListRecordMapper blackListRecordMapper;

    /**
     * studentMapper
     */
    @Autowired
    @Qualifier("studentMapper")
    private StudentMapper studentMapper;

    @Override
    public GenericMapper<BlackListRecord> getGenericMapper() {
        return blackListRecordMapper;
    }

    @Override
    public void addToBlackList(Long id, Long adminId) {
        StudentStatusEnum studentStatus = StudentStatusEnum.WENKU_BLACK;
        this.addRemoveBlackList(id, adminId, studentStatus);
    }

    @Override
    public void removeFromBlackList(Long id, Long adminId) {
        StudentStatusEnum studentStatus = StudentStatusEnum.NORMAL;
        this.addRemoveBlackList(id, adminId, studentStatus);
    }

    /**
     * 添加或移除黑名单
     *
     * @param id
     * @param studentStatus
     */
    private void addRemoveBlackList(Long id, Long adminId, StudentStatusEnum studentStatus) {
        if (id == null) {
            throw BusinessErrors.getInstance().paramsError();
        }
        Student student = this.studentMapper.fetchById(id);
        if (student == null) {
            throw BusinessErrors.getInstance().userNotExistsError();
        }
        Timestamp currentTime = DateUtil.getSqlTimestamp();

        student.setStatus(studentStatus.ordinal());
        student.setUpdatedAt(currentTime);
        this.studentMapper.update(student);
        BlackListRecord blackListRecord = new BlackListRecord();
        blackListRecord.setOperation(((byte) studentStatus.ordinal()));
        blackListRecord.setCreatedAt(currentTime);
        blackListRecord.setUpdatedAt(currentTime);
        blackListRecord.setStudentId(id);
        blackListRecord.setAdminId(adminId);
        this.save(blackListRecord);
    }
}
