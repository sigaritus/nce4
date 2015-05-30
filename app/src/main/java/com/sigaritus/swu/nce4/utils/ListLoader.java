package com.sigaritus.swu.nce4.utils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.sigaritus.swu.nce4.bean.Lesson;
import com.sigaritus.swu.nce4.dao.LessonDao;

import java.util.List;

/**
 * Created by Administrator on 2015/5/30.
 */
public class ListLoader extends AsyncTaskLoader<List<Lesson>> {

    public ListLoader(Context context) {

        super(context);

    }

    @Override
    public List<Lesson> loadInBackground() {

        List<Lesson> list = new LessonDao().getLessons();

        return list;
    }

}
