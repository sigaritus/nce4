package com.sigaritus.swu.nce4;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.sigaritus.swu.nce4.utils.DBManager;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private RecyclerView rv ;
    private List<String> mDatas= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SQLiteDatabase sq = Connector.getDatabase();

        DBManager dbHelper = new DBManager(this);
//        dbHelper.openDatabase();
//        dbHelper.closeDatabase();
        rv= (RecyclerView)findViewById(R.id.lesson_list);

        for (int i=0;i<30;i++){
            mDatas.add("sigaritus "+i);
        }

        rv.setLayoutManager(new LinearLayoutManager(this));

        rv.setAdapter(new ListAdapter(MainActivity.this,mDatas));
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
