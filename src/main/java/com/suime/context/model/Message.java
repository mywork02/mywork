package com.suime.context.model;

import com.suime.context.model.support.MessageExtraKeyEnum;
import com.suime.context.model.support.MessageTypeEnum;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_message 实体类
 * Created by ice 14/03/2016.
 */
public class Message implements Serializable {
    /**
     * sid
     */
    private static final long serialVersionUID = -7879055209649810974L;

    /**
     * id
     */
    private Long id;

    /**
     * 事件发起人id
     */
    private Long senderId;

    /**
     * 接收人id
     */
    private Long receiverId;

    /**
     * 消息相关的文档
     */
    private Long studentDocumentId;

    /**
     * 相关文档名称
     */
    private String studentDocumentName;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 关联的id
     */
    private Long relatedId;

    /**
     * 消息类型,1:文档收藏,2:文档分享,3:文档打印成功,4:文档通过审核,5:文档下架,6:文档评论,7:回复评论,8:目录收藏
     */
    private Byte messageType;

    /**
     * 消息类型,用于移动端区分,如:preview,comment,orderFinish 等
     */
    private MessageTypeEnum type;

    /**
     * extra扩展内容
     */
    private String extra;

    /**
     * 扩展key,如:wenku 等
     */
    private MessageExtraKeyEnum extraKey;

    /**
     * 是否已阅读,0:未阅读,1:已阅读,默认为:0
     */
    private Byte isReaded;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setStudentDocumentId(Long studentDocumentId) {
        this.studentDocumentId = studentDocumentId;
    }

    public Long getStudentDocumentId() {
        return studentDocumentId;
    }

    public String getStudentDocumentName() {
        return studentDocumentName;
    }

    public void setStudentDocumentName(String studentDocumentName) {
        this.studentDocumentName = studentDocumentName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setMessageType(Byte messageType) {
        this.messageType = messageType;
    }

    public Byte getMessageType() {
        return messageType;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getExtra() {
        return extra;
    }

    public void setIsReaded(Byte isReaded) {
        this.isReaded = isReaded;
    }

    public Byte getIsReaded() {
        return isReaded;
    }

    public void setActived(Byte actived) {
        this.actived = actived;
    }

    public Byte getActived() {
        return actived;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public MessageTypeEnum getType() {
        return type;
    }

    public void setType(MessageTypeEnum type) {
        this.type = type;
    }

    public MessageExtraKeyEnum getExtraKey() {
        return extraKey;
    }

    public void setExtraKey(MessageExtraKeyEnum extraKey) {
        this.extraKey = extraKey;
    }
}