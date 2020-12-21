//package com.example.hp_pc.metinpot.Chat;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.hp_pc.metinpot.R;
//import com.example.hp_pc.metinpot.Util.Chat;
//import com.example.hp_pc.metinpot.Util.MessageClass;
//import com.example.hp_pc.metinpot.Util.PrefManager;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ServerValue;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class MessageActivity extends AppCompatActivity {
//
//    ImageView iv_back,btn_send;
//    RecyclerView recyclerView;
//    List<Chat> mchat;
//    DatabaseReference reference;
//    MessageAdapter messageAdapter;
//    String receiverid,name;
//    TextView username,text_send;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_message);
//
//        iv_back=findViewById(R.id.iv_back);
//        recyclerView = findViewById(R.id.recycler_view);
//        text_send = findViewById(R.id.text_send);
//        recyclerView.setHasFixedSize(true);
//        username= findViewById(R.id.username);
//        btn_send= findViewById(R.id.btn_send);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        linearLayoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//
//
//
//
//        if(getIntent().hasExtra("receiverid")){
//            receiverid=getIntent().getStringExtra("receiverid");
//        }
//
//
//        if(getIntent().hasExtra("name")){
//            name=getIntent().getStringExtra("name");
//        }
//
//         Log.d("dfdfds",PrefManager.getUserId(MessageActivity.this));
//
//
//        username.setText(name);
//
//
//        //readMesagges(PrefManager.getUserId(MessageActivity.this),receiverid,"");
//
//        iv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//
//        btn_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                String msg = text_send.getText().toString();
//                if (!msg.equals("")){
//                    sendMessage(PrefManager.getUserId(MessageActivity.this), receiverid, msg,"");
//                } else {
//                    //Toast.makeText(MessageActivity.this, getResources().getString(R.string.cantsendemptymsg), Toast.LENGTH_LONG).show();
//                }
//                text_send.setText("");
//            }
//        });
//
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//        private void sendMessage(final String sender, final String receiver, String message,String imageurl){
//
//
//
//            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("cat");
//
//            reference1.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot snapshot) {
//
//                    for (DataSnapshot snapshot3 : snapshot.getChildren()) {
//
//                        if (snapshot3.hasChild("user_one")&& snapshot3.hasChild("user_two")) {
//
//                            Log.d("dasdasda",sender+" "+receiver);
//
//                            if(snapshot3.child("user_one").getValue().equals(sender) && snapshot3.child("user_two").getValue().equals(receiver)){
//                                Toast.makeText(MessageActivity.this, "one", Toast.LENGTH_SHORT).show();
//                                      Log.d("DFdfdf","one");
//
//
//                            }
//
//                            else if(snapshot3.child("user_one").getValue().equals(receiver) && snapshot3.child("user_two").getValue().equals(sender)){
//
//                                Toast.makeText(MessageActivity.this, "two", Toast.LENGTH_SHORT).show();
//                                Log.d("DFdfdf","two");
//
//
//                            }
//
//                            else{
//
//
//
//                                Log.d("DFdfdf","three");
//                                HashMap<String, Object> hashMap = new HashMap<>();
//                                hashMap.put("user_one", sender);
//                                hashMap.put("user_two", receiver);
//
//
//
//                                reference1.push().setValue(hashMap);
//
//                            }
//
//
//                        }
//                    }
//
//
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//
//
//
//
//
//
//    }
//
//
//
//
//
//
//
//
//    private void readMesagges(final String sender, final String receiver, final String imageurl){
//
//        mchat = new ArrayList<>();
//
//        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("cat");
//
//        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//
//                for (DataSnapshot snapshot3 : snapshot.getChildren()) {
//
//                if (snapshot3.hasChild("user_one")&& snapshot3.hasChild("user_two")) {
//
//                    Log.d("fdsfsfs", String.valueOf(snapshot3.child("user_one").getValue()));
//
//                    if(snapshot3.child("user_one").getValue().equals(sender) && snapshot3.child("user_two").getValue().equals(receiver)){
//                        Toast.makeText(MessageActivity.this, "dfgfdgfg", Toast.LENGTH_SHORT).show();
//
//                                                    for (DataSnapshot snapshot2 : snapshot3.child("message").getChildren()) {
//
//                                    Log.d("sdadadasd", String.valueOf(snapshot2));
//
//                                    Chat chat = snapshot2.getValue(Chat.class);
//
//                                    mchat.add(chat);
//
//                                    messageAdapter = new MessageAdapter(MessageActivity.this, mchat, imageurl);
//                                    recyclerView.setAdapter(messageAdapter);
//                                }
//
//                    }
//
//                    else if(snapshot3.child("user_one").getValue().equals(receiver) && snapshot3.child("user_two").getValue().equals(sender)){
//
//
//
//                    }
//
//
//                    }
//                }
//
//
////                    reference1.child(sender+"_"+receiver).child("message").addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            mchat.clear();
////
////                            Log.d("ggggh","kkj");
////
////                            for (DataSnapshot snapshot3 : dataSnapshot.getChildren()) {
////
////                                    Log.d("sdadadasd", String.valueOf(snapshot3));
////
////                                    Chat chat = snapshot3.getValue(Chat.class);
////
////                                    mchat.add(chat);
////
////                                    messageAdapter = new MessageAdapter(MessageActivity.this, mchat, imageurl);
////                                    recyclerView.setAdapter(messageAdapter);
////                                }
////
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                        }
////                    });
////
////
////                }
////                else if(snapshot.hasChild(receiver+"_"+sender)){
////
////
////                    reference1.child(receiver+"_"+sender).child("message").addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            mchat.clear();
////
////                            Log.d("ggggh","kkj");
////
////                            for (DataSnapshot snapshot3 : dataSnapshot.getChildren()) {
////
////                                    Chat chat = snapshot3.getValue(Chat.class);
////
////                                    mchat.add(chat);
////
////                                    messageAdapter = new MessageAdapter(MessageActivity.this, mchat, imageurl);
////                                    recyclerView.setAdapter(messageAdapter);
////                                }
////
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                        }
////                    });
////                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//
//    }
//
//}



