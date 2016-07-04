package com.suime.library.manager;

import com.confucian.framework.utils.StringUtils;
import com.confucian.mybatis.support.manager.GenericManager;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.StudentDocument;
import com.suime.library.dao.StudentDocumentMapper;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.service.support.OrderFieldEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * studentDocumentService
 * Created by ice 17/02/2016.
 */
@Repository("studentDocumentManager")
public class StudentDocumentManager extends GenericManager<StudentDocument> {

    /**
     * studentDocumentMapper
     */
    @Autowired
    @Qualifier("studentDocumentMapper")
    private StudentDocumentMapper studentDocumentMapper;

    @Override
    protected GenericMapper<StudentDocument> getGenericMapper() {
        return studentDocumentMapper;
    }

    /**
     * 获取公司推荐的文档
     *
     * @return 公司推荐的文档 studentDocumentDto
     */
    public List<StudentDocumentDto> fetchStickDocument() {
        Byte actived = 1;
        Conds conds = new Conds();
        conds.equal("sd.review_State", 2);
        conds.equal("sd.actived", actived);
        conds.equal("file.has_Thumbnail", 1);
        conds.equal("file.state", 3);
        conds.equal("sd.stick", 1);
        conds.notNull("file.page_Count");
        Map<String, Object> params = new HashMap<>();
        params.put("conds", conds);
        return this.studentDocumentMapper.fetchDtoSearch(params);
    }

    /**
     * 获取推荐文档记录
     * @param id
     * @param orderFieldEnum
     * @param pageSize
     * @return list student dociment dto
     */
    public List<StudentDocumentDto> fetchRecommendDocuments(Long id, OrderFieldEnum orderFieldEnum, int pageSize) {
        StudentDocument studentDocument = null;
        if (id != null) {
            studentDocument = this.fetchById(id);
            if (studentDocument == null || studentDocument.getActived().intValue() != 1) {
                throw BusinessErrors.getInstance().paramsError();
            }
        }
        Byte reviewState = 2;//通过审核
        Byte permission = 1;//公开
        Byte hasThumbnail = 1;//有缩略图
        Byte actived = 1;//有效状态
        Conds conds = new Conds();
        conds.equal("sd.actived", actived);
        conds.equal("sd.review_state", reviewState);
        conds.equal("sd.permission", permission);
        conds.equal("file.has_thumbnail", hasThumbnail);
        conds.notNull("file.page_count");
        if (id != null) {
            conds.notEqual("sd.id", id);
        }
        if (studentDocument != null && StringUtils.isNotBlank(studentDocument.getCategoryCode())) {
            conds.rightLike("sd.category_code", studentDocument.getCategoryCode());
        }
        Sort sort;
        if (orderFieldEnum != null) {
            if (orderFieldEnum.equals(OrderFieldEnum.RATINGS)) {
                sort = new Sort("sd.ratings/sd.rating_count", OrderType.DESC);
            } else {
                sort = new Sort("sd." + orderFieldEnum.getDbField(), OrderType.DESC);
            }
        } else {
            sort = new Sort("sd.print_count", OrderType.DESC);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("conds", conds);
        params.put("offset", 0);
        params.put("limit", pageSize > 0 ? pageSize : 0);
        params.put("sort", sort);
        return this.studentDocumentMapper.fetchDtoSearch(params);
    }
}
