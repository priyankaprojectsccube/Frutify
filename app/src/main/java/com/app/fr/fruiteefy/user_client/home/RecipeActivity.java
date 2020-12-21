package com.app.fr.fruiteefy.user_client.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.app.fr.fruiteefy.R;

public class RecipeActivity extends AppCompatActivity {


    LinearLayout linlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);


        linlay=findViewById(R.id.linlay);


        getSupportFragmentManager().beginTransaction().replace(R.id.linlay, new ReceipyFragment()).commit();

    }
}
