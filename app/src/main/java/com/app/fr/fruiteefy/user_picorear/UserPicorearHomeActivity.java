package com.app.fr.fruiteefy.user_picorear;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import androidx.annotation.NonNull;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.user_client.CliantChatActivity;
import com.app.fr.fruiteefy.user_client.NotificationcliantActivity;
import com.app.fr.fruiteefy.user_client.home.RecipeActivity;
import com.app.fr.fruiteefy.user_client.home.Termsandconditionactivity;
import com.app.fr.fruiteefy.user_client.mywallet.WalletActivity;
import com.app.fr.fruiteefy.user_picorear.fragment.MyPicoGardenActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_antigaspi.UserAntigaspiHomeActivity;
import com.app.fr.fruiteefy.user_client.Login.LoginView.LoginActivity;
import com.app.fr.fruiteefy.user_client.Signup.RegisterAdditionalFieldActivity;
import com.app.fr.fruiteefy.user_client.home.AboutActivity;
import com.app.fr.fruiteefy.user_client.home.ContactusActivity;
import com.app.fr.fruiteefy.user_client.home.GardensActivity;
import com.app.fr.fruiteefy.user_client.home.UserCliantHomeActivity;
import com.app.fr.fruiteefy.user_client.home.WishlistActivity;
import com.app.fr.fruiteefy.user_client.profile.ProfileActivity;
import com.app.fr.fruiteefy.user_picorear.fragment.HomeFragmentPico;
import com.app.fr.fruiteefy.user_picorear.fragment.PicoDonationAroundmeFragment;
import com.app.fr.fruiteefy.user_picorear.fragment.PicoMyOfferFragment;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class UserPicorearHomeActivity extends AppCompatActivity  {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    private TextView laymessage;
    ImageView iv_menu, profile_image;
    NavigationView navigation_view;
    Criteria criteria;
    int notiunreadcount=0;
    LinearLayout laywish;

    BottomNavigationView navigation;
    TextView termscondition,badge_notification_4,tv_antigaspi, mygarden, tv_wishlist, tv_noti, logout, tv_picoreur, tv_client, about, contactus, prof, tv_user_name, tv_email, receipts, garden, proceedsfromdonation, mTxtMywallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_picorear_home);

        initDrawer();
        init();
        onClick();

        termscondition=findViewById(R.id.termscondition);
        termscondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPicorearHomeActivity.this, Termsandconditionactivity.class));
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(UserPicorearHomeActivity.this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("client_notification"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("ffdff",response);

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")){
                        JSONArray jsonArray=jsonObject.getJSONArray("result");

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            if(jsonObject1.getString("read_status").equals("0")){
                                notiunreadcount=notiunreadcount+1;
                            }


                        }

                        if(notiunreadcount>0) {
                            badge_notification_4.setVisibility(View.VISIBLE);
                            badge_notification_4.setText(String.valueOf(notiunreadcount));
                        }



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                if (message != null) {

                    Toast.makeText(UserPicorearHomeActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(UserPicorearHomeActivity.this, message, Toast.LENGTH_SHORT).show();

                }

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();
                param.put("user_id", PrefManager.getUserId(UserPicorearHomeActivity.this));
                return param;
            }
        };

        requestQueue.add(stringRequest);






    }

    private void onClick() {

        tv_user_name.setText(PrefManager.getFirstName(UserPicorearHomeActivity.this) + " " + PrefManager.getLastName(UserPicorearHomeActivity.this));
        tv_email.setText(PrefManager.getEmailId(UserPicorearHomeActivity.this));

        String img = PrefManager.getProfPic(UserPicorearHomeActivity.this);
        Log.d("sdsads", img);

        if (img != null && !img.equals("") && !img.equals("null")) {
            Picasso.with(this).load(BaseUrl.PROFPICURL.concat(img)).into(profile_image);
        } else {
            Picasso.with(this).load(BaseUrl.LOGO).into(profile_image);
        }
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PrefManager.IsLogin(UserPicorearHomeActivity.this)) {


                    Intent intent = new Intent(UserPicorearHomeActivity.this, ProfileActivity.class);

                    startActivity(intent);
                }
            }
        });

        mTxtMywallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (PrefManager.IsLogin(UserPicorearHomeActivity.this)) {

                    Intent intent=new Intent(UserPicorearHomeActivity.this, WalletActivity.class);
                    startActivity(intent);


                    drawer.closeDrawer(Gravity.LEFT);

                } else {
                    Intent intent = new Intent(UserPicorearHomeActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);

                }


//                Intent intent=new Intent(UserPicorearHomeActivity.this, WalletActivity.class);
//                startActivity(intent);


            }
        });


        laymessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PrefManager.IsLogin(UserPicorearHomeActivity.this)) {
                    Intent intent=new Intent(UserPicorearHomeActivity.this, CliantChatActivity.class);
                    startActivity(intent);

                    drawer.closeDrawer(Gravity.LEFT);

                } else {
                    Intent intent = new Intent(UserPicorearHomeActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);

                }

            }
        });


        mygarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MyPicoGardenActivity()).commit();
