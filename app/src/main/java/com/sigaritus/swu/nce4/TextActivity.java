package com.sigaritus.swu.nce4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rey.material.widget.Button;
import com.rey.material.widget.Slider;
import com.sigaritus.swu.nce4.bean.Lesson;
import com.sigaritus.swu.nce4.bean.Word;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextActivity extends ActionBarActivity {

    private static final int WORD_MSG = 1;

    private static final int LESSON_MSG = 0;

    private ObservableScrollView scrollView;

    private List<Word> wordList;

    private TextView textView;

    private FloatingActionButton fab;

    private Handler handler;

    private Slider slider;

    private int id;

    private String title;

    private List<Integer> wordpos;

    private List<Integer> wordLen;

    String en_text;

    private static int slide_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text);

        Intent intent = getIntent();

        id = intent.getIntExtra("id", 0);

        title = intent.getStringExtra("title");

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(title);


        init_views();

        Thread thread = new Thread(new LessonQueryThread(id));

        thread.start();


//        textView.setMovementMethod(new ScrollingMovementMethod());

    }

    public void init_views() {

        textView = (TextView) findViewById(R.id.show_text);

        scrollView = (ObservableScrollView) findViewById(R.id.text_scroll);

        wordpos = new ArrayList<>();

        wordLen = new ArrayList<>();

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.attachToScrollView(scrollView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialog = new DialogPlus.Builder(TextActivity.this)
                        .setContentHolder(new ViewHolder(getLayoutInflater().inflate(R.layout.dialog_content,
                                null)))
                        .setCancelable(true)
                        .create();
                dialog.show();

                slider = (Slider) dialog.findViewById(R.id.slider);

                slider.setValue((float)slide_value,false);

                slider.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
                    @Override
                    public void onPositionChanged(Slider slider, float v, float v2, int i, int i2) {

                        Log.i("swu-----", v + "---" + v2 + "---" + i + "----" + i2);

                        Thread thread_word = new Thread(new WordQueryThread(id, i2));

                        thread_word.start();

                        slide_value =i2;
                    }
                });


            }
        });



        handler = new QueryHandler();
    }

    private class QueryHandler extends Handler {



        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case LESSON_MSG:

                    Bundle bundle = msg.getData();

                     en_text = bundle.getString("en_text");

                    textView.setText(en_text);

                    Log.d("Lesson",Thread.currentThread().getName()+" HandlerThread is OK");

                    break;

                case WORD_MSG:

                    String text = msg.getData().getString("text");

                    if (wordList.size()!=0) {

                        textView.setText(Html.fromHtml(text));

                    }else {

                        textView.setText(en_text);
                    }

                    Log.d("Word",Thread.currentThread().getName()+" HandlerThread is OK");

                    break;

            }


        }
    }

    private class LessonQueryThread implements Runnable {

        Intent intent;
        int id;

        private LessonQueryThread(int id) {

            this.id = id;

        }

        @Override
        public void run() {

            Lesson lesson = DataSupport.find(Lesson.class, id);

            Message message = Message.obtain();

            Bundle bundle = new Bundle();

            bundle.putString("en_text", lesson.getEn_text());

            bundle.putString("ch_text", lesson.getCh_text());

            message.setData(bundle);

            message.what = LESSON_MSG;

            handler.sendMessage(message);
        }
    }

    private class WordQueryThread implements Runnable {
        int id;
        int class_word;

        private WordQueryThread(int id, int class_word) {

            this.id = id;
            this.class_word = class_word;

        }

        @Override
        public void run() {

            wordList = DataSupport.where("lid = ? and level <= ? ", id + "", class_word + "").find(Word.class);

            String text = textView.getText().toString();

            for (int i = 0; i < wordList.size(); i++) {

                String word = wordList.get(i).getWord();

                Pattern pattern = Pattern.compile(word,Pattern.CASE_INSENSITIVE);

                Matcher matcher = pattern.matcher(text);

                text = matcher.replaceAll("<font color=\"#54beba\">"+word+"</font>");

            }

            Bundle bundle = new Bundle();

            bundle.putString("text",text);

            Message message = Message.obtain();

            message.what = WORD_MSG;

            message.setData(bundle);

            handler.sendMessage(message);




        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
