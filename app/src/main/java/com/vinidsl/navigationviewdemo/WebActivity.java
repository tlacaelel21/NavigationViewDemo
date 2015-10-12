package com.vinidsl.navigationviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by tlacaelel21 on 10/10/15.
 */
public class WebActivity extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.webcontent);

		/*webView = (WebView) findViewById(R.id.webView);

		webView.getSettings().setJavaScriptEnabled(true);

		webView.loadUrl("http://desarrollo.smartthinking.com.mx:8080/Cptm/media/Avisos/1_20150922143551.pdf");

		String customHtml = "<html><body><h2>Greetings from JavaCodeGeeks</h2></body></html>";*/
        //webView.loadData(customHtml, "text/html", "UTF-8");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String urlOpen=extras.getString("urlOpen");
        String urlTipo=extras.getString("urlTipo");
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //progressIndicator.setVisibility(View.GONE);
            }
        });
        //webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+urlOpen);
        if(urlTipo.equals("1")){
            webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+urlOpen);
        }
        if(urlTipo.equals("2")){
            webView.loadUrl(urlOpen);
        }

    }

}
