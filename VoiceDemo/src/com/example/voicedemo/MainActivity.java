package com.example.voicedemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int reqCode = 21;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button b = (Button) findViewById(R.id.b);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity();
			}
		});
		final EditText text = (EditText) findViewById(R.id.query);

		Button go = (Button) findViewById(R.id.go);
		go.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean isMatched = false;
				isMatched = gotoProductPage(text.getText().toString());
				if (!isMatched) {
					Context context = getApplicationContext();
					CharSequence text = "Sorry, I didn't undertsand what you wrote. Please write what operation you want to perform!";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
				}
			}
		});

		Button install = (Button) findViewById(R.id.install);
		install.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openToast("Opening Installation Page...");
				Intent i = new Intent(MainActivity.this, InstallActivity.class);
				startActivity(i);
			}
		});

		Button repair = (Button) findViewById(R.id.repair);
		repair.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openToast("Opening Repair Product Page...");
				Intent i = new Intent(MainActivity.this, RepairActivity.class);
				startActivity(i);
			}
		});

		Button assemble = (Button) findViewById(R.id.assemble);
		assemble.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openToast("Opening Assemble Product Page...");
				Intent i = new Intent(MainActivity.this, AssembleActivity.class);
				startActivity(i);
			}
		});

		Button opearate = (Button) findViewById(R.id.operate);
		opearate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openToast("Opening Operate Product Page...");
				Intent i = new Intent(MainActivity.this, OperateActivity.class);
				startActivity(i);
			}
		});

		Button help = (Button) findViewById(R.id.help);
		help.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openToast("Opening Help Page...");
				Intent i = new Intent(MainActivity.this, HelpActivity.class);
				startActivity(i);
			}
		});

		Button video = (Button) findViewById(R.id.video);
		video.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openToast("Opening Video...");
				Intent i = new Intent(MainActivity.this, VideoActivity.class);
				startActivity(i);
			}
		});
		
	}

	protected void openToast(String text) {
		Context context = getApplicationContext();
//		CharSequence text = "Sorry, I didn't undertsand what you wrote. Please write what operation you want to perform!";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}

	private void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				"Please speak your query.");
		startActivityForResult(intent, reqCode);
	}

	/**
	 * Handles the results from the recognition activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == reqCode
				&& resultCode == RESULT_OK) {
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

			boolean isMatched = false;
			// if user says install, open install page
			for (String string : matches) {
				isMatched = gotoProductPage(string);
				if (isMatched)
					break;
			}

			if (!isMatched) {
				Context context = getApplicationContext();
				CharSequence text = "Sorry, I didn't undertsand what you said. Please say the operation you want to perform!";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
			}

		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private boolean gotoProductPage(String string) {
		boolean isMatched = false;
		if (string.contains("install")) {
			isMatched = true;
			openToast("Opening Installation Page...");
			Intent i = new Intent(MainActivity.this, InstallActivity.class);
			startActivity(i);
		}

		else if (string.contains("repair")) {
			isMatched = true;
			openToast("Opening Repair Product Page...");
			Intent i = new Intent(MainActivity.this, RepairActivity.class);
			startActivity(i);
		}

		else if (string.contains("assembl")) {
			isMatched = true;
			openToast("Opening Assemble Product Page...");
			Intent i = new Intent(MainActivity.this, AssembleActivity.class);
			startActivity(i);
		}

		else if (string.contains("operat")) {
			isMatched = true;
			openToast("Opening Operate Product Page...");
			Intent i = new Intent(MainActivity.this, OperateActivity.class);
			startActivity(i);
		}

		else if (string.contains("help")) {
			isMatched = true;
			openToast("Opening Help Page...");
			Intent i = new Intent(MainActivity.this, HelpActivity.class);
			startActivity(i);
		}
		else if (string.contains("video")) {
			isMatched = true;
			openToast("Opening Video...");
			Intent i = new Intent(MainActivity.this, VideoActivity.class);
			startActivity(i);
		}

		return isMatched;
	}

}