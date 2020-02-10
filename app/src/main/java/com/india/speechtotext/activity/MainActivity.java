package com.india.speechtotext.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.india.speechtotext.ProgressDialog;
import com.india.speechtotext.R;
import com.india.speechtotext.ResponsesSingleton;
import com.india.speechtotext.adapter.DictionaryListAdapter;
import com.india.speechtotext.retrofitsdk.APIClient;
import com.india.speechtotext.retrofitsdk.Service;
import com.india.speechtotext.retrofitsdk.response.DictionaryResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DictionaryListAdapter dictionaryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializationView();
        loadDictionaryFromServer();
    }

    private void loadDictionaryFromServer() {
        ProgressDialog.getInstance().show(MainActivity.this);
        Service service = new APIClient.Builder().build(getApplicationContext()).getService();
        service.getDictionaryResponse().enqueue(new Callback<DictionaryResponse>() {
            @Override
            public void onResponse(Call<DictionaryResponse> call, Response<DictionaryResponse> response) {
                if (response.isSuccessful()) {
                    ProgressDialog.getInstance().dismiss();
                    ResponsesSingleton.getInstance().setDictionaryResponse(response.body());
                    if (!ResponsesSingleton.getInstance().getDictionaryResponse().getDictionary().isEmpty()) {
                        toSetAdapter();
                    }
                }
            }

            @Override
            public void onFailure(Call<DictionaryResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toSetAdapter() {
        @SuppressLint("WrongConstant") LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        dictionaryListAdapter = new DictionaryListAdapter(MainActivity.this, ResponsesSingleton.getInstance().getDictionaryResponse().getDictionary());
        recyclerView.setAdapter(dictionaryListAdapter);
    }

    private void initializationView() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    public void btnSpeak(View view) {
        Intent intent = new Intent(MainActivity.this, UserSpeakActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("dictionary", (ArrayList<? extends Parcelable>) ResponsesSingleton.getInstance().getDictionaryResponse().getDictionary());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
