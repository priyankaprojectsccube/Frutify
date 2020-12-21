package com.app.fr.fruiteefy.user_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.app.fr.fruiteefy.Chat.MessageActivity;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.UserChatlist;

import java.util.List;

public class UserChatAdapter extends RecyclerView.Adapter<UserChatAdapter.ViewHolder> {

    private Context mContext;
    private List<UserChatlist> mProductchatlist;
    private boolean ischat;

    public UserChatAdapter(Context mContext, List<UserChatlist> mProductchatlist) {
        this.mContext = mContext;
        this.mProductchatlist = mProductchatlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       final UserChatlist user = mProductchatlist.get(position);
//        final Productchatlist productchatlist=mProductchatlist.get(position);
//
       holder.username.setText(user.getUsername());
        if (user.getUserprofile().equals("")){
            holder.profile_image.setImageResource(R.drawable.logo);
        } else {
            Glide.with(mContext).load(BaseUrl.PROFPICURL.concat(user.getUserprofile())).into(holder.profile_image);
        }
//
//        if (ischat){
//            adname(user.getId(),productchatlist.getAdid(), holder.adname,holder.last_msg);
//        } else {
//            holder.adname.setVisibility(View.GONE);
//        }






//        Log.d("dsddds",user.getStatus());
//
//            if (user.getStatus().equals("online")){
//                holder.img_on.setVisibility(View.VISIBLE);
//                holder.img_off.setVisibility(View.GONE);
//            } else if(user.getStatus().equals("offline")){
//                holder.img_on.setVisibility(View.GONE);
//                holder.img_off.setVisibility(View.VISIBLE);
//            }
//        else {
//            holder.img_on.setVisibility(View.GONE);
//            holder.img_off.setVisibility(View.GONE);
//        }
//        Log.d("dad",user.getId());
//
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final UserChatlist user = mProductchatlist.get(position);

                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("receiverid",user.getUserId());
                intent.putExtra("name",user.getUsername());
                intent.putExtra("token",user.getToken());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductchatlist.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;
        private ImageView img_on;
        private ImageView img_off;
        private TextView last_msg
                //,adname
                        ;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            last_msg = itemView.findViewById(R.id.last_msg);

        }
    }

    //check for last message
//    private void adname(final String userid, final String adid,final TextView adname,final TextView lastmsg){
//        theLastMessage = "default";
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Chat chat = snapshot.getValue(Chat.class);
//                    if (PrefManager.getUserId(mContext) != null && chat != null) {
//                        if (chat.getReceiver().equals(PrefManager.getUserId(mContext)) && chat.getSender().equals(userid)&& chat.getAdid().equals(adid)||
//                                chat.getReceiver().equals(userid) && chat.getSender().equals(PrefManager.getUserId(mContext)) && chat.getAdid().equals(adid)) {
//
//
//
//                            lastmsg.setText(chat.getMessage());
//
//                            adTitle(adid,adname);
//
//                        }
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }


//    private void adTitle(String id, final TextView textView){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Ads").child(id);
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                textView.setText(dataSnapshot.child("title").getValue(String.class));
//
//                Log.d("sdsadsad", String.valueOf(dataSnapshot.child("title").getValue(String.class)));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });
//
//    }





}
