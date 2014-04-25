package com.example.voicedemo;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InstallActivity extends Activity implements
		TextToSpeech.OnInitListener {

	private static final int reqCode = 21;
	private static final String tag = "IKEAProductHelp";

	private TextToSpeech textToSpeech;
	private Button read;
	private Button stop;
	private static final String installationSteps = "Step 1.	Prepare the kitchen space for the dishwasher. Most standard built-in dishwashers need at least a 24-inch (60 cm) wide opening. If necessary, you can adjust your cupboard to the height of the dishwasher by removing the cupboard top, or adding an overhang to cover a gap between the top of the dishwasher and the countertop. If you are installing a dishwasher for the first time in a space, drill holes through the cabinet for the dishwasher's electric wires, water inlets and drain tube. "
			+ "Step 2. Connect the water supply under the sink and to the line of dishwasher Turn the water supply off and disconnect the sink's hot water supply inlet. Connect a dual outlet valve to the hot water inlet under the sink. Reattach the sink hot water outlet to one side, and attach the dishwasher water supply line to the other outlet. This step will vary depending on the type of dishwasher you are installing. Some newer dishwasher models require that your water and electrical wiring connect to a panel under the front of the unit. However, many models have water, electric and drain connections in the back of the dishwasher. "
			+ "Step 3. Connect the electrical wires to the dishwashing unit. Turn the power off at the main breaker before starting. Run the electrical wires into the back of the dishwasher's electrical box. Attach the black (hot) wire to the black wires coming out of the dishwasher using an electrical wire nut. "
			+ "Step 4. Secure the dishwasher to the cabinet. Push the under-the-counter dishwasher into its spot, being careful not to crimp any wiring or water tubes located under the unit. Check to make sure the unit sits level. If it is not, adjust the leveling feet by screwing them in or out. Mount the front of the dishwasher on the unit with the hardware provided with the product. ";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_install);

		RelativeLayout rl = (RelativeLayout) findViewById(R.id.installlayout);
		rl.setBackgroundColor(Color.rgb(137, 207, 240)); // ultra blue

		textToSpeech = new TextToSpeech(this, this);
		read = (Button) findViewById(R.id.readInstall);

		read.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				readText();
			}
		});

		stop = (Button) findViewById(R.id.stopInstall);
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

		textToSpeech.speak(installationSteps, TextToSpeech.QUEUE_FLUSH, null);
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