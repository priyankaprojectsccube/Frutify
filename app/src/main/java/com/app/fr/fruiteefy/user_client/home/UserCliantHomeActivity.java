package com.app.fr.fruiteefy.user_client.home;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.app.fr.fruiteefy.user_client.map.MapViewBuyFragment;
import com.app.fr.fruiteefy.user_client.mywallet.WalletActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_antigaspi.UserAntigaspiHomeActivity;
import com.app.fr.fruiteefy.user_client.Login.LoginView.LoginActivity;
import com.app.fr.fruiteefy.user_client.NotificationcliantActivity;
import com.app.fr.fruiteefy.user_client.Signup.RegisterAdditionalFieldActivity;
import com.app.fr.fruiteefy.user_client.cart.MyCartFragment;
import com.app.fr.fruiteefy.user_client.profile.ProfileActivity;
import com.app.fr.fruiteefy.user_picorear.UserPicorearHomeActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class UserCliantHomeActivity extends AppCompatActivity {
RelativeLayout tool2;
    DrawerLayout drawer;
    int notiunreadcount=0;
    TextView badge_notification_4;
    private RelativeLayout rellay1;
    ActionBarDrawerToggle toggle;
    private LinearLayout laylogin, laylogout, laypico, layanti, layprof, laymessage, laynoti, laywish;
    ImageView iv_menu, profile_image;
    TextView termscondition,mTxtMywallet,mygarden, tv_gardens, tv_antigaspi, tv_picoreur, cliant1, tv_logout, tv_user_name, tv_email,
            prof, login, tv_contactus, tv_about, tv_recepies, tv_wishlist,hide_unavailable,Earsefilter;
    NavigationView navigation_view;
    Criteria criteria;
    BottomNavigationView navigation;
    View main_content;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    String languageToLoad = "", language_code = "";
    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        Log.d("dssddda",PrefManager.getUserId(UserCliantHomeActivity.this));


        init();

        initDrawer();
        onClick();
        tool2= findViewById(R.id.tool2);
        Earsefilter = findViewById(R.id.Earsefilter);

        termscondition=findViewById(R.id.termscondition);
        termscondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserCliantHomeActivity.this, Termsandconditionactivity.class));
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(UserCliantHomeActivity.this);

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

                    Toast.makeText(UserCliantHomeActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(UserCliantHomeActivity.this, message, Toast.LENGTH_SHORT).show();

                }

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();
                param.put("user_id", PrefManager.getUserId(UserCliantHomeActivity.this));
                return param;
            }
        };

        requestQueue.add(stringRequest);

    }

    public void onClick() {


        if (PrefManager.IsLogin(this)) {

            laylogin.setVisibility(View.GONE);
            laylogout.setVisibility(View.VISIBLE);
            laypico.setVisibility(View.VISIBLE);
            layanti.setVisibility(View.VISIBLE);
            layprof.setVisibility(View.VISIBLE);
            laymessage.setVisibility(View.VISIBLE);
            laynoti.setVisibility(View.VISIBLE);
            laywish.setVisibility(View.VISIBLE);


        }

//        tv_user_name.setText(PrefManager.getFirstName(UserCliantHomeActivity.this) + " " + PrefManager.getLastName(UserCliantHomeActivity.this));
//        tv_email.setText(PrefManager.getEmailId(UserCliantHomeActivity.this));

        String img = PrefManager.getProfPic(UserCliantHomeActivity.this);

        if (img != null && !img.equals("") && !img.equals("null")) {
            Picasso.with(this).load(BaseUrl.PROFPICURL.concat(img)).into(profile_image);
        } else {
            Picasso.with(this).load(BaseUrl.LOGO).into(profile_image);
        }
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PrefManager.IsLogin(UserCliantHomeActivity.this)) {

                    Intent intent = new Intent(UserCliantHomeActivity.this, ProfileActivity.class);

                    startActivity(intent);
                }

            }
        });

        tv_gardens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserCliantHomeActivity.this, GardensActivity.class));
                finish();
                drawer.closeDrawer(Gravity.LEFT);
            }
        });


        laymessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PrefManager.IsLogin(UserCliantHomeActivity.this)) {
                  Intent intent=new Intent(UserCliantHomeActivity.this, CliantChatActivity.class);
                  startActivity(intent);

                    drawer.closeDrawer(Gravity.LEFT);

                } else {
                    Intent intent = new Intent(UserCliantHomeActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);

                }

            }
        });
        mTxtMywallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (PrefManager.IsLogin(UserCliantHomeActivity.this)) {


                    Intent intent=new Intent(UserCliantHomeActivity.this, WalletActivity.class);
                    startActivity(intent);

                    drawer.closeDrawer(Gravity.LEFT);

                } else {
                    Intent intent = new Intent(UserCliantHomeActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);

                }

            }
        });



        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(UserCliantHomeActivity.this, ProfileActivity.class);

                startActivity(intent);
                drawer.closeDrawer(Gravity.LEFT);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCliantHomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        tv_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCliantHomeActivity.this, WishlistActivity.class);
                startActivity(intent);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        tv_recepies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PrefManager.IsLogin(UserCliantHomeActivity.this)) {

                    Intent intent=new Intent(UserCliantHomeActivity.this,RecipeActivity.class);
                    startActivity(intent);

                    drawer.closeDrawer(Gravity.LEFT);
                } else {
                    Intent intent = new Intent(UserCliantHomeActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);
                    drawer.closeDrawer(Gravity.LEFT);
                }
            }
        });

        cliant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserCliantHomeActivity.this, UserCliantHomeActivity.class);
                intent.putExtra("homeact", "home");
                startActivity(intent);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        laynoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCliantHomeActivity.this, NotificationcliantActivity.class);
                startActivity(intent);
            }
        });

        tv_antigaspi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("dsdsd", PrefManager.getIsAnti(UserCliantHomeActivity.this));
                if (PrefManager.IsLogin(UserCliantHomeActivity.this)) {
                    if (PrefManager.getIsAnti(UserCliantHomeActivity.this).equals("1")) {
                        startActivity(new Intent(UserCliantHomeActivity.this, UserAntigaspiHomeActivity.class));
                        drawer.closeDrawer(Gravity.LEFT);
                    } else if (PrefManager.getIsAnti(UserCliantHomeActivity.this).equals("0")) {
                        Intent intent = new Intent(UserCliantHomeActivity.this, RegisterAdditionalFieldActivity.class);
                        intent.putExtra("type", "antigaspi");
                        startActivity(intent);
                        drawer.closeDrawer(Gravity.LEFT);

                    }
                } else {
                    Intent intent = new Intent(UserCliantHomeActivity.this, LoginActivity.class);
                    intent.putExtra("data", "antigaspi");
                    startActivity(intent);
                    drawer.closeDrawer(Gravity.LEFT);
                }

            }
        });


        tv_picoreur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue= Volley.newRequestQueue(UserCliantHomeActivity.this);

                StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("profile"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("ssdd",response);

                        try {
                            JSONObject jsonObject=new JSONObject(response);

                            if(jsonObject.getString("status").equals("1")){


                                JSONObject jsonObject1=jsonObject.getJSONObject("profiledata");
                                if (PrefManager.IsLogin(UserCliantHomeActivity.this)) {

                                    if (jsonObject1.getString("is_pico").equals("0")) {
                                        Intent intent = new Intent(UserCliantHomeActivity.this, RegisterAdditionalFieldActivity.class);
                                        intent.putExtra("type", "picorear");
                                        startActivity(intent);
                                        drawer.closeDrawer(Gravity.LEFT);
                                    }

                                    else if(jsonObject1.getString("subscribe_status").equals("0")){
//                                        startActivity(new Intent(UserCliantHomeActivity.this, SubscriptionActivity.class));
//                                        drawer.closeDrawer(Gravity.LEFT);
                                        startActivity(new Intent(UserCliantHomeActivity.this, UserPicorearHomeActivity.class));
                                        drawer.closeDrawer(Gravity.LEFT);

                                    }
                                    else if (jsonObject1.getString("is_approve").equals("1")) {
//                                        Toast.makeText(UserCliantHomeActivity.this, getResources().getString(R.string.pleaseckecksubmail), Toast.LENGTH_SHORT).show();
//                                        drawer.closeDrawer(Gravity.LEFT);
                                        startActivity(new Intent(UserCliantHomeActivity.this, UserPicorearHomeActivity.class));
                                        drawer.closeDrawer(Gravity.LEFT);
                                    }

                                    else if (jsonObject1.getString("is_approve").equals("2")) {
                                        startActivity(new Intent(UserCliantHomeActivity.this, UserPicorearHomeActivity.class));
                                        drawer.closeDrawer(Gravity.LEFT);
                                    }

                                } else {
                                    Intent intent = new Intent(UserCliantHomeActivity.this, LoginActivity.class);
                                    intent.putExtra("data", "picoact");
                                    startActivity(intent);
                                    drawer.closeDrawer(Gravity.LEFT);
                                }
                            }

                            else{
                                Intent intent = new Intent(UserCliantHomeActivity.this, LoginActivity.class);
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
                        param.put("user_id", PrefManager.getUserId(UserCliantHomeActivity.this));

                        return param;
                    }
                };

                requestQueue.add(stringRequest);

            }
        });

        tv_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UserCliantHomeActivity.this, ContactusActivity.class));
                drawer.closeDrawer(Gravity.LEFT);
                //  drawer.closeDrawer(Gravity.LEFT);
