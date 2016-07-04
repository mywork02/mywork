package com.suime.library.manager;

import com.confucian.mybatis.support.manager.GenericManager;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.context.model.SendRecord;
import com.suime.library.dao.SendRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenqy on 2016/4/12.
 */
@Repository
public class SendRecordManager extends GenericManager<SendRecord> {

	/**
	 * sendRecordMapper
	 */
    @Autowired
    private SendRecordMapper sendRecordMapper;

    @Override
    protected GenericMapper<SendRecord> getGenericMapper() {
        return sendRecordMapper;
    }

    /**
     * 接收的文档总数
     *
     * @param conds
     * @return 接收的文档总数
     */
    public int countReceivedDocument(Conds conds) {
        Map<String, Object> params = new HashMap<>();
        params.put("conds", conds);
        return this.sendRecordMapper.countReceivedDocument(params);
    }
}
