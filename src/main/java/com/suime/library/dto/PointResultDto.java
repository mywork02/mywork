package com.suime.library.dto;

import java.io.Serializable;

/**
 * commentDto
 *
 * @author ice
 */
public class PointResultDto implements Serializable {

    /**
     * 文档
     */
    private StudentDocumentDto studentDocument;

    /**
     * 评论内容
     */
    private CommentDto comment;

    /**
     * 收藏记录
     */
    private MarkRecordDto markRecord;

    /**
     * 积分内容
     */
    private PointDto point;

    public StudentDocumentDto getStudentDocument() {
        return studentDocument;
    }

    public void setStudentDocument(StudentDocumentDto studentDocument) {
        this.studentDocument = studentDocument;
    }

    public CommentDto getComment() {
        return comment;
    }

    public void setComment(CommentDto comment) {
        this.comment = comment;
    }

    public MarkRecordDto getMarkRecord() {
        return markRecord;
    }

    public void setMarkRecord(MarkRecordDto markRecord) {
        this.markRecord = markRecord;
    }

    public PointDto getPoint() {
        return point;
    }

    public void setPoint(PointDto point) {
        this.point = point;
    }
}