//                fragment = new ContactusFragment();
//                if (fragment != null) {
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.rl_main, fragment).addToBackStack(null).commit();
//
//                }

            }
        });

        tv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserCliantHomeActivity.this, AboutActivity.class));
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        mygarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserCliantHomeActivity.this, MyGardenActivity.class);
                startActivity(intent);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });


        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PrefManager.LogOut(UserCliantHomeActivity.this);
                PrefManager.setIsLogin(UserCliantHomeActivity.this, false);
                Intent intent = new Intent(UserCliantHomeActivity.this, LoginActivity.class);
                intent.putExtra("data", "home");
                startActivity(intent);
                finish();
            }
        });


    }


    private void init() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        navigation_view = findViewById(R.id.navigation_view);
        badge_notification_4=findViewById(R.id.badge_notification_4);
        mygarden = findViewById(R.id.mygarden);
        tv_recepies = findViewById(R.id.tv_recepies);
        laylogout = findViewById(R.id.laylogout);
        laymessage = findViewById(R.id.laymessage);
        tv_antigaspi = findViewById(R.id.tv_antigaspi);
        cliant1 = findViewById(R.id.cliant1);
        tv_logout = findViewById(R.id.logout);
        tv_contactus = findViewById(R.id.tv_contactus);
        layprof = findViewById(R.id.layprof);
        tv_about = findViewById(R.id.tv_about);
        tv_wishlist = findViewById(R.id.tv_wishlist);
        profile_image = findViewById(R.id.profile_image);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_email = findViewById(R.id.tv_email);
        laywish = findViewById(R.id.laywish);
        laypico = findViewById(R.id.laypico);
        laynoti = findViewById(R.id.laynoti);
        tv_gardens = findViewById(R.id.gardens);
        mTxtMywallet = findViewById(R.id.mywallet);

        layanti = findViewById(R.id.layanti);
        tv_picoreur = findViewById(R.id.tv_picoreur);
        prof = findViewById(R.id.prof);
        laylogin = findViewById(R.id.laylogin);
        login = findViewById(R.id.login);
        if (PrefManager.IsLogin(UserCliantHomeActivity.this)) {
            tv_logout.setVisibility(View.VISIBLE);
        }
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(navigation_view);

            }
        });
        if (!PrefManager.IsLogin(UserCliantHomeActivity.this)) {
            mygarden.setVisibility(View.GONE);
        } else {
            mygarden.setVisibility(View.VISIBLE);
        }









        RequestQueue requestQueue = Volley.newRequestQueue(UserCliantHomeActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("mycart"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("mycart", response);

                try {

                    BottomNavigationMenuView bottomNavigationMenuView =
                            (BottomNavigationMenuView) navigation.getChildAt(0);
                    View v = bottomNavigationMenuView.getChildAt(3);
                    BottomNavigationItemView itemView = (BottomNavigationItemView) v;

                    View badge = LayoutInflater.from(UserCliantHomeActivity.this)
                            .inflate(R.layout.notification_badge, itemView, true);

                    TextView tv_noti_itm=   badge.findViewById(R.id.notifications);


                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("mycartdata");
                        Log.d("mycartdatasize", String.valueOf(jsonArray.length()));
                        tv_noti_itm.setText(" "+jsonArray.length()+" ");

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sdsadd", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(UserCliantHomeActivity.this));

                return param;
            }
        };

        requestQueue.add(stringRequest);










        if (getIntent().hasExtra("homeact")) {

            Log.d("edsdfs",getIntent().getStringExtra("homeact"));
            if (getIntent().getStringExtra("homeact").equals("basket")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MyCartFragment()).commit();

            }
            if (getIntent().getStringExtra("homeact").equals("home")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new HomeFragment(), "homefragment").commit();

            }
            if (getIntent().getStringExtra("homeact").equals("myorder")) {
                navigation.setSelectedItemId(R.id.nav_purchase);

                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MyOrderFragment()).commit();

            }

            if (getIntent().getStringExtra("homeact").equals("cart")) {
                navigation.setSelectedItemId(R.id.nav_basket);
              //  getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MyCartFragment()).commit();

            }


        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new HomeFragment(), "homefragment").commit();
        }


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
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        iv_menu = (ImageView) findViewById(R.id.iv_menu);

        toggle = new ActionBarDrawerToggle(UserCliantHomeActivity.this, drawer,
                // Navigation menu toggle icon
                R.string.navigation_drawer_open, // Navigation drawer open description
                R.string.navigation_drawer_close // Navigation drawer close description
        );
        toggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.setScrimColor(Color.parseColor("#33000000"));
        tv_user_name.setText(PrefManager.getFirstName(UserCliantHomeActivity.this) + " " + PrefManager.getLastName(UserCliantHomeActivity.this));
        tv_email.setText(PrefManager.getEmailId(UserCliantHomeActivity.this));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;


            switch (item.getItemId()) {
                case R.id.nav_list:
                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new HomeFragment(),"homefragment").commit();

                    //   toolbar.setTitle("Shop");
                    return true;
                case R.id.nav_map:
                    //  toolbar.setTitle("My Gifts");
                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MapViewBuyFragment(),"homefragmap").commit();
                    return true;
                case R.id.nav_purchase:

                    if (PrefManager.IsLogin(UserCliantHomeActivity.this)) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MyOrderFragment()).commit();
                    } else {

                        Intent intent = new Intent(UserCliantHomeActivity.this, LoginActivity.class);
                        intent.putExtra("data", "myorder");
                        startActivity(intent);

                    }
                    return true;
                case R.id.nav_basket:


                    if (PrefManager.IsLogin(UserCliantHomeActivity.this)) {

                        getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MyCartFragment()).commit();

                    } else {

                        Intent intent = new Intent(UserCliantHomeActivity.this, LoginActivity.class);
                        intent.putExtra("data", "basket");
                        startActivity(intent);

                    }
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

                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new HomeFragment(), "homefragment").commit();


            } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homefragment");

                if (homeFragment != null) {
                    homeFragment.getAllProduc();
                }

            }
        }

            case 3: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MapViewBuyFragment(), "homefragmap").commit();

                } else {
                    MapViewBuyFragment mapproduct = (MapViewBuyFragment) getSupportFragmentManager().findFragmentByTag("homefragmap");

                    if (mapproduct != null) {
                        mapproduct.getAllProduc();
                    }
                }
                return;
            }

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 5) {
            if (resultCode == Activity.RESULT_OK) {
                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new HomeFragment(), "homefragment").commit();
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homefragment");

                if (homeFragment != null) {
                    homeFragment.getAllProduc();
                }
            }
        }


        if (requestCode == 6) {
            if (resultCode == Activity.RESULT_OK) {
                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MapViewBuyFragment(), "homefragmap").commit();
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                MapViewBuyFragment homeFragmentmap = (MapViewBuyFragment) getSupportFragmentManager().findFragmentByTag("homefragmap");

                if (homeFragmentmap != null) {
                    homeFragmentmap.getAllProduc();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }
}


