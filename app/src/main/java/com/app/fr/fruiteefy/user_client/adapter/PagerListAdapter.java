package com.app.fr.fruiteefy.user_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.app.fr.fruiteefy.user_client.home.LargeView;
import com.app.fr.fruiteefy.user_client.home.VideoviewActivity;
import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.app.fr.fruiteefy.R;
import com.bumptech.glide.Glide;


import java.util.List;

public class PagerListAdapter extends LoopingPagerAdapter<String> {


    public PagerListAdapter(Context context, List<String> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }


    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {

        return LayoutInflater.from(context).inflate(R.layout.adapter_pager, container, false);

    }


    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {

        ImageView description = convertView.findViewById(R.id.text_banner);
        ImageView playimg=convertView.findViewById(R.id.playimg);
        RelativeLayout mainlayout = convertView.findViewById(R.id.mainlayout);

        Glide.with(context)
                .load(itemList.get(listPosition))
                .thumbnail(0.1f)
                .into(description);


        String filename = itemList.get(listPosition);
        String filenameArray[] = filename.split("\\.");
        String extension = filenameArray[filenameArray.length-1];

        if(extension.equalsIgnoreCase("mp4")) {
            playimg.setVisibility(View.VISIBLE);
            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(context, VideoviewActivity.class);
                    intent.putExtra("videourl", itemList.get(listPosition));
                    context.startActivity(intent);
                }

            });
        }else{
            playimg.setVisibility(View.GONE);
            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LargeView.class);
                    intent.putExtra("largeurl", itemList.get(listPosition));
                    context.startActivity(intent);
                }
            });
        }



    }




}
