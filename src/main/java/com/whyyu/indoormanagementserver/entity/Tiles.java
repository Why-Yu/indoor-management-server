package com.whyyu.indoormanagementserver.entity;

import javax.persistence.*;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/5 10:18
 */
@Entity
@Table(name = "tiles")
public class Tiles {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tilesSeq")
    @SequenceGenerator(name = "tilesSeq",initialValue = 1, allocationSize = 1,sequenceName = "tilesSeq")
    private Integer index;
    private String name;
    private String zoom;
    private String row;
    private String col;

    public Tiles() {
    }

    public Tiles(String name, String zoom, String row, String col) {
        this.name = name;
        this.zoom = zoom;
        this.row = row;
        this.col = col;
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

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "Tiles{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", zoom='" + zoom + '\'' +
                ", row='" + row + '\'' +
                ", col='" + col + '\'' +
                '}';
    }
}

