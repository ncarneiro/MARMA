package org.ncarneiro.marma;

import android.os.Bundle;
import android.widget.Toast;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import org.ncarneiro.marma.camera.CameraForCardboard;
import org.ncarneiro.marma.speech.OnSpeechListener;
import org.ncarneiro.marma.speech.SpeechAction;
import org.ncarneiro.marma.speech.SpeechService;

import javax.microedition.khronos.egl.EGLConfig;

public class MainActivity extends CardboardActivity implements CardboardView.StereoRenderer {

    CameraForCardboard cfc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardboardView cardboardView = (CardboardView)findViewById(R.id.cardboard_view);
        cardboardView.setRenderer(this);
        setCardboardView(cardboardView);
        //
        this.cfc = new CameraForCardboard(this, cardboardView);
    }

    private void setupSpeech() {
        final SpeechAction sa = new SpeechAction(this);
        SpeechService speech = new SpeechService(this, new OnSpeechListener() {
            @Override
            public void onStart() {}
            @Override
            public void onError(int error) {}
            @Override
            public void onResult(String action) {
                sa.changeText(action);
                Toast.makeText(MainActivity.this,action,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStop() {}
        });
        speech.start();
    }

    @Override
    public void onNewFrame(HeadTransform headTransform) {
        cfc.newFrame(headTransform);
    }

    @Override
    public void onDrawEye(Eye eye) {
        cfc.drawEye(eye);
    }

    @Override
    public void onFinishFrame(Viewport viewport) {}

    @Override
    public void onSurfaceChanged(int i, int i1) {}

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {
        cfc.surfaceCreated(eglConfig);
    }

    @Override
    public void onRendererShutdown() {}

}