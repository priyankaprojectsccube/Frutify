package com.app.fr.fruiteefy.user_client.home;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import androidx.annotation.NonNull;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.user_antigaspi.fragment.MyGardenActivity;
import com.app.fr.fruiteefy.user_client.CliantChatActivity;
import com.app.fr.fruiteefy.user_client.NotificationcliantActivity;
import com.app.fr.fruiteefy.user_client.mywallet.WalletActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_antigaspi.UserAntigaspiHomeActivity;
import com.app.fr.fruiteefy.user_client.Login.LoginView.LoginActivity;
import com.app.fr.fruiteefy.user_client.Signup.RegisterAdditionalFieldActivity;
import com.app.fr.fruiteefy.user_client.map.MapViewGardenFragment;
import com.app.fr.fruiteefy.user_client.profile.ProfileActivity;
import com.app.fr.fruiteefy.user_picorear.UserPicorearHomeActivity;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class GardensActivity extends AppCompatActivity {
    private BottomNavigationView navigation;
    private DrawerLayout drawer;
    private NavigationView navigation_view;
    private ActionBarDrawerToggle toggle;
    private LinearLayout laymygarden,laylogin, laylogout, laypico, layanti, layprof, laymessage, laynoti, laywish;
    private TextView termscondition,mTxtMywallet, tv_gardens, mygarden, tv_antigaspi, tv_picoreur, cliant1, tv_logout, tv_user_name, tv_email, prof, login, tv_contactus, tv_about, tv_recepies, tv_wishlist;
    private ImageView iv_menu, profile_image;
    private FusedLocationProviderClient fusedLocationClient;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;


            switch (item.getItemId()) {
                case R.id.nav_list:
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new GardenFragment(), "gardenfrag").commit();

                    return true;
                case R.id.nav_map:

                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new MapViewGardenFragment(), "gardenfragmap").commit();
                    return true;

            }
            return false;
        }
    };

    public void initDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);

        termscondition=findViewById(R.id.termscondition);

        termscondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GardensActivity.this, Termsandconditionactivity.class));
                drawer.closeDrawer(Gravity.LEFT);
            }
        });


        toggle = new ActionBarDrawerToggle(GardensActivity.this, drawer,
                // Navigation menu toggle icon
                R.string.navigation_drawer_open, // Navigation drawer open description
                R.string.navigation_drawer_close // Navigation drawer close description
        );
        toggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.setScrimColor(Color.parseColor("#33000000"));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardens);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {

                            PrefManager.setCURRENTLAT(String.valueOf(location.getLatitude()), GardensActivity.this);
                            PrefManager.setCURRENTLNG(String.valueOf(location.getLongitude()), GardensActivity.this);


                        }
                    }
                });


        initDrawer();


        navigation = (BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation);
        navigation_view = findViewById(R.id.navigation_view);


        tv_recepies = findViewById(R.id.tv_recepies);
        mygarden = findViewById(R.id.mygarden);
        laylogout = findViewById(R.id.laylogout);
        mTxtMywallet = findViewById(R.id.mywallet);
        laymessage = findViewById(R.id.laymessage);
        tv_antigaspi = findViewById(R.id.tv_antigaspi);
        cliant1 = findViewById(R.id.cliant1);
        laymygarden=findViewById(R.id.laymygarden);
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


        layanti = findViewById(R.id.layanti);
        tv_picoreur = findViewById(R.id.tv_picoreur);
        prof = findViewById(R.id.prof);
        laylogin = findViewById(R.id.laylogin);
        login = findViewById(R.id.login);
        if (PrefManager.IsLogin(GardensActivity.this)) {
            tv_logout.setVisibility(View.VISIBLE);
        }


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            View activeLabel = item.findViewById(R.id.largeLabel);
            if (activeLabel instanceof TextView) {
                activeLabel.setPadding(0, 0, 0, 0);
            }
        }


        onClick();


        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new GardenFragment(), "gardenfrag").commit();


    }


    public void onClick() {

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(navigation_view);

            }
        });


        if (PrefManager.IsLogin(this)) {

            laylogin.setVisibility(View.GONE);
            laylogout.setVisibility(View.VISIBLE);
            laypico.setVisibility(View.VISIBLE);
            layanti.setVisibility(View.VISIBLE);
            layprof.setVisibility(View.VISIBLE);
            laymessage.setVisibility(View.VISIBLE);
            laynoti.setVisibility(View.VISIBLE);
            laywish.setVisibility(View.VISIBLE);
            laymygarden.setVisibility(View.VISIBLE);


        }


        tv_user_name.setText(PrefManager.getFirstName(GardensActivity.this) + " " + PrefManager.getLastName(GardensActivity.this));
        tv_email.setText(PrefManager.getEmailId(GardensActivity.this));

        String img = PrefManager.getProfPic(GardensActivity.this);
        Log.d("sdsads", img);

        if (img != null && !img.equals("") && !img.equals("null")) {
            Picasso.with(this).load(BaseUrl.PROFPICURL.concat(img)).into(profile_image);
        } else {
            Picasso.with(this).load(BaseUrl.LOGO).into(profile_image);
        }
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(GardensActivity.this, ProfileActivity.class);
                startActivity(intent);
                drawer.closeDrawer(Gravity.LEFT);

            }
        });

        laymessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PrefManager.IsLogin(GardensActivity.this)) {
                    Intent intent = new Intent(GardensActivity.this, CliantChatActivity.class);
                    startActivity(intent);

                    drawer.closeDrawer(Gravity.LEFT);

                } else {
                    Intent intent = new Intent(GardensActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);

                }

            }
        });


        laynoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PrefManager.IsLogin(GardensActivity.this)) {
                    Intent intent = new Intent(GardensActivity.this, NotificationcliantActivity.class);
                    startActivity(intent);

                    drawer.closeDrawer(Gravity.LEFT);

                } else {
                    Intent intent = new Intent(GardensActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);

                }

            }
        });

        tv_gardens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GardensActivity.this, GardensActivity.class));
            }
        });


        cliant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GardensActivity.this, UserCliantHomeActivity.class);
                intent.putExtra("homeact", "home");
                startActivity(intent);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        tv_antigaspi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PrefManager.IsLogin(GardensActivity.this)) {
                    if (PrefManager.getIsAnti(GardensActivity.this).equals("1")) {
                        startActivity(new Intent(GardensActivity.this, UserAntigaspiHomeActivity.class));
                        drawer.closeDrawer(Gravity.LEFT);
                    } else if (PrefManager.getIsAnti(GardensActivity.this).equals("0")) {
                        Intent intent = new Intent(GardensActivity.this, RegisterAdditionalFieldActivity.class);
                        intent.putExtra("type", "antigaspi");
                        startActivity(intent);
                        drawer.closeDrawer(Gravity.LEFT);

                    }
                } else {
                    Intent intent = new Intent(GardensActivity.this, LoginActivity.class);
                    intent.putExtra("data", "antigaspi");
                    startActivity(intent);
                    drawer.closeDrawer(Gravity.LEFT);
                }

            }
        });


        tv_picoreur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RequestQueue requestQueue = Volley.newRequestQueue(GardensActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("profile"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("ssdd", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("status").equals("1")) {


                                JSONObject jsonObject1 = jsonObject.getJSONObject("profiledata");
                                if (PrefManager.IsLogin(GardensActivity.this)) {

                                    if (jsonObject1.getString("is_pico").equals("0")) {
                                        Intent intent = new Intent(GardensActivity.this, RegisterAdditionalFieldActivity.class);
                                        intent.putExtra("type", "picorear");
                                        startActivity(intent);
                                        drawer.closeDrawer(Gravity.LEFT);
                                    } else if (jsonObject1.getString("subscribe_status").equals("0")) {
//                                        startActivity(new Intent(GardensActivity.this, SubscriptionActivity.class));
//                                        drawer.closeDrawer(Gravity.LEFT);

                                        startActivity(new Intent(GardensActivity.this, UserPicorearHomeActivity.class));
                                        drawer.closeDrawer(Gravity.LEFT);
                                    } else if (jsonObject1.getString("is_approve").equals("1")) {
//                                        Toast.makeText(GardensActivity.this, getResources().getString(R.string.pleaseckecksubmail), Toast.LENGTH_SHORT).show();
//                                        drawer.closeDrawer(Gravity.LEFT);
                                        startActivity(new Intent(GardensActivity.this, UserPicorearHomeActivity.class));
                                        drawer.closeDrawer(Gravity.LEFT);
                                    } else if (jsonObject1.getString("is_approve").equals("2")) {
                                        startActivity(new Intent(GardensActivity.this, UserPicorearHomeActivity.class));
                                        drawer.closeDrawer(Gravity.LEFT);
                                    }

                                } else {
                                    Intent intent = new Intent(GardensActivity.this, LoginActivity.class);
                                    intent.putExtra("data", "picoact");
                                    startActivity(intent);
                                    drawer.closeDrawer(Gravity.LEFT);
                                }
                            } else {
                                Intent intent = new Intent(GardensActivity.this, LoginActivity.class);
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
                        param.put("user_id", PrefManager.getUserId(GardensActivity.this));

                        return param;
                    }
                };

                requestQueue.add(stringRequest);

            }
        });

        tv_recepies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PrefManager.IsLogin(GardensActivity.this)) {

                    Intent intent = new Intent(GardensActivity.this, RecipeActivity.class);
                    startActivity(intent);

                    drawer.closeDrawer(Gravity.LEFT);

                } else {
                    Intent intent = new Intent(GardensActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);
                    drawer.closeDrawer(Gravity.LEFT);
                }
            }
        });


        mygarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(Gravity.LEFT);
                Intent intent = new Intent(GardensActivity.this, MyGardenActivity.class);
                startActivity(intent);
            }
        });


        mTxtMywallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PrefManager.IsLogin(GardensActivity.this)) {

                    Intent intent = new Intent(GardensActivity.this, WalletActivity.class);
                    startActivity(intent);

                    drawer.closeDrawer(Gravity.LEFT);

                } else {
                    Intent intent = new Intent(GardensActivity.this, LoginActivity.class);
                    intent.putExtra("data", "home");
                    startActivity(intent);

                }


