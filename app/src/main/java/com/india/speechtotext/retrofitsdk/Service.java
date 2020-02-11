package com.india.speechtotext.retrofitsdk;

import com.india.speechtotext.retrofitsdk.response.DictionaryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("dictionary-v2.json")
    Call<DictionaryResponse> getDictionaryResponse();

}
