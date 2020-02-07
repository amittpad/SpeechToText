package com.india.speechtotext;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.india.speechtotext.retrofitsdk.APIClient;
import com.india.speechtotext.retrofitsdk.Service;
import com.india.speechtotext.retrofitsdk.response.ExampleResponse;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.RECORD_AUDIO;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private TextView tvSpeechInput;
    private LottieAnimationView speakerBtn;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializationView();
        if (!checkPermission()) {
            requestPermission();

        } else {
            Snackbar.make(linearLayout, "Permission already granted.", Snackbar.LENGTH_LONG).show();
        }
        speechInputAction();
        serviceCall();
    }

    private void serviceCall() {
        Service service = new APIClient.Builder().build(getApplicationContext()).getService();
        service.getExampleResponse().enqueue(new Callback<ExampleResponse>() {
            @Override
            public void onResponse(Call<ExampleResponse> call, Response<ExampleResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResult().equalsIgnoreCase("success")) {
                        String mResult = response.body().getName();
                        Log.d("name", mResult);
                        Toast.makeText(MainActivity.this, mResult, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ExampleResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Someting went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void speechInputAction() {
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something");
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
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
                ArrayList<String> arrayList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                tvSpeechInput.setText(arrayList.get(0));
                Log.e("TAG", "onResults--->" + arrayList.get(0));

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        speakerBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        Log.e("TAG ", "onTouch ACTION_UP");
                        tvSpeechInput.setText("You will see input here");
                        mSpeechRecognizer.stopListening();
                        speakerBtn.cancelAnimation();
                        break;

                    case MotionEvent.ACTION_DOWN:
                        Log.e("TAG ", "onTouch ACTION_DOWN");
                        tvSpeechInput.setText("");
                        tvSpeechInput.setHint("Listening...");
                        speakerBtn.playAnimation();
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        break;
                }
                return false;
            }
        });
    }

    private void initializationView() {
        linearLayout = findViewById(R.id.linear_parent);
        tvSpeechInput = findViewById(R.id.speech_text_id);
        speakerBtn = findViewById(R.id.speech_image_id);
        tvSpeechInput.setText("You will see input here");
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, RECORD_AUDIO)) {

        }
        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean audioRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (audioRecordAccepted)
                        Snackbar.make(linearLayout, "Permission Granted, Now you can access record audio.", Snackbar.LENGTH_LONG).show();
                    else {
                        Snackbar.make(linearLayout, "Permission Denied, You cannot access record audio.", Snackbar.LENGTH_LONG).show();

                        boolean showRationale = shouldShowRequestPermissionRationale(RECORD_AUDIO);
                        if (!showRationale) {
                            openSettingsDialog();
                        }
                    }
                }

                break;
        }
    }

    private void openSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Required Permissions");
        builder.setMessage("This app require permission to use audio record feature. Grant them in app settings.");
        builder.setPositiveButton("Take Me To SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
