package com.sigaritus.swu.nce4.thread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.sigaritus.swu.nce4.Constants;
import com.sigaritus.swu.nce4.TextActivity;
import com.sigaritus.swu.nce4.bean.Lesson;

import org.litepal.crud.DataSupport;



/**
 * Created by Administrator on 2015/6/28.
 */
public class LessonQueryThread implements Runnable {

    Intent intent;
    int id;
    Handler handler;

    public LessonQueryThread(int id,Handler handler) {

        this.id = id;
        this.handler = handler;


    }

    @Override
    public void run() {


        Lesson lesson = DataSupport.find(Lesson.class, id);

        Message message = Message.obtain();

        Bundle bundle = new Bundle();

        bundle.putString("en_text", lesson.getEn_text());

        bundle.putString("ch_text", lesson.getCh_text());

        message.setData(bundle);

        message.what = Constants.LESSON_MSG;

        handler.sendMessage(message);
    }
}
