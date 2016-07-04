package com.suime.library.dto.pack;

/**
 * fileLinkBean
 * Created by ice on 13/11/2015.
 */
public class FileLinkBean {

    /**
     * 学生id
     */
    private Long studentId;
    /**
     * 游客,随机给的一串字符串标识
     */
    private String visitor;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件id
     */
    private Long fileId;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