//                drawer.closeDrawer(Gravity.LEFT);

                Intent intent = new Intent(UserPicorearHomeActivity.this, MyPicoGardenActivity.class);
                startActivity(intent);
            }
        });

        tv_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPicorearHomeActivity.this, UserCliantHomeActivity.class);
                intent.putExtra("homeact", "home");
                startActivity(intent);
            }
        });

        garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPicorearHomeActivity.this, GardensActivity.class));
            }
        });

        tv_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPicorearHomeActivity.this, WishlistActivity.class);
                startActivity(intent);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });


        tv_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPicorearHomeActivity.this, NotificationcliantActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PrefManager.LogOut(UserPicorearHomeActivity.this);
                PrefManager.setIsLogin(UserPicorearHomeActivity.this, false);
                Intent intent = new Intent(UserPicorearHomeActivity.this, LoginActivity.class);
                intent.putExtra("data", "home");
                startActivity(intent);
                finish();
            }
        });


        tv_antigaspi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PrefManager.IsLogin(UserPicorearHomeActivity.this)) {
                    if (PrefManager.getIsAnti(UserPicorearHomeActivity.this).equals("1")) {
                        startActivity(new Intent(UserPicorearHomeActivity.this, UserAntigaspiHomeActivity.class));
                    } else if (PrefManager.getIsAnti(UserPicorearHomeActivity.this).equals("0")) {
                        Intent intent = new Intent(UserPicorearHomeActivity.this, RegisterAdditionalFieldActivity.class);
                        intent.putExtra("type", "antigaspi");
                        startActivity(intent);

                    }
                } else {

                    Intent intent = new Intent(UserPicorearHomeActivity.this, LoginActivity.class);
                    intent.putExtra("data", "pico");
                    startActivity(intent);
                }

            }
        });

//       donationaroundme.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new PicoDonationAroundmeFragment()).commit();
//               drawer.closeDrawer(Gravity.LEFT);
//           }
//       });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPicorearHomeActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserPicorearHomeActivity.this, ProfileActivity.class);

                startActivity(intent);

            }
        });

