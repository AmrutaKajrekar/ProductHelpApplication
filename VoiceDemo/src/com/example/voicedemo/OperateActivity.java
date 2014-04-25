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


public class OperateActivity extends Activity implements OnInitListener {

	private static final int reqCode =21;
	private static final String tag = "IKEAProductHelp";

	private TextToSpeech textToSpeech;
	private Button read;
	private Button stop;
	private static final String operationSteps = "Step 1.	Prepare the dishes and arrange them properly. Scrape off any excess food from the dishes before placing them in the. If the food has hardened, you may need to let the dish soak first. Check to make sure the dishes are all dishwasher safe. Place glassware in glass rack. Put silverware in the silverware rack. Place pots, pans, and bake ware around the other dishes, but do not block the water sprayer. " +
			"Step 2.	Add detergent. Place dish detergent designed for dishwasher use in the soap dispenser. Add rinse agent to prevent water spots if your dishwasher has that functionality. " +
			"Step 3.	Select the mode and dishwasher cycle. Close door. Choose wash cycle for your dishes. If you are washing mostly pots and pans, a heavy cycle will work best." +
			"Step 4.	Start the dishwasher.";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_operate);
		
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.operatelayout);
		rl.setBackgroundColor(Color.rgb(137, 207, 240)); // ultra blue
		
		textToSpeech = new TextToSpeech(this, this);
		read = (Button) findViewById(R.id.readOperate);

		read.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				readText();
			}
		});

		stop = (Button) findViewById(R.id.stopOperate);
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

		textToSpeech.speak(operationSteps, TextToSpeech.QUEUE_FLUSH, null);
	}

    /**
     * Handles the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == reqCode && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            TextView t = (TextView) findViewById(R.id.textView1);
        	t.setText("You said: " + matches.get(0));
            System.out.println(matches);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    
}