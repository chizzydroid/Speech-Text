package com.chizzy.sendmgonspeechtext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    RelativeLayout parentRelativeLayout;
    SpeechRecognizer speechRecognizer;
    Intent speechRecognizerIntent;
    String keeper = "";
        EditText editText;
    Button button;


    public void send (View view){

         Intent shareIntent = new Intent(Intent.ACTION_SEND);
         shareIntent.setType("text/plain");
         shareIntent.putExtra(Intent.EXTRA_SUBJECT,"");
         String message = editText.getText().toString().trim();
         shareIntent.putExtra(Intent.EXTRA_TEXT,message);
         startActivity(Intent.createChooser(shareIntent,""));

         }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        checkVoiceCommandPermission();

        parentRelativeLayout = findViewById(R.id.parentRelativeLayout);
        editText= findViewById(R.id.editText);
        button = findViewById(R.id.button);

        speechRecognizer = speechRecognizer .createSpeechRecognizer(MainActivity2.this);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matchesFound = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);

                if (matchesFound != null){
                    keeper = matchesFound.get(0);
                   editText.setText(keeper);
                }

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        parentRelativeLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case  MotionEvent.ACTION_DOWN:
                        speechRecognizer.startListening(speechRecognizerIntent);
                        keeper = "";
                        break;

                    case  MotionEvent.ACTION_UP:
                        speechRecognizer.stopListening();
                        break;


                }

                return false;
            }
        });

    }
    public void checkVoiceCommandPermission () {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!(ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("Package:" + getPackageName()));
                startActivity(intent);
                finish();

            }

        }
    }
}