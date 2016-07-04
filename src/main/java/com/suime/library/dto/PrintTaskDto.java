package com.suime.library.dto;

import com.suime.context.model.PrintTask;

import java.sql.Timestamp;

/**
 * Created by ice on 16/11/2015.
 */
public class PrintTaskDto {


    /**
     * 下单人,如果是游客，该值0
     */
    private Long studentId;

    /**
     * student name
     */
    private String studentName;

    /**
     * 游客id,如果是游客，该值0
     */
    private String visitorId;

    /**
     * 文件名,(为去掉filelink_id而添加)
     */
    private String fileName;

    /**
     * 所属print task id
     */
    private Long printTaskId;

    /**
     * 所属打印订单 id
     */
    private Long printOrderId;

    /**
     * 打印店 id
     */
    private Long printshopId;

    /**
     * 打印店 name
     */
    private String printshopName;

    /**
     * 开始打印时回传的printerId,只做记录未关联其他表,有值时不可退款
     */
    private Integer printerId;

    /**
     * 是否双面,1:双面打印,0:单面
     */
    private Byte bothside;

    /**
     * 是否彩打,1:彩打,0:非彩打
     */
    private Byte colorful;

    /**
     * 份数,计算价格 打印页数时会用到
     */
    private Integer copies;

    /**
     * 排版
     * X13 一页三面
     */
    private String handouts;

    /**
     * 开始打印页,暂时未使用,值为-1
     */
    private Integer startPage;

    /**
     * 打印页数,暂时未使用,值为-1
     */
    private Integer countPage;

    /**
     * 文档的页数
     */
    private Integer pages;

    /**
     * 考虑版式之后的页数,创建订单时计算
     * (面数,不考虑单双面,考虑整除排版)
     */
    private Integer pageAdjusted;

    /**
     * 状态. 1:建立,2:打印中,3:打印完成
     */
    private Byte state;

    /**
     * 打印时间
     */
    private Timestamp printedAt;

    /**
     * 最后一次打印时间，可能涉及重打
     */
    private Timestamp lastPrintedAt;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    private Timestamp updatedAt;

    /**
     * 任务来源,0:个人上传文件;1:楼长小文库;2:文库
     */
    private Byte lib;

    /**
     * constructor
     */
    public PrintTaskDto() {

    }


    /**
     * constructor
     */
    public PrintTaskDto(PrintTask printTask) {
        if (printTask != null) {
            this.setPrintTaskId(printTask.getId());
            this.setLib(printTask.getLib());
            this.setState(printTask.getState().byteValue());
            this.setLastPrintedAt(printTask.getLastPrintedAt());
            this.setBothside(printTask.getBothside());
            this.setVisitorId(printTask.getVisitorId());
            this.setUpdatedAt(printTask.getUpdatedAt());
            this.setStartPage(printTask.getStartPage());
            this.setPages(printTask.getPages());
            this.setHandouts(printTask.getHandouts());
            this.setPageAdjusted(printTask.getPagesAdjusted());
            this.setColorful(printTask.getColorful());
            this.setCopies(printTask.getCopies());
            this.setFileName(printTask.getFileName());
        }
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getPrintTaskId() {
        return printTaskId;
    }

    public void setPrintTaskId(Long printTaskId) {
        this.printTaskId = printTaskId;
    }

    public Long getPrintOrderId() {
        return printOrderId;
    }

    public void setPrintOrderId(Long printOrderId) {
        this.printOrderId = printOrderId;
    }

    public Long getPrintshopId() {
        return printshopId;
    }

    public void setPrintshopId(Long printshopId) {
        this.printshopId = printshopId;
    }

    public String getPrintshopName() {
        return printshopName;
    }

    public void setPrintshopName(String printshopName) {
        this.printshopName = printshopName;
    }

    public Integer getPrinterId() {
        return printerId;
    }

    public void setPrinterId(Integer printerId) {
        this.printerId = printerId;
    }

    public Byte getBothside() {
        return bothside;
    }

    public void setBothside(Byte bothside) {
        this.bothside = bothside;
    }

    public Byte getColorful() {
        return colorful;
    }

    public void setColorful(Byte colorful) {
        this.colorful = colorful;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public String getHandouts() {
        return handouts;
    }

    public void setHandouts(String handouts) {
        this.handouts = handouts;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getCountPage() {
        return countPage;
    }

    public void setCountPage(Integer countPage) {
        this.countPage = countPage;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPageAdjusted() {
        return pageAdjusted;
    }

    public void setPageAdjusted(Integer pageAdjusted) {
        this.pageAdjusted = pageAdjusted;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Timestamp getPrintedAt() {
        return printedAt;
    }

    public void setPrintedAt(Timestamp printedAt) {
        this.printedAt = printedAt;
    }

    public Timestamp getLastPrintedAt() {
        return lastPrintedAt;
    }

    public void setLastPrintedAt(Timestamp lastPrintedAt) {
        this.lastPrintedAt = lastPrintedAt;
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

    public Byte getLib() {
        return lib;
    }

    public void setLib(Byte lib) {
        this.lib = lib;
    }
}