package com.app.fr.fruiteefy.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.APIService;
import com.app.fr.fruiteefy.Util.Chat;
import com.app.fr.fruiteefy.Util.Client;
import com.app.fr.fruiteefy.Util.Data;
import com.app.fr.fruiteefy.Util.MyResponse;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Sender;
import com.app.fr.fruiteefy.user_client.CliantChatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    ImageView iv_back,btn_send;
    RecyclerView recyclerView;
    List<Chat> mchat;
    APIService apiService;
    DatabaseReference reference;
    MessageAdapter messageAdapter;
    String receiverid,name,Token="";
    TextView username,text_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        iv_back=findViewById(R.id.iv_back);
        recyclerView = findViewById(R.id.recycler_view);
        text_send = findViewById(R.id.text_send);
        recyclerView.setHasFixedSize(true);
        username= findViewById(R.id.username);
        btn_send= findViewById(R.id.btn_send);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);




        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        if(getIntent().hasExtra("receiverid")){
            receiverid=getIntent().getStringExtra("receiverid");
        }
        if(getIntent().hasExtra("token")){
            Token=getIntent().getStringExtra("token");
        }


        if(getIntent().hasExtra("name")){
            name=getIntent().getStringExtra("name");
        }

        Log.d("dfdfds",PrefManager.getUserId(MessageActivity.this));


        username.setText(name);


        readMesagges(PrefManager.getUserId(MessageActivity.this),receiverid,"");

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(getIntent().hasExtra("name")){
                    finish();
                }else{
                    Intent intent=new Intent(MessageActivity.this, CliantChatActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });


        btn_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                String msg = text_send.getText().toString();
                if (!msg.equals("")){

                    sendMessage(PrefManager.getUserId(MessageActivity.this), receiverid, msg);

                }
                else {


                }
                text_send.setText("");
            }
        });


    }


    private void sendNotifiaction(String token,String username, final String message){




                    Data data = new Data(PrefManager.getUserId(MessageActivity.this),R.drawable.logo, username+": "+message, "Nouveau message","chatnoti",username,token);



                    Sender sender = new Sender(data, token);

                   apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                                    Log.d("sdfsff", String.valueOf(response));
                                    if (response.code() == 200){


                                        Log.d("dsdsadd", String.valueOf(response.body().success));
                                        if (response.body().success != 1){

                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {
                                    Log.d("rdfdf", String.valueOf(t));

                                }
                            });




    }


    private void sendMessage(final String sender, final String receiver, String message){


        Log.d("dsfdgdss",sender+" "+receiver);

        sendNotifiaction(Token,name,message);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("cat");


        Log.d("dsfdsfdsfs",sender+" "+receiver);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {





                if (snapshot.hasChild(sender+"_"+receiver)) {


                    DatabaseReference reference3= FirebaseDatabase.getInstance().getReference().child("cat").child(sender+"_"+receiver);


                    reference3.child("datetime").setValue(ServerValue.TIMESTAMP);


                    reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {



                            if(snapshot.hasChild("message")) {

                                HashMap<String, Object> hashMap2 = new HashMap<>();
                                hashMap2.put("message", message);
                                hashMap2.put("user_one", sender);
                                hashMap2.put("date", ServerValue.TIMESTAMP);
                                reference3.child("message").push().setValue(hashMap2);

                            }

                            else
                                {

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("user_one", sender);
                                hashMap.put("user_two", receiver);
                                reference.child(sender+"_"+receiver).setValue(hashMap);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }
                else if(snapshot.hasChild(receiver+"_"+sender)){



                    DatabaseReference reference3= FirebaseDatabase.getInstance().getReference().child("cat").child(receiver+"_"+sender);

                    reference3.child("datetime").setValue(ServerValue.TIMESTAMP);

                    reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            if(snapshot.hasChild("message")) {


                                HashMap<String, Object> hashMap2 = new HashMap<>();
                                hashMap2.put("message", message);
                                hashMap2.put("user_one", sender);
                                hashMap2.put("date", ServerValue.TIMESTAMP);
                                reference3.child("message").push().setValue(hashMap2);
                            }
                            else{

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("user_one", sender);
                                hashMap.put("user_two", receiver);


                                reference.child(sender+"_"+receiver).setValue(hashMap);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                else{

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("user_one", sender);
                    hashMap.put("user_two", receiver);
                    hashMap.put("datetime", ServerValue.TIMESTAMP);
                    reference.child(sender+"_"+receiver).setValue(hashMap);

                    HashMap<String, Object> hashMap2 = new HashMap<>();
                    hashMap2.put("message", message);
                    hashMap2.put("user_one", sender);
                    hashMap2.put("date", ServerValue.TIMESTAMP);

                    DatabaseReference reference3= FirebaseDatabase.getInstance().getReference().child("cat").child(sender+"_"+receiver).child("message");
                    reference3.push().setValue(hashMap2);

                    readMesagges(PrefManager.getUserId(MessageActivity.this),receiverid,"");


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//

    }








    private void readMesagges(final String sender, final String receiver, final String imageurl){

        mchat = new ArrayList<>();





        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("cat");







        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChild(sender+"_"+receiver)) {


                    reference1.child(sender+"_"+receiver).child("message").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mchat.clear();

                            Log.d("ggggh","kkj");

                            for (DataSnapshot snapshot3 : dataSnapshot.getChildren()) {

                                Log.d("sdadadasd", String.valueOf(snapshot3));

                                Chat chat = snapshot3.getValue(Chat.class);

                                mchat.add(chat);

                                messageAdapter = new MessageAdapter(MessageActivity.this, mchat, imageurl);
                                recyclerView.setAdapter(messageAdapter);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setItemViewCacheSize(20);
                                recyclerView.setDrawingCacheEnabled(true);
                                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                else if(snapshot.hasChild(receiver+"_"+sender)){


                    reference1.child(receiver+"_"+sender).child("message").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mchat.clear();

                            Log.d("ggggh","kkj");

                            for (DataSnapshot snapshot3 : dataSnapshot.getChildren()) {

                                Chat chat = snapshot3.getValue(Chat.class);

                                mchat.add(chat);

                                messageAdapter = new MessageAdapter(MessageActivity.this, mchat, imageurl);
                                recyclerView.setAdapter(messageAdapter);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setItemViewCacheSize(20);
                                recyclerView.setDrawingCacheEnabled(true);
                                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(MessageActivity.this, CliantChatActivity.class);
        startActivity(intent);
        finish();
    }
}

