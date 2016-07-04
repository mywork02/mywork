package com.suime.library.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suime.common.support.Configure;
import com.suime.context.model.Comment;

/**
 * commentDto
 *
 * @author ice
 */
public class CommentDto implements Serializable {

    /**
     * sid
     */
    private static final long serialVersionUID = -8099963819003032028L;

    /**
     * 唯一id
     */
    private Long id;

    /**
     * 关联的studentDocument id
     */
    private Long studentDocumentId;

    /**
     * 关联的student id
     */
    private Long studentId;

    /**
     * 用户昵称,冗余字段,优化查询
     */
    private String studentNickName;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 被回复的评论id
     */
    private Long replyId;

    /**
     * 被回复的评论 用户 id
     */
    private Long replyStudentId;

    /**
     * 被回复的评论 用户 昵称,冗余字段,优化查询
     */
    private String replyNickName;

    /**
     * 父评论id
     */
    private Long parentId;

    /**
     * 审核状态,1:待审核状态,2:通过状态.预留字段,先默认通过状态
     */
    private Byte reviewState;

    /**
     * 是否有效(用于软删除),1:有效 0:无效.默认为0
     */
    private Byte actived;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    private Timestamp updatedAt;

    /**
     * 评分
     */
    private Integer rating;

    /**
     * 子评论
     */
    private List<CommentDto> children;

    /**
     * 用户头像
     */
    private String studentAvatar;

    /**
     * 获得的积分
     */
    private PointDto point;


    /**
     * constructor
     */
    public CommentDto() {
    }

    /**
     * constructor
     *
     * @param comment
     */
    public CommentDto(Comment comment) {
        if (comment != null) {
            this.setActived(comment.getActived());
            this.setContent(comment.getContent());
            this.setCreatedAt(comment.getCreatedAt());
            this.setId(comment.getId());
            this.setParentId(comment.getParentId());

            this.setReplyId(comment.getReplyId());
            this.setReplyStudentId(comment.getReplyStudentId());
            this.setReplyNickName(comment.getReplyStudentNickName());

            this.setStudentDocumentId(comment.getStudentDocumentId());
            this.setReviewState(comment.getReviewState());

            this.setStudentId(comment.getStudentId());
            this.setStudentNickName(comment.getStudentNickName());
            this.setUpdatedAt(comment.getUpdatedAt());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentDocumentId() {
        return studentDocumentId;
    }

    public void setStudentDocumentId(Long studentDocumentId) {
        this.studentDocumentId = studentDocumentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentNickName() {
        return studentNickName;
    }

    public void setStudentNickName(String studentNickName) {
        this.studentNickName = studentNickName;
    }

    /**
     * 如果该评论被删除了，则替换掉删除的文档内容，为该评论已被删除
     *
     * @return 处理后的评论内容
     */
    public String getContent() {
        if (this.actived == 0) {
            return SpringContext.getText("comment.already.removed");
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Long getReplyStudentId() {
        return replyStudentId;
    }

    public void setReplyStudentId(Long replyStudentId) {
        this.replyStudentId = replyStudentId;
    }

    public String getReplyNickName() {
        return replyNickName;
    }

    public void setReplyNickName(String replyNickName) {
        this.replyNickName = replyNickName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Byte getActived() {
        return actived;
    }

    public void setActived(Byte actived) {
        this.actived = actived;
    }

    public Byte getReviewState() {
        return reviewState;
    }

    public void setReviewState(Byte reviewState) {
        this.reviewState = reviewState;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CommentDto> getChildren() {
        return children;
    }

    public void setChildren(List<CommentDto> children) {
        this.children = children;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * 如果用户没有头像，则返回默认头像
     *
     * @return the studentAvatar
     */
    public String getStudentAvatar() {
        if (StringUtils.isBlank(studentAvatar)) {
            return Configure.getPropertyValue("student.avatar.default");
        }
        return studentAvatar;
    }

    public void setStudentAvatar(String studentAvatar) {
        this.studentAvatar = studentAvatar;
    }

    @JsonIgnore
    public PointDto getPoint() {
        return point;
    }

    public void setPoint(PointDto point) {
        this.point = point;
    }
}
