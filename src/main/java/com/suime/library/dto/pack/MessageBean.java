package com.suime.library.dto.pack;

import com.suime.context.model.support.MessageExtraKeyEnum;
import com.suime.context.model.support.MessageTypeEnum;

/**
 * Created by chenqy on 2016/3/23.
 */
public class MessageBean {

    /**
     * 发送人id
     */
    private Long senderId;

    /**
     * 文档id
     */
    private Long studentDocumentId;

    /**
     * 相关文档名称
     */
    private String studentDocumentName;

    /**
     * 相关记录的 id
     */
    private Long relateId;

    /**
     * 接收人id
     */
    private Long receiverId;

    /**
     * 消息类型,1:文档收藏,2:文档分享,3:文档打印成功,4:文档通过审核,5:文档下架,6:文档评论,7:回复评论
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
     * 消息内容
     */
    private String content;

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getStudentDocumentId() {
        return studentDocumentId;
    }

    public void setStudentDocumentId(Long studentDocumentId) {
        this.studentDocumentId = studentDocumentId;
    }

    public String getStudentDocumentName() {
        return studentDocumentName;
    }

    public void setStudentDocumentName(String studentDocumentName) {
        this.studentDocumentName = studentDocumentName;
    }

    public Long getRelateId() {
        return relateId;
    }

    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Byte getMessageType() {
        return messageType;
    }

    public void setMessageType(Byte messageType) {
        this.messageType = messageType;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