//        proceedsfromdonation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new PicoProductFragment()).commit();
//                drawer.closeDrawer(Gravity.LEFT);
//            }
//        });

        receipts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PrefManager.IsLogin(UserPicorearHomeActivity.this)) {
                    //getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new ReceipyFragment()).commit();

                    Intent intent = new Intent(UserPicorearHomeActivity.this, RecipeActivity.class);
                    startActivity(intent);

                    drawer.closeDrawer(Gravity.LEFT);

                } else {
                    Intent intent = new Intent(UserPicorearHomeActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);

                }
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPicorearHomeActivity.this, ContactusActivity.class);
                startActivity(intent);
            }
        });


        tv_picoreur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue= Volley.newRequestQueue(UserPicorearHomeActivity.this);

                StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("profile"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("ssdd",response);

                        try {
                            JSONObject jsonObject=new JSONObject(response);

                            if(jsonObject.getString("status").equals("1")){


                                JSONObject jsonObject1=jsonObject.getJSONObject("profiledata");
                                if (PrefManager.IsLogin(UserPicorearHomeActivity.this)) {

                                    if (jsonObject1.getString("is_pico").equals("0")) {
                                        Intent intent = new Intent(UserPicorearHomeActivity.this, RegisterAdditionalFieldActivity.class);
                                        intent.putExtra("type", "picorear");
                                        startActivity(intent);
                                        drawer.closeDrawer(Gravity.LEFT);
                                    }

                                    else if(jsonObject1.getString("subscribe_status").equals("0")){
                                       // startActivity(new Intent(UserPicorearHomeActivity.this, SubscriptionActivity.class));
                                        startActivity(new Intent(UserPicorearHomeActivity.this, UserPicorearHomeActivity.class));

                                        drawer.closeDrawer(Gravity.LEFT);

                                    }
                                    else if (jsonObject1.getString("is_approve").equals("1")) {
                                        //Toast.makeText(UserPicorearHomeActivity.this, getResources().getString(R.string.pleaseckecksubmail), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(UserPicorearHomeActivity.this, UserPicorearHomeActivity.class));

                                        drawer.closeDrawer(Gravity.LEFT);
                                    }

                                    else if (jsonObject1.getString("is_approve").equals("2")) {
                                        startActivity(new Intent(UserPicorearHomeActivity.this, UserPicorearHomeActivity.class));
                                        drawer.closeDrawer(Gravity.LEFT);
                                    }

                                } else {
                                    Intent intent = new Intent(UserPicorearHomeActivity.this, LoginActivity.class);
                                    intent.putExtra("data", "picoact");
                                    startActivity(intent);
                                    drawer.closeDrawer(Gravity.LEFT);
                                }
                            }
                            else{
                                Intent intent = new Intent(UserPicorearHomeActivity.this, LoginActivity.class);
                                intent.putExtra("data", "picoact");
                                startActivity(intent);
                                drawer.closeDrawer(Gravity.LEFT);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();
                        param.put("user_id", PrefManager.getUserId(UserPicorearHomeActivity.this));

                        return param;
                    }
                };

                requestQueue.add(stringRequest);



//
//                if (PrefManager.IsLogin(UserPicorearHomeActivity.this)) {
//
//                    if (PrefManager.getIsPico(UserPicorearHomeActivity.this).equals("0")) {
//                        Intent intent = new Intent(UserPicorearHomeActivity.this, RegisterAdditionalFieldActivity.class);
//                        intent.putExtra("type", "picorear");
//                        startActivity(intent);
//                        drawer.closeDrawer(Gravity.LEFT);
//                    }
//
//                    else if(PrefManager.getSubscribepico(UserPicorearHomeActivity.this).equals("0")){
//                        startActivity(new Intent(UserPicorearHomeActivity.this, SubscriptionActivity.class));
//                        drawer.closeDrawer(Gravity.LEFT);
//
//                    }
//                    else if (PrefManager.getIsPico(UserPicorearHomeActivity.this).equals("1")) {
//                        startActivity(new Intent(UserPicorearHomeActivity.this, UserPicorearHomeActivity.class));
//                        drawer.closeDrawer(Gravity.LEFT);
//                    }
//
//                } else {
//                    Intent intent = new Intent(UserPicorearHomeActivity.this, LoginActivity.class);
//                    intent.putExtra("data", "picoact");
//                    startActivity(intent);
//                    drawer.closeDrawer(Gravity.LEFT);
//                }
            }
        });

    }

    private void init() {
        criteria = new Criteria();
        laywish=findViewById(R.id.laywish);
        badge_notification_4=findViewById(R.id.badge_notification_4);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        navigation_view = findViewById(R.id.navigation_view);
        laymessage= findViewById(R.id.laymessage);
        logout = findViewById(R.id.logout);
        mTxtMywallet = findViewById(R.id.mywallet);
        mygarden = findViewById(R.id.mygarden);
        tv_antigaspi = findViewById(R.id.tv_antigaspi);
        about = findViewById(R.id.about);
        //  donationaroundme=findViewById(R.id.donationaroundme);
        profile_image = findViewById(R.id.profile_image);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_email = findViewById(R.id.tv_email);
        receipts = findViewById(R.id.receipts);
        prof = findViewById(R.id.prof);
        tv_wishlist = findViewById(R.id.tv_wishlist);
        garden = findViewById(R.id.garden);
        tv_noti = findViewById(R.id.tv_noti);
        contactus = findViewById(R.id.contactus);
        tv_client = findViewById(R.id.tv_client);
        tv_picoreur = findViewById(R.id.tv_picoreur);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(navigation_view);

            }
        });


        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            View activeLabel = item.findViewById(R.id.largeLabel);
            if (activeLabel instanceof TextView) {
                activeLabel.setPadding(0, 0, 0, 0);
            }
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new PicoMyOfferFragment()).commit();

        drawer.setScrimColor(Color.parseColor("#33000000"));


    }//initClose

    public void closeDrawer() {
        drawer.closeDrawer(Gravity.LEFT);
    }

    public void initDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);

        toggle = new ActionBarDrawerToggle(UserPicorearHomeActivity.this, drawer,
                // Navigation menu toggle icon
                R.string.navigation_drawer_open, // Navigation drawer open description
                R.string.navigation_drawer_close // Navigation drawer close description
        );
        toggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {



            switch (item.getItemId()) {

                case R.id.nav_offer:

                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new PicoMyOfferFragment()).commit();
                    return true;

                case  R.id.nav_add:
                    Intent mIntent = new Intent(UserPicorearHomeActivity.this, AddOfferActivity.class);
                    mIntent.putExtra("From", "Add");
                    startActivity(mIntent);
                    return true;

                case R.id.nav_purchase:
                    startActivity(new Intent(UserPicorearHomeActivity.this, PicoOrderActivity.class));
                    return true;

                case R.id.nav_my_booking:

                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new HomeFragmentPico()).commit();

                    return true;


                case R.id.nav_donationaroundme:
                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new PicoDonationAroundmeFragment(),"picodonationaroundme").commit();

                    return true;

            }
            return false;
        }
    };





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 2: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new PicoDonationAroundmeFragment(), "picodonationaroundme").commit();


                } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    PicoDonationAroundmeFragment picoDonationAroundmeFragment = (PicoDonationAroundmeFragment) getSupportFragmentManager().findFragmentByTag("picodonationaroundme");

                    if (picoDonationAroundmeFragment != null) {
                        picoDonationAroundmeFragment.getAllProductsDetails();
                    }

                }
            }



        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 5) {
            if (resultCode == Activity.RESULT_OK) {
                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new PicoDonationAroundmeFragment(), "picodonationaroundme").commit();
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                PicoDonationAroundmeFragment picodonationaroundme = (PicoDonationAroundmeFragment) getSupportFragmentManager().findFragmentByTag("picodonationaroundme");

                if (picodonationaroundme != null) {
                    picodonationaroundme.getAllProductsDetails();
                }
            }
        }


    }





}
