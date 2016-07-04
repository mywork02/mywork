package com.suime.library.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suime.library.dto.PointDto;
import me.sui.context.model.StudentPointLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.support.Page;
import com.confucian.framework.utils.DateUtil;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.Comment;
import com.suime.context.model.Student;
import com.suime.context.model.StudentDocument;
import com.suime.context.model.support.DocumentCountEnum;
import com.suime.context.model.support.MessageExtraKeyEnum;
import com.suime.context.model.support.MessageTypeConstants;
import com.suime.context.model.support.MessageTypeEnum;
import com.suime.library.dao.CommentMapper;
import com.suime.library.dao.StudentDocumentMapper;
import com.suime.library.dao.StudentMapper;
import com.suime.library.dto.CommentDto;
import com.suime.library.dto.pack.CommentBean;
import com.suime.library.dto.pack.MessageBean;
import com.suime.library.error.CommentErrors;
import com.suime.library.manager.MessageManager;
import com.suime.library.service.CommentService;

import me.sui.context.support.PointTypeEnum;
import me.sui.user.remote.service.StudentPointRemoteService;

/**
 * commentService
 * Created by ice 17/02/2016.
 */
@Service("commentService")
public class CommentServiceImpl extends GenericServiceImpl<Comment> implements CommentService {

    /**
     * commentMapper
     */
    @Autowired
    @Qualifier("commentMapper")
    private CommentMapper commentMapper;

    /**
     * studentMapper
     */
    @Autowired
    @Qualifier("studentMapper")
    private StudentMapper studentMapper;

    /**
     * studentDocumentMapper
     */
    @Autowired
    @Qualifier("studentDocumentMapper")
    private StudentDocumentMapper studentDocumentMapper;

    /**
     * messageManager
     */
    @Autowired
    @Qualifier("messageManager")
    private MessageManager messageManager;

    /**
     * studentPointRemoteService
     */
    @Autowired
    @Qualifier("studentPointRemoteService")
    private StudentPointRemoteService studentPointRemoteService;

    @Override
    public GenericMapper<Comment> getGenericMapper() {
        return commentMapper;
    }

    @Override
    public CommentDto addComment(CommentBean commentBean) {
        if (commentBean == null) {
            return null;
        }
        if (commentBean.getStudentDocumentId() == null) {
            throw BusinessErrors.getInstance().fileNotFound();
        }
        Comment comment = commentBean.transToDocComment();
        Byte actived = 1;
        comment.setActived(actived);
        Timestamp currentTime = DateUtil.getSqlTimestamp();
        comment.setCreatedAt(currentTime);
        comment.setUpdatedAt(currentTime);
        Long studentId = commentBean.getStudentId();
        Student student = studentMapper.fetchById(studentId);
        if (student == null) {
            return null;
        }
        if (commentBean.getParentId() != null) {
            Comment parent = this.fetchById(commentBean.getParentId());
            if (parent != null) {
                comment.setParentId(parent.getId());
            }
        }
        comment.setStudentId(student.getId());
        comment.setStudentNickName(student.getNickName());// student 需要添加昵称字段，在此处先占位
        if (commentBean.getReplyId() != null) {
            Comment reply = this.fetchById(commentBean.getReplyId());
            if (reply != null) {
                comment.setReplyId(reply.getId());
                if (reply != null) {
                    comment.setReplyStudentNickName(reply.getStudentNickName());
                    comment.setReplyStudentId(reply.getStudentId());
                    this.logger.info("reply id:" + reply.getId());
                    this.logger.info("reply parent id:" + ((reply.getParentId() != null) ? reply.getParentId() : "null"));
                    if (reply.getParentId() != null) {
                        comment.setParentId(reply.getParentId());
                    } else {
                        comment.setParentId(reply.getId());
                    }
                }
            }
        }
        Byte reviewState = 2;
        comment.setReviewState(reviewState);
        StudentDocument studentDocument = studentDocumentMapper.fetchById(commentBean.getStudentDocumentId());
        if (studentDocument == null) {
            throw BusinessErrors.getInstance().fileNotFound();
        }
        comment.setStudentDocumentId(studentDocument.getId());
        this.studentDocumentMapper.updateCountById(studentDocument.getId(), DocumentCountEnum.commentCount.getText(), 1);
        this.save(comment);

        addCommentMessage(comment, studentDocument.getStudentId(), studentDocument.getName());

        /**
         * 添加评论积分
         */
        StudentPointLog studentPointLog = this.studentPointRemoteService.addStudentPointLog(PointTypeEnum.REPLY, studentId, comment.getId(), null);
        Integer avliablePoint = student.getAvliablePoint();
        if (avliablePoint == null) {
            avliablePoint = 0;
        }
        PointDto pointDto = new PointDto();
        if (studentPointLog != null) {
            pointDto.setPoint(studentPointLog.getChangePoint());
            avliablePoint += studentPointLog.getChangePoint();
        } else {
            pointDto.setPoint(0);
            pointDto.setPointMemo(SpringContext.getText("point.reply.no_point.memo"));
        }
        pointDto.setCurrentPoint(avliablePoint.longValue());
        CommentDto dto = new CommentDto(comment);
        dto.setPoint(pointDto);
        return dto;
    }

