package com.app.fr.fruiteefy.user_client.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.MyGardenImageHelperClass;
import com.app.fr.fruiteefy.Util.PositionInterface;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int IS_VIDEO = 0;
    private static final int IS_IMAGE = 1;
    private ArrayList<MyGardenImageHelperClass> items;
    private Context mContext;
    private PositionInterface positionInterface;

    public CustomAdapter(ArrayList<MyGardenImageHelperClass> items, Context mContext, PositionInterface positionInterface) {
        this.items = items;
        this.mContext = mContext;
        this.positionInterface = positionInterface;
    }

    public void refresh(ArrayList<MyGardenImageHelperClass> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).isVideoOrImg()) {
            return IS_VIDEO;
        } else {
            return IS_IMAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == IS_VIDEO) {
            return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_video, parent, false));
        } else {
            return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_image, parent, false));
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageViewHolder) {
            onBindImageViewHolder((ImageViewHolder) holder, position);
        } else {
            onBindVideoViewHolder((VideoViewHolder) holder, position);
        }
    }

    private void onBindImageViewHolder(ImageViewHolder holder, int position) {

        if (items.get(position).getmImgFrom().equalsIgnoreCase("Api")) {
            holder.mImgProduct.setTag(position);
            Picasso.with(mContext).load(items.get(position).getmImvByte()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    int pos = (int) holder.mImgProduct.getTag();
                    items.get(pos).setmBitmap(bitmap);
                    holder.mImgProduct.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });

        } else {

            holder.mImgProduct.setImageBitmap(items.get(position).getmBitmap());
        }


        holder.mImvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionInterface.onClick(position);
                // positionInterface.onClick(i);
            }
        });


    }

    private void onBindVideoViewHolder(VideoViewHolder holder, int position) {
        if (items.get(position).getmImgFrom().equalsIgnoreCase("Api")) {

            holder.mVideo.setVideoPath(items.get(position).getmImvByte());
            holder.mVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    holder.mVideo.start();
                }
            });

//            holder.mImgProduct.setTag(position);
//            Picasso.with(mContext).load(items.get(position).getmImvByte()).into(new Target() {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                    int pos = (int) holder.mImgProduct.getTag();
//                    items.get(pos).setmBitmap(bitmap);
//                    holder.mImgProduct.setImageBitmap(bitmap);
//                }
//
//                @Override
//                public void onBitmapFailed(Drawable errorDrawable) {
//
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                }
//            });

        } else {
            holder.mVideo.setVideoURI(items.get(position).getmUri());
            holder.mVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    holder.mVideo.start();
                }
            });
            // holder.mVideo.setImageBitmap(items.get(position).getmBitmap());
        }


        holder.mImvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionInterface.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgProduct, mImvDelete;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mImgProduct = itemView.findViewById(R.id.productimg);
            mImvDelete = itemView.findViewById(R.id.imvdelete);
        }

    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImvDelete;
        private VideoView mVideo;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mVideo = itemView.findViewById(R.id.video);
            mImvDelete = itemView.findViewById(R.id.imvdelete);
        }

    }
}