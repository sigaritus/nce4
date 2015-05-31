package com.sigaritus.swu.nce4.dao;

import com.sigaritus.swu.nce4.bean.Lesson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/30.
 */
public class LessonDao {

    private List<Lesson> lessons;

    private int count;

    public LessonDao() {

        count = DataSupport.count(Lesson.class);

        lessons = new ArrayList<Lesson>();


    }

    public List<Lesson> getLessons() {

        lessons = DataSupport.findAll(Lesson.class);

        return lessons;
    }

    public List<String> getTitles() {

        List<String> titles = new ArrayList<String>();

        if (lessons.size() != count) {

            getLessons();

        }

        for (int i = 0; i < lessons.size(); i++) {

            titles.add(lessons.get(i).getTitle());

        }

        return titles;

    }

}