//                Intent intent=new Intent(UserPicorearHomeActivity.this, WalletActivity.class);
//                startActivity(intent);


            }
        });

        tv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GardensActivity.this, AboutActivity.class));
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        tv_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(GardensActivity.this, ContactusActivity.class));
                drawer.closeDrawer(Gravity.LEFT);

            }
        });


        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PrefManager.LogOut(GardensActivity.this);
                PrefManager.setIsLogin(GardensActivity.this, false);
                Intent intent = new Intent(GardensActivity.this, LoginActivity.class);
                intent.putExtra("data", "home");
                startActivity(intent);
                finish();

            }
        });


        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GardensActivity.this, ProfileActivity.class);
                startActivity(intent);
                drawer.closeDrawer(Gravity.LEFT);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GardensActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        tv_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GardensActivity.this, WishlistActivity.class);
                startActivity(intent);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(GardensActivity.this, UserCliantHomeActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5) {
            if (resultCode == Activity.RESULT_OK) {

                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new GardenFragment(), "gardenfrag").commit();


            }

            if (resultCode == Activity.RESULT_CANCELED) {

                GardenFragment gardenfrag = (GardenFragment) getSupportFragmentManager().findFragmentByTag("gardenfrag");
                gardenfrag.getGardenlist();
            }
        }

        if (requestCode == 6) {
            if (resultCode == Activity.RESULT_OK) {

                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new MapViewGardenFragment(), "gardenfragmap").commit();


            }

            if (resultCode == Activity.RESULT_CANCELED) {

                MapViewGardenFragment gardenfrag = (MapViewGardenFragment) getSupportFragmentManager().findFragmentByTag("gardenfragmap");
                gardenfrag.getGardenlist();
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new GardenFragment(), "gardenfrag").commit();

                } else {
                    GardenFragment gardenfrag = (GardenFragment) getSupportFragmentManager().findFragmentByTag("gardenfrag");
                    gardenfrag.getGardenlist();
                }
                return;
            }


            case 3: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new MapViewGardenFragment(), "gardenfragmap").commit();

                } else {
                    MapViewGardenFragment gardenfrag = (MapViewGardenFragment) getSupportFragmentManager().findFragmentByTag("gardenfragmap");
                    gardenfrag.getGardenlist();
                }
                return;
            }
        }
    }


}