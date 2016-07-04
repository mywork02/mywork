package com.suime.library.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.DateUtil;
import com.confucian.framework.utils.StringUtils;
import com.confucian.mybatis.support.manager.GenericManager;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.context.model.StdocTagRels;
import com.suime.context.model.StudentDocument;
import com.suime.context.model.Tags;
import com.suime.library.dao.StdocTagRelsMapper;
import com.suime.library.dao.TagsMapper;

/**
 * Created by ice on 14/3/2016.
 */
@Repository("tagsManager")
public class TagsManager extends GenericManager<Tags> {

    /**
     * tagsMapper
     */
    @Autowired
    @Qualifier("tagsMapper")
    private TagsMapper tagsMapper;

    /**
     * stdocTagRelsMapper
     */
    @Autowired
    @Qualifier("stdocTagRelsMapper")
    private StdocTagRelsMapper stdocTagRelsMapper;

    @Override
    protected GenericMapper<Tags> getGenericMapper() {
        return tagsMapper;
    }

    /**
     * addOrUpdateTags
     * @param studentDocument studentDocument
     */
    public void addOrUpdateTags(StudentDocument studentDocument) {
        String tagsStr = studentDocument.getTags();
        if (StringUtils.isNotBlank(tagsStr)) {
            String tempTags = tagsStr.replaceAll("，", Constants.VALUE_SIMPLE_SPLIT_CHAR);  //去掉全角的逗号
            String[] array = tempTags.split(Constants.VALUE_SIMPLE_SPLIT_CHAR);
            Byte type = 1;//
            Byte source = 2;//用户
            Double relevancy = 1d;
            List<Long> tagsIds = new ArrayList<>();
            Map<String, Object> params = new HashMap<String, Object>();
            Conds relsConds = new Conds();
            relsConds.equal("stdoc_id", studentDocument.getId());
            params.put("conds", relsConds);
            List<Long> oldTagIds = stdocTagRelsMapper.fetchTagIdByStdocId(studentDocument.getId());
            if (oldTagIds == null) {
                oldTagIds = new ArrayList<Long>();
            }
            this.logger.info(StringUtils.join(oldTagIds, Constants.VALUE_SIMPLE_SPLIT_CHAR));

            for (String text : array) {
                if (StringUtils.isNotBlank(text)) {
                    Timestamp now = DateUtil.getSqlTimestamp();
                    Tags tags = addOrUpdateTags(studentDocument, text, source, type, now);
                    if (oldTagIds.contains(tags.getId())) {
                        oldTagIds.remove(tags.getId());
                    } else {
                        tagsIds.add(tags.getId());
                        tagsMapper.updateUseCount(tags.getId(), 1);
                        addStdocTagsRels(studentDocument, tags, relevancy, now);
                    }
                }
            }
            if (oldTagIds != null && !oldTagIds.isEmpty()) {
                stdocTagRelsMapper.removeRelsByTagIds(studentDocument.getId(), oldTagIds);
            }
            // List<StdocTagRels> stdocTagRelses = stdocTagRelsMapper.fetchByTagIds(studentDocument.getId(), tagsIds);
            // this.logger.info(JsonUtil.toJsonString(stdocTagRelses));
        }
    }

    /**
     * 添加或更新标签
     *
     * @param studentDocument 文档
     * @param text            标签文本
     * @param source          来源
     * @param type            标签类型
     * @param now             当前时间
     * @return tags
     */
    private Tags addOrUpdateTags(StudentDocument studentDocument, String text, Byte source, Byte type, Timestamp now) {
        Conds conds = new Conds();
        conds.equal("text", text);
        conds.equal("type", type);
        Tags tags = this.fetchSearchByConds(conds);
		// if (tags != null) {
		// // tagsMapper.updateUseCount(tags.getId(), 1);
		// } else {
            if (tags == null) {
            tags = new Tags();
            tags.setSource(source);
            tags.setCreatedAt(now);
            tags.setUpdatedAt(now);
            tags.setText(text);
            tags.setType(type);
            tags.setUseCount(0);
            tags.setUserId(studentDocument.getStudentId());
            tags.setActived(Byte.valueOf("1"));
            this.save(tags);
        }
        return tags;
    }

    /**
     * 添加 文档 和 tags 关联
     *
     * @param studentDocument studentDocument
     * @param tags            tags
     * @param relevancy       权重
     * @param now             当前时间
     */
    private void addStdocTagsRels(StudentDocument studentDocument, Tags tags, Double relevancy, Timestamp now) {
        Map<String, Object> params = new HashMap<String, Object>();
        Conds relsConds = new Conds();
        relsConds.equal("tag_id", tags.getId());
        relsConds.equal("stdoc_id", studentDocument.getId());
        params.put("conds", relsConds);
        List<StdocTagRels> stdocTagRelses = stdocTagRelsMapper.fetchSearchByPage(params);
        if (stdocTagRelses == null || stdocTagRelses.isEmpty()) {
            StdocTagRels stdocTagRels = new StdocTagRels();
            stdocTagRels.setCreatedAt(now);
            stdocTagRels.setUpdatedAt(now);
            stdocTagRels.setTagId(tags.getId());
            stdocTagRels.setStdocId(studentDocument.getId());
            stdocTagRels.setRelevancy(relevancy);
            stdocTagRels.setStudentId(studentDocument.getStudentId());
            this.stdocTagRelsMapper.save(stdocTagRels);
        }
    }
}
