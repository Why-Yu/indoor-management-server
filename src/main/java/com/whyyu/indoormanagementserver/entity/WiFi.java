package com.whyyu.indoormanagementserver.entity;

import javax.persistence.*;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/7/26 15:15
 */
@Entity
@Table(name = "wifi")
public class WiFi {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="wiFiSeq")
    @SequenceGenerator(name = "wiFiSeq",initialValue = 1, allocationSize = 1,sequenceName = "wiFiSeq")
    private Integer index;
    private String name;
    private Double x;
    private Double y;
    private Double h;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getH() {
        return h;
    }

    public void setH(Double h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return "WiFi{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", h=" + h +
                '}';
    }
}
