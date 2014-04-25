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

public class AssembleActivity extends Activity implements OnInitListener {

	private static final int reqCode = 21;
	private static final String tag = "IKEAProductHelp";

	private TextToSpeech textToSpeech;
	private Button read;
	private Button stop;
	private static final String assemblingSteps = "Step 1.	Panel  Installation. The extension pieces are used to match the control panel height  to the horizontal drawer line of the cabinets, and must be installed as shown in on the template sheet. The standard piece is used for drawer heights up to 6” (152mm); the long piece is used for drawer heights greater than 6” (152mm) but 6-7/16” (164mm) or less. "
			+ "Step 2.	Door Installation. Generally models come with additional mounting hardware and a template sheet that will show you how to mount the panel. One side of the template shows how to mount a one piece panel; the other side shows how to mount a two piece panel. Decide which type of assembling  you want before proceeding with the installation. "
			+ "Step 3.	Securing Dishwasher. Drive the mounting screws through the holes in the mounting brackets as shown in figure for Top or Side Mount. After the unit is installed in the enclosure, leveled and secured, lock the two front leg levelers in place by driving the enclosed leg leveler locking screws into each screw boss located in front of the levelers.  Tighten screws until they are flush with the surface of the bosses."
			+ "Step 4.	Installation of the Rubber Drain Hose Adaptor. Obtain the Rubber Drain Hose Adaptor and the two hose clamps from the Dishwasher Installation Kit. On one outside end of the Rubber Drain Hose Adapter is a raised rib. Insert the drain hose into the end without the raised rib. Be sure to fully insert the drain hose. Secure the connection with the Silver Spring Clamp. Use the Gold Screw Clamp to attach the Rubber Drain Hose Adaptor to the house plumbing.";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assemble);

		RelativeLayout rl = (RelativeLayout) findViewById(R.id.assemblelayout);
		rl.setBackgroundColor(Color.rgb(137, 207, 240)); // ultra blue

		textToSpeech = new TextToSpeech(this, this);
		read = (Button) findViewById(R.id.readAssemble);

		read.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				readText();
			}
		});

		stop = (Button) findViewById(R.id.stopAssemble);

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
		textToSpeech.speak(assemblingSteps, TextToSpeech.QUEUE_FLUSH, null);
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
			TextView t = (TextView) findViewById(R.id.textView1);
			t.setText("You said: " + matches.get(0));
			System.out.println(matches);
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

}