package com.app.fr.fruiteefy.Chat;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.Chat;
import com.app.fr.fruiteefy.Util.PrefManager;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;

    private String imageurl;



    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl){
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {




        Chat chat = mChat.get(position);

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(chat.getDate());



        String date = DateFormat.format("hh:mm aa", cal).toString();
        String date1 = DateFormat.format("dd MMM yyyy", cal).toString();
        Log.d("Dfdsfdffsd",chat.getMessage());

            holder.show_message.setText(chat.getMessage());

        holder.show_time.setText(date1+" "+date);



        if (imageurl.equals("default")){
            //holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            //Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }

//        if (position == mChat.size()-1){
//            if (chat.isIsseen()){
//                holder.txt_seen.setText(mContext.getResources().getString(R.string.seen));
//            } else {
//              //  holder.txt_seen.setText(mContext.getResources().getString(R.string.delivered));
//            }
//        } else {
//            holder.txt_seen.setVisibility(View.GONE);
     //   }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message,show_time;
     //   public ImageView profile_image;
        public TextView txt_seen;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
          //  profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
            show_time = itemView.findViewById(R.id.show_time);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mChat.get(position).getUser_one().equals(PrefManager.getUserId(mContext))){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}