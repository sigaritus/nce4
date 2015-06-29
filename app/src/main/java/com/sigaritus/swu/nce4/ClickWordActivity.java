package com.sigaritus.swu.nce4;

/**
 * Created by Administrator on 2015/6/28.
 */
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.sigaritus.swu.nce4.thread.LessonQueryThread;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import butterknife.ButterKnife;
import butterknife.InjectView;

public class ClickWordActivity extends AppCompatActivity {

    @InjectView(R.id.text_art)
    TextView art;

    private int id;

    private String title;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        ButterKnife.inject(this);

        handler = new ArticleHandler();

        Intent intent = getIntent();

        id = intent.getIntExtra("id", 0);

        title = intent.getStringExtra("title");

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(title);

        Thread thread = new Thread(new LessonQueryThread(id,handler));

        thread.start();


    }

    private class ArticleHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();

            String en_text = bundle.getString("en_text");

            art.setText(en_text,TextView.BufferType.SPANNABLE);

            //点击每个单词响应
            getEveryWord(art);


        /*
        LinkMovementMethod（）里面有个OnKeyDown()，
        然后如果是点击的话会跳到action()里的click中.然后调用ClickableSpan.onClick()..
         */
            art.setMovementMethod(LinkMovementMethod.getInstance());

        }
    }

    public void getEveryWord(TextView tv){
        Spannable span = (Spannable)tv.getText();
        String s[] = art.getText().toString().split(" ");
        int start =0;
        int end =0;
        for (String s1 : s) {

            ClickableSpan clickspan = getClickbleSpan();

            end = start + s1.length();



            span.setSpan(clickspan,start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            start =  end +1;

        }

        tv.setHighlightColor(Color.rgb(41,153,129));

    }

    private ClickableSpan getClickbleSpan(){
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                TextView text = (TextView)widget;
                final String s = text.getText().subSequence(text.getSelectionStart(),
                        text.getSelectionEnd()).toString();
//                Toast.makeText(MainActivity.this,"onclick",Toast.LENGTH_SHORT).show();
                WordQueryClient.get(s,null,new JsonHttpResponseHandler(){

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                                try {
                                    String msg = "";
                                    JSONObject data = response.getJSONObject("data");
                                    JSONObject en_def = data.getJSONObject("en_definitions");
                                    if(en_def.has("n")){
                                        JSONArray noun=en_def.getJSONArray("n");
                                        msg=msg+"n:"+"\n";
                                        for (int i = 0; i <noun.length(); i++) {
                                            msg=msg+noun.get(i)+"\n";
                                            Log.i(msg,"-----------.");
                                        }
                                    }else if (en_def.has("v")){
                                        JSONArray verb = en_def.getJSONArray("v");
                                        msg=msg+"v:"+"\n";
                                        for (int i = 0; i < verb.length(); i++) {
                                            msg=msg+verb.get(i)+"\n";
                                        }
                                    }
                                    JSONObject ch_def = data.getJSONObject("cn_definition");
                                    String ch_def_str = ch_def.getString("defn");

                                    final String audio_url = data.getString("audio");

//                                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();

                                    DialogPlus word_dialog = new DialogPlus.Builder(ClickWordActivity.this)
                                            .setContentHolder(new ViewHolder(R.layout.word_dialog))
                                            .setCancelable(true)
                                            .create();


                                    TextView word = (TextView)word_dialog.findViewById(R.id.word_text_dialog);

                                    TextView word_content = (TextView)word_dialog.findViewById(R.id.word_content_dialog);

                                    ImageView pronoun = (ImageView)word_dialog.findViewById(R.id.pronounce);

                                    word.setText(s);

                                    word_content.setText(msg+"\n"+ch_def_str);


                                    pronoun.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AudioPlay.getInstance().play(audio_url);
                                        }
                                    });

                                    word_dialog.show();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                                Toast.makeText(ClickWordActivity.this,"fail",Toast.LENGTH_SHORT).show();
                            }
                        }
                );

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.GRAY);
                ds.setUnderlineText(false);
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
