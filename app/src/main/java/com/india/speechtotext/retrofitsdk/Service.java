package com.india.speechtotext.retrofitsdk;

import com.india.speechtotext.retrofitsdk.response.DictionaryResponse;
import com.india.speechtotext.retrofitsdk.response.ExampleResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
//    @FormUrlEncoded
//    @POST("/teachers-registration")
//    Call<RegistrationResponse> DoRegistration(@Field("student_teachers_email_address") String email,
//                                              @Field("student_teachers_password") String Password,
//                                              @Field("grade_level") String Grade);

    @GET("dictionary-v2.json")
    Call<DictionaryResponse> getDictionaryResponse();


}
