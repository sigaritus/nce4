package com.sigaritus.swu.nce4.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2015/5/30.
 */
public class Lesson extends DataSupport{
    private int id;
    private int unit;
    private String title;
    private String en_text;
    private String ch_text;
    private String words;

    public Lesson() {
    }

    public Lesson(int id, int unit, String title, String en_text, String ch_text, String words) {
        this.id = id;
        this.unit = unit;
        this.title = title;
        this.en_text = en_text;
        this.ch_text = ch_text;
        this.words = words;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEn_text() {
        return en_text;
    }

    public void setEn_text(String en_text) {
        this.en_text = en_text;
    }

    public String getCh_text() {
        return ch_text;
    }

    public void setCh_text(String ch_text) {
        this.ch_text = ch_text;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return id+unit+en_text+ch_text;
    }
}
