package com.app.fr.fruiteefy.user_antigaspi.AntigaspiAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;

public class AdapterSpinnerOther extends BaseAdapter {
    private String[] mArrDataList;
    private Context mContext;
    private LayoutInflater mLytInflater;

    public AdapterSpinnerOther(String[] mArrDataList, Context mContext) {
        this.mArrDataList = mArrDataList;
        this.mContext = mContext;
        mLytInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mArrDataList.length;
    }

    @Override
    public Object getItem(int position) {
        return mArrDataList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLytInflater.inflate(R.layout.layout_spineer_itme, parent, false);
        TextView mTxtName = convertView.findViewById(R.id.tv_name);
        mTxtName.setText(mArrDataList[position].toString());
        return convertView;
    }
}
