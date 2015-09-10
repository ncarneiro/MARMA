package org.ncarneiro.marma.speech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

/**
 * Created by TiagoDavi on 23/07/2015.
 */

public class SpeechService implements RecognitionListener {

    public SpeechRecognizer sr;
    public Activity act;
    public Intent intent;
    public String VOICE_TAG = "Voice";
    public OnSpeechListener speechListener;

    public SpeechService(Activity activity) {
        this.act = activity;
        setIntent();
        setSR();
    }

    public SpeechService(Activity activity, OnSpeechListener listener) {
        this.act = activity;
        this.speechListener = listener;
        setIntent();
        setSR();
    }

    public void setSpeechListener(OnSpeechListener listener) {
        speechListener = listener;
    }

    private void setIntent(){
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,999999999);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, act.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
    }

    private void setSR() {
        sr = SpeechRecognizer.createSpeechRecognizer(act);
        sr.setRecognitionListener(this);
    }

    public void start() {
        sr.startListening(intent);
    }

    private boolean isReservedWord(String result) {

        String[] stopListening = {"parar","pausar","pausa","parar reconhecimento",
                "para reconhecimento", "para"};
        if ( checkArray(stopListening,result) ) {
            stop();
            return true;
        }
        return false;
    }

    private boolean checkArray(String[] array, String string) {
        for (String str : array) {
            if (string.equals(str))
                return true;
        }
        return false;
    }

    public void stop() {
        speechListener.onStop();
        sr.cancel();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        speechListener.onStart();
        //Log.i(VOICE_TAG, "Ready fo Speech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(VOICE_TAG, "Beggining of Speech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        //    Log.i(VOICE_TAG, "onRmsChanged");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        //
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(VOICE_TAG, "End of Speech");
    }

    @Override
    public void onError(int error) {
        speechListener.onError(error);
        Log.e(VOICE_TAG, "Código do Erro: " + error);
        //sr.stopListening();
        sr.cancel();
        sr.destroy();
        setSR();
        start();
    }

    @Override
    public void onResults(Bundle results) {
        String result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0).toString().toLowerCase();
        //Log.i(VOICE_TAG, "Resultado: " + result);

        if (isReservedWord(result))
            return;

        speechListener.onResult(result);

        sr.cancel();
        sr.startListening(intent);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        //Log.d(VOICE_TAG, "onPartialResults");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        //Log.d(VOICE_TAG, "onEvent " + eventType);
    }
}
