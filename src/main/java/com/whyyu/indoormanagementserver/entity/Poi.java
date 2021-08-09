package com.whyyu.indoormanagementserver.entity;

import javax.persistence.*;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/9 12:16
 */
@Entity
@Table(name = "poi")
public class Poi {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="poiSeq")
    @SequenceGenerator(name = "poiSeq",initialValue = 1, allocationSize = 1,sequenceName = "poiSeq")
    private Integer index;
    private String province;
    private String city;
    private String area;
    private String type;
    private String name;
    private Double longitude;
    private Double latitude;

    public Poi() {
    }

    public Poi(String province, String city, String area, String type, String name, Double longitude, Double latitude) {
        this.province = province;
        this.city = city;
        this.area = area;
        this.type = type;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Poi{" +
                "index=" + index +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
