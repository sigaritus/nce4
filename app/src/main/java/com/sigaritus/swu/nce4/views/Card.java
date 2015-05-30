package com.sigaritus.swu.nce4.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sigaritus.swu.nce4.R;
import com.sigaritus.swu.nce4.TextActivity;
import com.sigaritus.swu.nce4.bean.Lesson;

import java.util.List;

/**
 * Created by Administrator on 2015/5/30.
 */
public class Card extends RelativeLayout{

    private TextView title_num;
    private TextView title_view;
    private ImageView icon;

    public Card(Context context) {
        super(context, null, R.attr.cardStyle);
        init();
    }


    public Card(Context context, AttributeSet attrs) {

        super(context, attrs);

        init();
        //  = =
    }

    public void init(){
        inflate(getContext(), R.layout.card, this);
        setBackgroundColor(getResources().getColor(R.color.card_background));

        //Add missing top level attributes
        int padding = (int)getResources().getDimension(R.dimen.card_padding);
        setPadding(padding, padding, padding, padding);

        this.title_num = (TextView)findViewById(R.id.title_num);
        this.title_view = (TextView)findViewById(R.id.title_view);
        this.icon = (ImageView)findViewById(R.id.icon);

        this.icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {



                 int id = Integer.parseInt(title_num.getText().toString().substring(7,
                         title_num.getText().toString().length()));

                Intent intent = new Intent(getContext(), TextActivity.class);

                intent.putExtra("id",id);

                Log.i("....id.....swu",id+"");

                getContext().startActivity(intent);



            }
        });

    }

    public void setData(Lesson lesson){
        if (lesson!=null) {


            title_num.setText("Lesson " + lesson.getId());
            title_view.setText(lesson.getTitle());
        }else{
            Log.i("lesson is null swu","setdata");
        }

    }

}
