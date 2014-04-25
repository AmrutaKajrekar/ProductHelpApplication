package com.example.voicedemo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;

public class VideoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);

		WebView webView = (WebView) findViewById(R.id.video);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginState(PluginState.ON);

		webView.loadUrl("http://www.youtube.com/watch?v=Qa6OsgJqLmc");

	}

}