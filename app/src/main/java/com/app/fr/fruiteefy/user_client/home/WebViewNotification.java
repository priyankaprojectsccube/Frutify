package com.app.fr.fruiteefy.user_client.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.fr.fruiteefy.R;

public class WebViewNotification extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_notification);

        webview=findViewById(R.id.webview);


       // webview.loadUrl(getIntent().getStringExtra("url"));

        webview.loadUrl("http://fruiteefy.cube9projects.co.in/Mes_Commandes/26");

        webview.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:var footer = document.getElementById(\"footer\"); footer.parentNode.removeChild(footer); var header = document.getElementById(\"header-full\"); header.parentNode.removeChild(header);");

            }
        });

    }







}
