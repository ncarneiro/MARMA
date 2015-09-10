package org.ncarneiro.marma.speech;

import android.app.Activity;
import android.widget.TextView;

import org.ncarneiro.marma.R;

public class SpeechAction {

    private Activity activity;

    public SpeechAction(Activity a) {
        this.activity = a;
    }

    public void changeText(String text) {
        //((TextView)(activity.findViewById(R.id.tv_speechresult))).setText(text);
    }

}