package com.app.fr.fruiteefy.user_antigaspi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.LocationManager;

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
import com.app.fr.fruiteefy.user_antigaspi.fragment.MyGardenActivity;
import com.app.fr.fruiteefy.user_client.CliantChatActivity;
import com.app.fr.fruiteefy.user_client.NotificationcliantActivity;
import com.app.fr.fruiteefy.user_client.home.RecipeActivity;
import com.app.fr.fruiteefy.user_client.home.Termsandconditionactivity;
import com.app.fr.fruiteefy.user_client.mywallet.WalletActivity;
import com.app.fr.fruiteefy.user_picorear.fragment.MapViewPicoFragment;
import com.app.fr.fruiteefy.user_picorear.fragment.PicoDonationAroundmeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_antigaspi.fragment.AntiOfferFragment;
import com.app.fr.fruiteefy.user_antigaspi.fragment.AntiProductFragment;
import com.app.fr.fruiteefy.user_client.Login.LoginView.LoginActivity;
import com.app.fr.fruiteefy.user_client.Signup.RegisterAdditionalFieldActivity;
import com.app.fr.fruiteefy.user_client.home.AboutActivity;
import com.app.fr.fruiteefy.user_client.home.ContactusActivity;
import com.app.fr.fruiteefy.user_client.home.GardensActivity;
import com.app.fr.fruiteefy.user_client.home.UserCliantHomeActivity;
import com.app.fr.fruiteefy.user_client.home.WishlistActivity;
import com.app.fr.fruiteefy.user_client.profile.ProfileActivity;
import com.app.fr.fruiteefy.user_picorear.UserPicorearHomeActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserAntigaspiHomeActivity extends AppCompatActivity  {
    private static final String TAG = "UserCliantHomeActivity";
    DrawerLayout drawer;
    int notiunreadcount=0;
    ActionBarDrawerToggle toggle;
    LocationManager locationManager;
    ImageView iv_menu,iv_menu2, profile_image;
    TextView laymessage;
    NavigationView navigation_view;
    Criteria criteria;
    BottomNavigationView navigation;
    TextView termscondition,badge_notification_4,mTxtMywallet, tv_donation, tv_noti, mygarden, garden, tv_wishlist, tv_antigaspi, tv_picoreur, manageoffer, tv_client, tv_user_name, tv_email, tv_recepies, tv_logout, about, contactus, prof, cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_antigaspi_home);

        initDrawer();
        init();
        init();
        onClick();

        termscondition=findViewById(R.id.termscondition);
        termscondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAntigaspiHomeActivity.this, Termsandconditionactivity.class));
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(UserAntigaspiHomeActivity.this);

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

                    Toast.makeText(UserAntigaspiHomeActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(UserAntigaspiHomeActivity.this, message, Toast.LENGTH_SHORT).show();

                }

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();
                param.put("user_id", PrefManager.getUserId(UserAntigaspiHomeActivity.this));
                return param;
            }
        };

        requestQueue.add(stringRequest);


    }

    private void onClick() {


        mTxtMywallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PrefManager.IsLogin(UserAntigaspiHomeActivity.this)) {
                    Intent intent = new Intent(UserAntigaspiHomeActivity.this, WalletActivity.class);
                    startActivity(intent);


                    drawer.closeDrawer(Gravity.LEFT);

                } else {
                    Intent intent = new Intent(UserAntigaspiHomeActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);

                }


//                Intent intent=new Intent(UserPicorearHomeActivity.this, WalletActivity.class);
//                startActivity(intent);


            }
        });


        tv_user_name.setText(PrefManager.getFirstName(UserAntigaspiHomeActivity.this) + " " + PrefManager.getLastName(UserAntigaspiHomeActivity.this));
        tv_email.setText(PrefManager.getEmailId(UserAntigaspiHomeActivity.this));

        String img = PrefManager.getProfPic(UserAntigaspiHomeActivity.this);
        Log.d("sdsads", img);

        if (img != null && !img.equals("") && !img.equals("null")) {
            Picasso.with(this).load(BaseUrl.PROFPICURL.concat(img)).into(profile_image);
        } else {
            Picasso.with(this).load(BaseUrl.LOGO).into(profile_image);
        }
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PrefManager.IsLogin(UserAntigaspiHomeActivity.this)) {


                    Intent intent = new Intent(UserAntigaspiHomeActivity.this, ProfileActivity.class);

                    startActivity(intent);
                }
            }
        });

        laymessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PrefManager.IsLogin(UserAntigaspiHomeActivity.this)) {
                    Intent intent = new Intent(UserAntigaspiHomeActivity.this, CliantChatActivity.class);
                    startActivity(intent);

                    drawer.closeDrawer(Gravity.LEFT);

                } else {
                    Intent intent = new Intent(UserAntigaspiHomeActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);

                }

            }
        });

        tv_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAntigaspiHomeActivity.this, NotificationcliantActivity.class);
                startActivity(intent);
            }
        });
        tv_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAntigaspiHomeActivity.this, UserCliantHomeActivity.class);
                intent.putExtra("homeact", "home");
                startActivity(intent);
            }
        });


        mygarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(Gravity.LEFT);
                Intent intent = new Intent(UserAntigaspiHomeActivity.this, MyGardenActivity.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserAntigaspiHomeActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAntigaspiHomeActivity.this, ProfileActivity.class);

                startActivity(intent);
            }
        });

