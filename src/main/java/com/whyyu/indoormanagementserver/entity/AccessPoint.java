package com.whyyu.indoormanagementserver.entity;


import org.springframework.data.repository.query.QueryMethod;

import javax.persistence.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/7/28 11:37
 */
@Entity
@Table(name = "accesspoint")
public class AccessPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="accessPointSeq")
    @SequenceGenerator(name = "accessPointSeq",initialValue = 1, allocationSize = 1,sequenceName = "accessPointSeq")
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
}
