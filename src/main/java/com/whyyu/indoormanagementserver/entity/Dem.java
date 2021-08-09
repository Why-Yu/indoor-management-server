package com.whyyu.indoormanagementserver.entity;

import javax.persistence.*;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/9 10:49
 */
@Entity
@Table(name = "dem")
public class Dem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="demSeq")
    @SequenceGenerator(name = "demSeq",initialValue = 1, allocationSize = 1,sequenceName = "demSeq")
    private Integer index;
    private String name;
    private Integer band;
    private Integer rowIndex;
    private Double longitude;
    private Double latitude;

    public Dem() {
    }

    public Dem(String name, Integer band, Integer rowIndex, Double longitude, Double latitude) {
        this.name = name;
        this.band = band;
        this.rowIndex = rowIndex;
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

    public Integer getBand() {
        return band;
    }

    public void setBand(Integer band) {
        this.band = band;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
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
        return "Dem{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", band=" + band +
                ", rowIndex=" + rowIndex +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