//        cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(UserAntigaspiHomeActivity.this, MyCartActivity.class);
//                startActivity(intent);
//            }
//        });

        tv_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAntigaspiHomeActivity.this, WishlistActivity.class);
                startActivity(intent);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserAntigaspiHomeActivity.this, ContactusActivity.class);
                startActivity(intent);
            }
        });


        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefManager.LogOut(UserAntigaspiHomeActivity.this);
                PrefManager.setIsLogin(UserAntigaspiHomeActivity.this, false);
                Intent intent = new Intent(UserAntigaspiHomeActivity.this, LoginActivity.class);
                intent.putExtra("data", "home");
                startActivity(intent);
                finish();

            }
        });


        tv_recepies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (PrefManager.IsLogin(UserAntigaspiHomeActivity.this)) {

                    Intent intent = new Intent(UserAntigaspiHomeActivity.this, RecipeActivity.class);
                    startActivity(intent);

                    drawer.closeDrawer(Gravity.LEFT);
                } else {
                    Intent intent = new Intent(UserAntigaspiHomeActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);
                    drawer.closeDrawer(Gravity.LEFT);
                }


            }
        });


        garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserAntigaspiHomeActivity.this, GardensActivity.class));
            }
        });

//
//        manageoffer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(UserAntigaspiHomeActivity.this, ManageofferActivity.class));
//            }
//        });


        tv_antigaspi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("dsdsd", PrefManager.getIsAnti(UserAntigaspiHomeActivity.this));
                if (PrefManager.IsLogin(UserAntigaspiHomeActivity.this)) {
                    if (PrefManager.getIsAnti(UserAntigaspiHomeActivity.this).equals("1")) {
                        startActivity(new Intent(UserAntigaspiHomeActivity.this, UserAntigaspiHomeActivity.class));
                    } else if (PrefManager.getIsAnti(UserAntigaspiHomeActivity.this).equals("0")) {
                        Intent intent = new Intent(UserAntigaspiHomeActivity.this, RegisterAdditionalFieldActivity.class);
                        intent.putExtra("type", "antigaspi");
                        startActivity(intent);

                    }
                } else {
                    Intent intent = new Intent(UserAntigaspiHomeActivity.this, LoginActivity.class);
                    intent.putExtra("data", "anti");
                    startActivity(intent);

                }

            }
        });

//        tv_donation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(UserAntigaspiHomeActivity.this,DonationActivity.class));
//
//            }
//        });


        tv_picoreur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue requestQueue = Volley.newRequestQueue(UserAntigaspiHomeActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("profile"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("ssdd", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("status").equals("1")) {


                                JSONObject jsonObject1 = jsonObject.getJSONObject("profiledata");
                                if (PrefManager.IsLogin(UserAntigaspiHomeActivity.this)) {

                                    if (jsonObject1.getString("is_pico").equals("0")) {
                                        Intent intent = new Intent(UserAntigaspiHomeActivity.this, RegisterAdditionalFieldActivity.class);
                                        intent.putExtra("type", "picorear");
                                        startActivity(intent);
                                        drawer.closeDrawer(Gravity.LEFT);
                                    } else if (jsonObject1.getString("subscribe_status").equals("0")) {
                                       // startActivity(new Intent(UserAntigaspiHomeActivity.this, SubscriptionActivity.class));
                                        startActivity(new Intent(UserAntigaspiHomeActivity.this, UserPicorearHomeActivity.class));

                                        drawer.closeDrawer(Gravity.LEFT);

                                    } else if (jsonObject1.getString("is_approve").equals("1")) {
                                        //Toast.makeText(UserAntigaspiHomeActivity.this, getResources().getString(R.string.pleaseckecksubmail), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(UserAntigaspiHomeActivity.this, UserPicorearHomeActivity.class));

                                        drawer.closeDrawer(Gravity.LEFT);
                                    } else if (jsonObject1.getString("is_approve").equals("2")) {
                                        startActivity(new Intent(UserAntigaspiHomeActivity.this, UserPicorearHomeActivity.class));
                                        drawer.closeDrawer(Gravity.LEFT);
                                    }

                                } else {
                                    Intent intent = new Intent(UserAntigaspiHomeActivity.this, LoginActivity.class);
                                    intent.putExtra("data", "picoact");
                                    startActivity(intent);
                                    drawer.closeDrawer(Gravity.LEFT);
                                }
                            } else {
                                Intent intent = new Intent(UserAntigaspiHomeActivity.this, LoginActivity.class);
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
                }) {


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();
                        param.put("user_id", PrefManager.getUserId(UserAntigaspiHomeActivity.this));

                        return param;
                    }
                };

                requestQueue.add(stringRequest);


            }
        });

    }

    private void init() {
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        navigation_view = findViewById(R.id.navigation_view);
        garden = findViewById(R.id.garden);
        badge_notification_4=findViewById(R.id.badge_notification_4);
        laymessage = findViewById(R.id.laymessage);
        tv_noti = findViewById(R.id.tv_noti);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        tv_antigaspi = findViewById(R.id.tv_antigaspi);
        about = findViewById(R.id.about);
        mTxtMywallet = findViewById(R.id.mywallet);
        contactus = findViewById(R.id.contactus);
        //    tv_donation=findViewById(R.id.tv_donation);
        prof = findViewById(R.id.prof);
        //cart=findViewById(R.id.cart);
        //  manageoffer=findViewById(R.id.manageoffer);
        tv_logout = findViewById(R.id.logout);
        tv_client = findViewById(R.id.tv_client);
        mygarden = findViewById(R.id.mygarden);
        tv_picoreur = findViewById(R.id.tv_picoreur);
        tv_wishlist = findViewById(R.id.tv_wishlist);
        profile_image = findViewById(R.id.profile_image);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_email = findViewById(R.id.tv_email);
        tv_recepies = findViewById(R.id.tv_recepies);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(navigation_view);

            }
        });

        iv_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(navigation_view);

            }
        });
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navigation.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(3);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

