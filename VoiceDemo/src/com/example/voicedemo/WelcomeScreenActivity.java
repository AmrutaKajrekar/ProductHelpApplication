package com.example.voicedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		TextView textview = (TextView) findViewById(R.id.welcome);
		textview.setTextSize(getResources().getDimension(R.dimen.textsize));
		
		Button start = (Button) findViewById(R.id.start);
		start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(WelcomeScreenActivity.this, MainActivity.class);
				startActivity(i);
			}
		});

	}

}