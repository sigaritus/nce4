package com.sigaritus.swu.nce4;

import android.database.sqlite.SQLiteDatabase;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sigaritus.swu.nce4.bean.Lesson;
import com.sigaritus.swu.nce4.dao.LessonDao;
import com.sigaritus.swu.nce4.utils.ListLoader;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<List<Lesson>>{

    private RecyclerView rv ;

    private List<String> mDatas= new ArrayList<String>();

    private ListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        rv= (RecyclerView)findViewById(R.id.lesson_list);

        rv.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(0,null,this);

        mAdapter = new ListAdapter(MainActivity.this,mDatas);

        rv.setAdapter(mAdapter);


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

    @Override
    public Loader<List<Lesson>> onCreateLoader(int id, Bundle args) {

        return new ListLoader(MainActivity.this);

    }

    @Override
    public void onLoadFinished(Loader<List<Lesson>> loader, List<Lesson> data) {
//
        mAdapter.setmDatas(data);

        mAdapter.notifyDataSetChanged();

        Log.i("size-------",""+data.size());


    }

    @Override
    public void onLoaderReset(Loader<List<Lesson>> loader) {

        mAdapter.setmDatas(null);

    }
}
