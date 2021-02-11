package com.chaitanya.quicksoft.glutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaitanya.quicksoft.glutton.databinding.ActivitySupportBinding;

public class Support extends AppCompatActivity {

    ActivitySupportBinding activitySupportBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySupportBinding = DataBindingUtil.setContentView(this,R.layout.activity_support);

        activitySupportBinding.cancelAction.setOnClickListener(view -> {

            showWebDialog(Glutton_Constants.CANCELLATION_POLICY_OR_REFUND_LINK);
        });
        activitySupportBinding.privacyAction.setOnClickListener(view -> {

            showWebDialog(Glutton_Constants.PRIVACY_PLOCY_LINK);
        });
        activitySupportBinding.termsAction.setOnClickListener(view -> {

            showWebDialog(Glutton_Constants.TERMS_OF_USE);
        });
    }

    public void showWebDialog(String url){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            activitySupportBinding.redirect.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            activitySupportBinding.redirect.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        WebSettings settings = activitySupportBinding.redirect.getSettings();
        settings.setTextSize(WebSettings.TextSize.SMALLEST);
        settings.setDomStorageEnabled(true);

        final ProgressDialog webview_progressDialog = ProgressDialog.show(Support.this, "", "Loading Please wait...", true);
        webview_progressDialog.setCancelable(true);

        activitySupportBinding.redirect.getSettings().setJavaScriptEnabled(true);
        activitySupportBinding.redirect.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);// enable javascript
        activitySupportBinding.redirect.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webview_progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webview_progressDialog.dismiss();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (URLUtil.isNetworkUrl(url)) {
                    return false;
                }
                return true;
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });
        activitySupportBinding.redirect.loadUrl(url);
    }

}
