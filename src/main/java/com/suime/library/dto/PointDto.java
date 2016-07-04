package com.suime.library.dto;

/**
 * Created by ice on 19/5/16.
 */
public class PointDto {

    /**
     * 获得的积分
     */
    private Integer point;

    /**
     * 可用积分，当前积分
     */
    private Long currentPoint;

    /**
     * 积分备注
     */
    private String pointMemo;

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Long getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Long currentPoint) {
        this.currentPoint = currentPoint;
    }

    public String getPointMemo() {
        return pointMemo;
    }

    public void setPointMemo(String pointMemo) {
        this.pointMemo = pointMemo;
    }
}
