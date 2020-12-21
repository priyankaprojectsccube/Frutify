package com.app.fr.fruiteefy.user_client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.Chat.MessageActivity;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.UserChatlist;
import com.app.fr.fruiteefy.user_client.adapter.UserChatAdapter;
import com.app.fr.fruiteefy.user_client.home.UserCliantHomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CliantChatActivity extends AppCompatActivity {



    private List<UserChatlist> usersList;
    private String requestBody = "";
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray;
    UserChatAdapter userAdapter;
    ArrayList<UserChatlist> mUsers=new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliant_chat);
        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setTitle(getResources().getString(R.string.my_messages));





        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CliantChatActivity.this));


        usersList = new ArrayList<>();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("cat");

        //reference.orderByChild("datetime");



        reference.addListenerForSingleValueEvent(new ValueEventListener(){
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            usersList.clear();


            for (DataSnapshot snapshot3 : snapshot.getChildren()) {

                Log.d("sdfsfdsf", String.valueOf(snapshot3));
                Log.d("dsfdsf",PrefManager.getUserId(CliantChatActivity.this));
                if (snapshot3.child("user_one").getValue(String.class).equals(PrefManager.getUserId(CliantChatActivity.this))) {


                    UserChatlist userchatlist = new UserChatlist();
                    userchatlist.setUserId(snapshot3.child("user_two").getValue(String.class));
                    userchatlist.setDatetime(snapshot3.child("datetime").getValue(Long.class));
                    usersList.add(userchatlist);

                } else if (snapshot3.child("user_two").getValue(String.class).equals(PrefManager.getUserId(CliantChatActivity.this))) {


                    UserChatlist userchatlist = new UserChatlist();
                    userchatlist.setUserId(snapshot3.child("user_one").getValue(String.class));
                    userchatlist.setDatetime(snapshot3.child("datetime").getValue(Long.class));
                    usersList.add(userchatlist);

                }


               Collections.sort(usersList,Collections.reverseOrder());

                for(int i=0;i<usersList.size();i++){

                    Log.d("ffdfdsf",usersList.get(i).getUsername()+" "+usersList.get(i).getDatetime().toString());
                    usersList.get(i).getUsername();
                    usersList.get(i).getDatetime();
                }


                 jsonArray = new JSONArray();



                for (int i = 0; i < usersList.size(); i++) {

                    jsonArray.put(usersList.get(i).userId);

                }

            }

            try {
                jsonObject.put("user_id", jsonArray);
                requestBody = jsonObject.toString();





                RequestQueue requestQueue= Volley.newRequestQueue(CliantChatActivity.this);
                StringRequest stringRequest1=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_user_name") , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("get_user_name",response);

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("result");

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                UserChatlist userChatlist=new UserChatlist();
                                userChatlist.setToken(jsonObject1.getString("fcm_token"));
                                userChatlist.setUsername(jsonObject1.getString("firstname").concat(" "+jsonObject1.getString("lastname")));
                                userChatlist.setUserId(jsonObject1.getString("userid"));
                                userChatlist.setUserprofile(jsonObject1.getString("profile_pic"));

                                mUsers.add(userChatlist);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        userAdapter = new UserChatAdapter(CliantChatActivity.this, mUsers);
                        recyclerView.setAdapter(userAdapter);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Log.d("sdfsfdff", String.valueOf(volleyError));
//                                String message = null;
//                                if (volleyError instanceof NetworkError) {
//                                    message = getResources().getString(R.string.cannotconnectinternate);
//                                } else if (volleyError instanceof ServerError) {
//                                    message = getResources().getString(R.string.servernotfound);
//                                } else if (volleyError instanceof AuthFailureError) {
//                                    message = getResources().getString(R.string.loginagain);
//                                } else if (volleyError instanceof ParseError) {
//                                    message = getResources().getString(R.string.tryagain);
//                                } else if (volleyError instanceof NoConnectionError) {
//                                    message = getResources().getString(R.string.cannotconnectinternate);
//                                } else if (volleyError instanceof TimeoutError) {
//                                    message = getResources().getString(R.string.connectiontimeout);
//                                }
//                                if (message != null) {
//
//                                    Toast.makeText(CliantChatActivity.this, message, Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(CliantChatActivity.this, "an error occured", Toast.LENGTH_SHORT).show();
//
//                                }
                    }
                }){
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {

                            Log.d("sdsdsdd",requestBody);
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> param=new HashMap<>();
                        param.put("Content-Type","application/json");

                        return param;
                    }
                };

                requestQueue.add(stringRequest1);




            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
        @Override
        public void onCancelled(DatabaseError databaseError) {}
    });




    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(CliantChatActivity.this, UserCliantHomeActivity.class);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(CliantChatActivity.this, UserCliantHomeActivity.class);
        startActivity(intent);
        finish();
    }
}
