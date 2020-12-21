package com.app.fr.fruiteefy.user_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.user_client.home.UserCliantHomeActivity;

public class PaymentActivity extends AppCompatActivity {


    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        webview=findViewById(R.id.webview);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAllowContentAccess(true);
        webview.getSettings().setSupportMultipleWindows(true);
        webview.setScrollbarFadingEnabled(false);



        CustomUtil.ShowDialog(PaymentActivity.this,false);
        webview.loadUrl(getIntent().getStringExtra("webredirect_url"));

        webview.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return true;
            }
            public void onLoadResource (WebView view, String url) {

            }
            public void onPageFinished(WebView view, String url) {

                CustomUtil.DismissDialog(PaymentActivity.this);


                Log.d("dsada",url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                Log.d("ddfdsffs", String.valueOf(error));
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(PaymentActivity.this, UserCliantHomeActivity.class);
        intent.putExtra("homeact", "myorder");
        startActivity(intent);
    }
}
