package com.example.voicedemo;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HelpActivity extends Activity implements OnInitListener {

	private static final int reqCode = 21;
	private static final String tag = "IKEAProductHelp";
	private TextToSpeech textToSpeech;
	private Button read;
	private Button stop;
	private static final String helpSteps = " This is step by step instruction manual for new users. This manual will guide you through actions you need to take to use application.\n "
			+ "\n1.	On first screen, you will notice applications basic user interface. "
			+ "\n2.	You can choose operations you want to perform on your product like Install, Assemble, Repair, and Operate. "
			+ "\n3.	You can enter operation by typing operation’s name in provided area at top of your screen. "
			+ "\n4.	Then press ‘GO’ button provided, your requested operations form will be presented to you. "
			+ "\n5.	When you speak any of the key word listed like install, operate, assemble, repair using speak button, then appropriate operation form will be presented to you. "
			+ "\n6.	Otherwise you can directly click on operation button and information about that operation will be presented to you. "
			+ "\n7.	Once you select one of the operation, then on second user interface screen you get actual information about that operation. "
			+ "\n8.	You can scroll down through each step.  You can also press read button, so that application reads instructions step by step. "
			+ "\n9.	You can stop reading instructions at any point you want to. "
			+ "\n10.	When you are finished with steps provided, you can go back using back button and choose next operation. "
			+ "\n11.	If you are done with using application, then you can exit application by pressing back button again. ";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		RelativeLayout rl = (RelativeLayout) findViewById(R.id.helplayout);
		rl.setBackgroundColor(Color.rgb(137, 207, 240)); // ultra blue

		textToSpeech = new TextToSpeech(this, this);
		read = (Button) findViewById(R.id.read);

		read.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				readText();
			}
		});

		stop = (Button) findViewById(R.id.pause);
		stop.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				textToSpeech.stop();
			}
		});

	}

	@Override
	public void onDestroy() {
		if (textToSpeech != null) {
			textToSpeech.stop();
			textToSpeech.shutdown();
		}

		super.onDestroy();
	}

	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = textToSpeech.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e(tag, "Language is not available.");
			} else {
				read.setEnabled(true);
			}
		} else {
			Log.e(tag, "Could not initialize TextToSpeech.");
		}
	}

	private void readText() {

		textToSpeech.speak(helpSteps, TextToSpeech.QUEUE_FLUSH, null);
	}

	/**
	 * Handles the results from the recognition activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == reqCode && resultCode == RESULT_OK) {
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			TextView t = (TextView) findViewById(R.id.textView1);
			t.setText("You said: " + matches.get(0));
			System.out.println(matches);
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

}