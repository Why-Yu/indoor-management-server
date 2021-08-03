package com.whyyu.indoormanagementserver.entity;

import javax.persistence.*;

/**
 * @author WhyYu
 * @Description shpFile Entity,解析shapefile得来
 * @Date 2021/8/3 12:12
 */
@Entity
@Table(name = "shape_model")
public class ShapeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="modelSeq")
    @SequenceGenerator(name = "modelSeq",initialValue = 1, allocationSize = 1,sequenceName = "modelSeq")
    private Integer index;
    private String buildId;
    private String beginId;
    private Double beginX;
    private Double beginY;
    private String endId;
    private Double endX;
    private Double endY;
    private String floor;

    public ShapeModel() {
    }

    public ShapeModel(String buildId, String beginId, Double beginX, Double beginY, String endId, Double endX, Double endY, String floor) {
        this.buildId = buildId;
        this.beginId = beginId;
        this.beginX = beginX;
        this.beginY = beginY;
        this.endId = endId;
        this.endX = endX;
        this.endY = endY;
        this.floor = floor;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getBeginId() {
        return beginId;
    }

    public void setBeginId(String beginId) {
        this.beginId = beginId;
    }

    public Double getBeginX() {
        return beginX;
    }

    public void setBeginX(Double beginX) {
        this.beginX = beginX;
    }

    public Double getBeginY() {
        return beginY;
    }

    public void setBeginY(Double beginY) {
        this.beginY = beginY;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public Double getEndX() {
        return endX;
    }

    public void setEndX(Double endX) {
        this.endX = endX;
    }

    public Double getEndY() {
        return endY;
    }

    public void setEndY(Double endY) {
        this.endY = endY;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "ShapeModel{" +
                "index=" + index +
                ", buildId='" + buildId + '\'' +
                ", beginId='" + beginId + '\'' +
                ", beginX=" + beginX +
                ", beginY=" + beginY +
                ", endId='" + endId + '\'' +
                ", endX=" + endX +
                ", endY=" + endY +
                ", floor='" + floor + '\'' +
                '}';
    }
}
