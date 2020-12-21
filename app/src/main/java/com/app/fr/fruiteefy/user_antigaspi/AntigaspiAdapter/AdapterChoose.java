package com.app.fr.fruiteefy.user_antigaspi.AntigaspiAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.ChooseHellperClass;
import com.app.fr.fruiteefy.Util.HelperClass;

import java.util.ArrayList;

public class AdapterChoose extends BaseAdapter {
    private ArrayList<ChooseHellperClass> mArrDataList;
    private Context mContext;
    private LayoutInflater mLytInflater;
    private  int value;

    public AdapterChoose(ArrayList<ChooseHellperClass> mArrDataList, Context mContext,int value) {
        this.mArrDataList = mArrDataList;
        this.mContext = mContext;
        this.value = value;
        mLytInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mArrDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLytInflater.inflate(R.layout.layout_spineer_itme, parent, false);
        TextView mTxtName = convertView.findViewById(R.id.tv_name);
        if(value == 0){
            mTxtName.setText(mArrDataList.get(position).getType()+" "+mArrDataList.get(position).getNom());
        }else{
            mTxtName.setText(mArrDataList.get(position).getType());
        }

        return convertView;
    }
}
