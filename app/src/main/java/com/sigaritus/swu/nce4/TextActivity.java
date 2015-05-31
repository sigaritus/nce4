package com.sigaritus.swu.nce4;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.ViewHolder;
import com.sigaritus.swu.nce4.bean.Lesson;

import org.litepal.crud.DataSupport;


public class TextActivity extends ActionBarActivity {

    ObservableScrollView scrollView;

    TextView textView;

    FloatingActionButton fab;

    Handler handler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text);

        Intent intent =getIntent();

        init_views();

        Thread thread =new Thread(new QueryThread(intent));

        thread.start();


//        textView.setMovementMethod(new ScrollingMovementMethod());

    }

    public void init_views(){

        textView = (TextView)findViewById(R.id.show_text);

        scrollView= (ObservableScrollView)findViewById(R.id.text_scroll);

        fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.attachToScrollView(scrollView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogPlus dialog = new DialogPlus.Builder(TextActivity.this)
                        .setContentHolder(new ViewHolder(getLayoutInflater().inflate(R.layout.dialog_content,
                                null)))
                        .setCancelable(true)
                        .create();
                dialog.show();
            }
        });

        handler = new QueryHandler();
    }

    private class QueryHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();

            String en_text = bundle.getString("en_text");

            textView.setText(en_text);

        }
    }

    private class QueryThread implements Runnable{

        Intent intent;
        int id;
        private QueryThread(Intent intent) {

            id = intent.getIntExtra("id",0);


        }

        @Override
        public void run() {

            Lesson lesson = DataSupport.find(Lesson.class,id);

            Message message = Message.obtain();

            Bundle bundle = new Bundle();

            bundle.putString("en_text",lesson.getEn_text());

            bundle.putString("ch_text",lesson.getCh_text());

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
