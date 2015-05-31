package com.sigaritus.swu.nce4.utils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.sigaritus.swu.nce4.bean.Lesson;
import com.sigaritus.swu.nce4.dao.LessonDao;

import java.util.List;

public class ListLoader extends AsyncTaskLoader<List<Lesson>> {

    private List<Lesson> mData;

    public ListLoader(Context context) {

        super(context);

    }

    @Override
    public List<Lesson> loadInBackground() {

        mData = new LessonDao().getLessons();

        return mData;
    }


    @Override
    public void deliverResult(List<Lesson> data) {
        if (isReset()) {
            releaseResources(data);
            return;
        }

        List<Lesson> oldData = mData;
        mData = data;

        if (isStarted()) {

            super.deliverResult(data);
        }


        if (oldData != null && oldData != data) {
            releaseResources(oldData);
        }
    }


    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }



        if (takeContentChanged() || mData == null) {

            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {

        cancelLoad();


    }

    @Override
    protected void onReset() {

        onStopLoading();


        if (mData != null) {
            releaseResources(mData);
            mData = null;
        }



    }

    @Override
    public void onCanceled(List<Lesson> data) {

        super.onCanceled(data);

        releaseResources(data);
    }

    private void releaseResources(List<Lesson> data) {

    }



}
