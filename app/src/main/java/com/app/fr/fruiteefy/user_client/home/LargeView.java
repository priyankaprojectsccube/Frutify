package com.app.fr.fruiteefy.user_client.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.app.fr.fruiteefy.R;
import com.bumptech.glide.Glide;

public class LargeView extends AppCompatActivity {
ImageView imageView;
    String img_url;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_view);


        if (getIntent().hasExtra("largeurl")) {
            img_url = getIntent().getStringExtra("largeurl");
            imageView = findViewById(R.id.imageView);

            Log.d("largeurl", getIntent().getStringExtra("largeurl"));


            Glide.with(LargeView.this)
                    .load(img_url)
                    .thumbnail(0.1f)
                    .into(imageView);
        }

       else  if (getIntent().hasExtra("imgurl")) {
            img_url = getIntent().getStringExtra("imgurl");
            imageView = findViewById(R.id.imageView);

            Log.d("imgurl", getIntent().getStringExtra("imgurl"));


            Glide.with(LargeView.this)
                    .load(img_url)
                    .thumbnail(0.1f)
                    .into(imageView);
        }

    }
}