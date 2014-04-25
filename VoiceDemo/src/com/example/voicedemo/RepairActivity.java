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

public class RepairActivity extends Activity implements OnInitListener {

	private static final int reqCode = 21;
	private static final String tag = "IKEAProductHelp";

	private TextToSpeech textToSpeech;
	private Button read;
	private Button stop;
	private static final String repairingSteps = "Step 1.	If your dishes don’t come out clean. Check for following actions Are you overloading your dishwasher? Check the manufacturer's instructions to make sure you're loading it right .Does silverware drop below the lower basket? The spray arm can't spin if obstructed. Are you using the proper dishwasher detergent? Do you routinely scrape food bits off dishes before loading them into the racks? (Rinsing is not necessary.) Are you using a special rinsing agent if your water is hard (highly mineralized)? Hard water can leave a film on the dishes. You can simply Remove the spray arm to clean it. Unscrew the cap, turning it clockwise, and lift off the arm. "
			+ "Step 2.	Water pools on the floor—door leaks Check the gasket for cracks, wear and caked-on crud. If it's damaged, replace it. If it's dirty, clean it with mild detergent and a brush and sponge. A poorly sealing door causes most leaks, and this section concentrates on solutions for this problem. Check for a bad seal when washing the next load of dishes by looking for drips directly under the door. If you find moisture, go to the next step. If you don't find any and a puddle appears, unscrew the lower front panels and look for drips around hoses and other parts. Sometimes you can tighten a hose connection, but most repairs for these types of leaks are difficult. "
			+ "Step 3.	If dishwasher doesn’t start All dishwashers should have a nearby shutoff, either a switch above the sink or a cord you can unplug under the sink. If the switch is off, turn it back on. Or plug the machine back in. If it doesn't start, check the circuit breaker or fuse at the main electrical panel to make sure it hasn't tripped. The dishwasher should have its own circuit. Sometimes the motor sticks and won't turn, especially if you haven't used the dishwasher for a while. One sign of this problem is a humming sound without any other action. To get things going again, remove the lower panels and reach under and spin the motor by turning its fan blades by hand. ";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repair);

		RelativeLayout rl = (RelativeLayout) findViewById(R.id.repairlayout);
		rl.setBackgroundColor(Color.rgb(137, 207, 240)); // ultra blue

		textToSpeech = new TextToSpeech(this, this);
		read = (Button) findViewById(R.id.readRepair);

		read.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				readText();
			}
		});

		stop = (Button) findViewById(R.id.stopRepair);
		stop.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				textToSpeech.stop();
			}
		});

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

		textToSpeech.speak(repairingSteps, TextToSpeech.QUEUE_FLUSH, null);
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

		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

}