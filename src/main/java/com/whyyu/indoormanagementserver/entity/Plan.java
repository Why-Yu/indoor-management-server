package com.whyyu.indoormanagementserver.entity;

import javax.persistence.*;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/6 17:22
 */
@Entity
@Table(name = "plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="planSeq")
    @SequenceGenerator(name = "planSeq",initialValue = 1, allocationSize = 1,sequenceName = "planSeq")
    private Integer index;
    private String name;
    private String path;
    private Double longitude;
    private Double latitude;

    public Plan() {
    }

    public Plan(String name, String path, Double longitude, Double latitude) {
        this.name = name;
        this.path = path;
        this.longitude = longitude;
        this.latitude = latitude;
    }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
