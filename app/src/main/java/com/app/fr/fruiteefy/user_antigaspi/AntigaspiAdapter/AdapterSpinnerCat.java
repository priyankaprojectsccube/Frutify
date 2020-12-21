package com.app.fr.fruiteefy.user_antigaspi.AntigaspiAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.HelperClass;

import java.util.ArrayList;

public class AdapterSpinnerCat extends BaseAdapter {
    private ArrayList<HelperClass> mArrDataList;
    private Context mContext;
    private LayoutInflater mLytInflater;

    public AdapterSpinnerCat(ArrayList<HelperClass> mArrDataList, Context mContext) {
        this.mArrDataList = mArrDataList;
        this.mContext = mContext;
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
        mTxtName.setText(mArrDataList.get(position).getmStrName());
        return convertView;
    }
}
