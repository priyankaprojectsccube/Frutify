package com.app.fr.fruiteefy.user_picorear.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.app.fr.fruiteefy.R;

public class SpineerUnitAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mStrArrAddress;
    private LayoutInflater layoutInflater;

    public SpineerUnitAdapter(Context mContext, String[] mStrArrAddress) {
        this.mContext = mContext;
        this.mStrArrAddress = mStrArrAddress;
        layoutInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return mStrArrAddress.length;
    }

    @Override
    public Object getItem(int position) {
        return mStrArrAddress[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            convertView = layoutInflater.inflate(R.layout.layout_singlerowspinner, parent, false);
            myViewHolder.mTxtName = convertView.findViewById(R.id.txt_name);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.mTxtName.setText(mStrArrAddress[position].toString());
        return convertView;
    }

    class MyViewHolder {
        TextView mTxtName;
    }
}
