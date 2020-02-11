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
import com.india.speechtotext.retrofitsdk.pojo.Dictionary;
import com.india.speechtotext.retrofitsdk.response.DictionaryResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DictionaryListAdapter dictionaryListAdapter;
    private String foundWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializationView();

        loadDictionaryFromServer();
    }

    /** call the dictionary API */
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
                        toSetAdapterInRecyclerView();
                    }
                }
            }

            @Override
            public void onFailure(Call<DictionaryResponse> call, Throwable t) {
                findViewById(R.id.button).setVisibility(View.GONE);
                ProgressDialog.getInstance().dismiss();
                Toast.makeText(MainActivity.this, "Could't connect internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /** set adapter in the recyclerView to show dictionary list */
    private void toSetAdapterInRecyclerView() {
        @SuppressLint("WrongConstant") LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        /** frequency(list) to be sorted */
        Collections.sort(ResponsesSingleton.getInstance().getDictionaryResponse().getDictionary(), new Comparator<Dictionary>() {
            @Override
            public int compare(Dictionary lhs, Dictionary rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.getFrequency() > rhs.getFrequency() ? -1 : (lhs.getFrequency() < rhs.getFrequency()) ? 1 : 0;
            }
        });

        dictionaryListAdapter = new DictionaryListAdapter(MainActivity.this, ResponsesSingleton.getInstance().getDictionaryResponse().getDictionary(), foundWord);
        recyclerView.setAdapter(dictionaryListAdapter);
    }

    /** initialize the Ids */
    private void initializationView() {
        recyclerView = findViewById(R.id.recyclerView);

        /** Get matched word from dictionary API using Intent */
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            foundWord = bundle.getString("key_found_word");
        }
    }

    /** Button Speak onClickAction*/
    public void btnSpeak(View view) {
        Intent intent = new Intent(MainActivity.this, UserSpeakActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("dictionary", (ArrayList<? extends Parcelable>) ResponsesSingleton.getInstance().getDictionaryResponse().getDictionary());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
