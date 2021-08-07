package com.whyyu.indoormanagementserver.entity;

import javax.persistence.*;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/6 22:26
 */
@Entity
@Table(name = "remoteimage")
public class RemoteImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="remoteimageSeq")
    @SequenceGenerator(name = "remoteimageSeq",initialValue = 1, allocationSize = 1,sequenceName = "remoteimageSeq")
    private Integer index;
    private String name;
    private Integer band;
    private Integer rowIndex;
    private String date;
    private Float cloud;
    private Double longitude;
    private Double latitude;

    public RemoteImage() {
    }

    public RemoteImage(String name, Integer band, Integer rowIndex, String date, Float cloud, Double longitude, Double latitude) {
        this.name = name;
        this.band = band;
        this.rowIndex = rowIndex;
        this.date = date;
        this.cloud = cloud;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getCloud() {
        return cloud;
    }

    public void setCloud(Float cloud) {
        this.cloud = cloud;
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
        return "RemoteImage{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", band=" + band +
                ", rowIndex=" + rowIndex +
                ", date='" + date + '\'' +
                ", cloud=" + cloud +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
