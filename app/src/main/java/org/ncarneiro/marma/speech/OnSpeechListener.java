package org.ncarneiro.marma.speech;

/**
 * Created by TiagoDavi on 02/09/2015.
 */
public interface OnSpeechListener {
    void onStart();
    void onError(int error);
    void onResult(String result);
    void onStop();
}
