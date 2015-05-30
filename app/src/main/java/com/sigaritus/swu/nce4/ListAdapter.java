package com.sigaritus.swu.nce4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2015/5/27.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>{

    private Context mContext;
    private List mDatas;

    public ListAdapter(Context context, List<?> Datas) {
        mContext = context;
        mDatas = Datas;


    }

    public List getmDatas() {
        return mDatas;
    }

    public void setmDatas(List mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ListViewHolder view = new ListViewHolder(LayoutInflater.from(mContext).inflate(
                R.layout.itemlayout,viewGroup,false
        ));

        return view;
    }

    @Override
    public void onBindViewHolder(ListViewHolder viewHolder, int i) {

        viewHolder.tv.setText(mDatas.get(i).toString());


    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        ListViewHolder(View itemView) {
            super(itemView);
            tv= (TextView)itemView.findViewById(R.id.recycle_list_item);
        }
    }

}
