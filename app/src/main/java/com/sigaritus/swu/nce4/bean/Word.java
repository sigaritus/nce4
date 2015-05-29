package com.sigaritus.swu.nce4.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2015/5/30.
 */
public class Word extends DataSupport{
    private int id;
    private int lid;
    private String word;
    private int level;

    public Word() {
    }

    public Word(int id, int lid, String word, int level) {
        this.id = id;
        this.lid = lid;
        this.word = word;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