    /**
     * 添加评论message
     *
     * @param comment             comment
     * @param receiverId          接收人id
     * @param studentDocumentName 文档名称
     */
	private void addCommentMessage(Comment comment, Long receiverId, String studentDocumentName) {
        MessageBean messageBean = new MessageBean();
        messageBean.setSenderId(comment.getStudentId());
        messageBean.setRelateId(comment.getId());
        messageBean.setStudentDocumentId(comment.getStudentDocumentId());
        messageBean.setStudentDocumentName(studentDocumentName);
        if (comment.getReplyId() != null) {
            messageBean.setMessageType(MessageTypeConstants.REPLY_COMMENT);
            messageBean.setType(MessageTypeEnum.comment);
            messageBean.setContent(SpringContext.getText("message.reply_comment"));
            messageBean.setReceiverId(comment.getReplyStudentId());
        } else {
            messageBean.setMessageType(MessageTypeConstants.COMMENT);
            messageBean.setType(MessageTypeEnum.comment);
            messageBean.setContent(SpringContext.getText("message.new_comment", studentDocumentName));
            messageBean.setReceiverId(receiverId);
        }

        messageBean.setExtraKey(MessageExtraKeyEnum.wenku);
        messageManager.add(messageBean);
    }

    @Override
    public Page pageByItem(CommentBean commentBean, int page, int pageSize) {
        if (commentBean == null) {
            return null;
        }
        Conds conds = extractConds(commentBean);
        int count = this.count(conds);

        Sort sort = new Sort("t.created_at", OrderType.DESC);
        List<CommentDto> list = fetchDtoSearchByPage(conds, sort, page, pageSize);

        if (list != null && !list.isEmpty()) {
            for (CommentDto dto : list) {
                CommentBean temp = new CommentBean();
                temp.setParentId(dto.getId());
                temp.setReviewState(commentBean.getReviewState());
                temp.setStudentDocumentId(dto.getStudentDocumentId());
                List<CommentDto> children = this.fetchDtoSearchByPage(temp, sort, page, 0);
                dto.setChildren(children);
            }
        }
        Page result = this.generatePage(page, pageSize, count, list);
        return result;
    }

    /**
     * 封装查询条件
     *
     * @param commentBean
     * @return conds
     */
    private Conds extractConds(CommentBean commentBean) {
        Conds conds = new Conds();
        if (commentBean.getParentId() == null) {
            conds.isNull("t.parent_id");
        } else {
            conds.equal("t.parent_id", commentBean.getParentId());
        }
        if (commentBean.getStudentDocumentId() != null) {
            conds.equal("t.student_document_id", commentBean.getStudentDocumentId());
        }
        if (commentBean.getActived() != null) {
            conds.equal("t.actived", commentBean.getActived());
        }
        if (commentBean.getReplyId() != null) {
            conds.equal("t.reply_id", commentBean.getReplyId());
        }
        if (commentBean.getReviewState() != null) {
            conds.equal("t.review_state", commentBean.getReviewState());
        }
        return conds;
    }

    /**
     * 查询
     *
     * @param commentBean
     * @param sort
     * @param page
     * @param pageSize
     * @return list comment dto
     */
    private List<CommentDto> fetchDtoSearchByPage(CommentBean commentBean, Sort sort, int page, int pageSize) {
        if (commentBean == null) {
            return null;
        }
        Conds conds = extractConds(commentBean);
        return this.fetchDtoSearchByPage(conds, sort, page, pageSize);
    }

    /**
     * 私有查询
     *
     * @param conds
     * @param sort
     * @param page
     * @param pageSize
     * @return list comment dto
     */
    private List<CommentDto> fetchDtoSearchByPage(Conds conds, Sort sort, int page, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("conds", conds);
        params.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
        params.put("limit", pageSize > 0 ? pageSize : 0);
        params.put("sort", sort);
        return this.commentMapper.fetchDtoSearchByPage(params);
    }

    @Override
    public Page pageByItemForMobile(CommentBean commentBean, int page, int pageSize) {
        if (commentBean == null) {
            return null;
        }
        Conds conds = new Conds();

        if (commentBean.getStudentDocumentId() != null) {
            conds.equal("t.student_document_id", commentBean.getStudentDocumentId());
        }
        if (commentBean.getActived() != null) {
            conds.equal("t.actived", commentBean.getActived());
        }
        if (commentBean.getReplyId() != null) {
            conds.equal("t.reply_id", commentBean.getReplyId());
        }
        if (commentBean.getReviewState() != null) {
            conds.equal("t.review_state", commentBean.getReviewState());
        }
        int count = this.count(conds);
        Sort sort = new Sort("t.created_at", OrderType.DESC);
        List<CommentDto> list = fetchDtoSearchByPage(conds, sort, page, pageSize);
        Page result = this.generatePage(page, pageSize, count, list);
        return result;
    }

    @Override
    public Page pageByStudentOrContent(String cellphone, String text, int page, int pageSize) {
        Conds conds = new Conds();
        if (StringUtils.isNotBlank(cellphone)) {
            conds.equal("cellphone", cellphone);
        }
        if (StringUtils.isNotBlank(text)) {
            conds.like("content", text);
        }
        Sort sort = new Sort("created_at", OrderType.DESC);
        Page result = this.fetchPageSearch(conds, sort, page, pageSize);

        return result;
    }

    @Override
    public void removeById(Long id) {
        if (id == null) {
            throw BusinessErrors.getInstance().paramsError();
        }
        Comment comment = this.fetchById(id);
        if (comment == null) {
            throw CommentErrors.getInstance().commentNotFoundError();
        }
        Byte actived = 0;
        Timestamp currentTime = DateUtil.getSqlTimestamp();
        comment.setActived(actived);
        comment.setUpdatedAt(currentTime);
        this.update(comment);
    }

    @Override
    public void removeByIdList(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw BusinessErrors.getInstance().paramsError();
        }
        Conds conds = new Conds();
        conds.in("id", ids.toArray(new Long[ids.size()]));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("conds", conds);
        this.commentMapper.batchRemove(params);
    }

}