//        View badge = LayoutInflater.from(UserAntigaspiHomeActivity.this)
//                .inflate(R.layout.notification_badge, itemView, true);

        // TextView tv_noti_itm=   badge.findViewById(R.id.notifications);
        if (getIntent().hasExtra("type")) {
            if (getIntent().getStringExtra("type").equals("offer")) {

                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new AntiOfferFragment()).commit();


            } else if (getIntent().getStringExtra("type").equals("gardendelete")) {

                // navigation.setSelectedItemId(R.id.nav_basket);

            }

        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new PicoDonationAroundmeFragment(),"picodonationaroundme").commit();
        }


        drawer.setScrimColor(Color.parseColor("#33000000"));


        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            View activeLabel = item.findViewById(R.id.largeLabel);
            if (activeLabel instanceof TextView) {
                activeLabel.setPadding(0, 0, 0, 0);
            }
        }


    }//initClose

    public void closeDrawer() {
        drawer.closeDrawer(Gravity.LEFT);
    }

    public void initDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu2 = (ImageView) findViewById(R.id.iv_menu2);
        toggle = new ActionBarDrawerToggle(UserAntigaspiHomeActivity.this, drawer,
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
            Fragment fragment;


            switch (item.getItemId()) {
                case R.id.nav_list:

                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new AntiOfferFragment()).commit();

                    return true;

                case R.id.nav_donationaroundme:
                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new PicoDonationAroundmeFragment(),"picodonationaroundme").commit();

                    return true;

                case R.id.nav_orignalmap:
                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MapViewPicoFragment(),"picodonationaroundmemap").commit();


                    return true;
                case R.id.nav_map:

                    Intent mIntent = new Intent(UserAntigaspiHomeActivity.this, AntiAddofferActivity.class);
                    mIntent.putExtra("Flag", "no");
                    startActivity(mIntent);

                    return true;

                case R.id.nav_products:

                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new AntiProductFragment()).commit();
                    return true;

            }
            return false;
        }
    };





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5) {
            if (resultCode == Activity.RESULT_OK) {

                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new PicoDonationAroundmeFragment(), "picodonationaroundme").commit();


            }

            if (resultCode == Activity.RESULT_CANCELED) {

                PicoDonationAroundmeFragment picodonationaroundme = (PicoDonationAroundmeFragment) getSupportFragmentManager().findFragmentByTag("picodonationaroundme");
                picodonationaroundme.getAllProductsDetails();
            }
        }

        if (requestCode == 6) {
            if (resultCode == Activity.RESULT_OK) {

                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MapViewPicoFragment(), "picodonationaroundmemap").commit();


            }

            if (resultCode == Activity.RESULT_CANCELED) {

                MapViewPicoFragment mapViewPicoFragment = (MapViewPicoFragment) getSupportFragmentManager().findFragmentByTag("picodonationaroundmemap");
                mapViewPicoFragment.getAllProduc();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new PicoDonationAroundmeFragment(), "picodonationaroundme").commit();

                } else {
                    PicoDonationAroundmeFragment picodonationaroundme = (PicoDonationAroundmeFragment) getSupportFragmentManager().findFragmentByTag("picodonationaroundme");
                    picodonationaroundme.getAllProductsDetails();
                }
                return;
            }


            case 3: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MapViewPicoFragment(), "picodonationaroundmemap").commit();

                } else {
                    MapViewPicoFragment mapViewPicoFragment = (MapViewPicoFragment) getSupportFragmentManager().findFragmentByTag("picodonationaroundmemap");
                    mapViewPicoFragment.getAllProduc();
                }
                return;
            }
        }
    }






}
