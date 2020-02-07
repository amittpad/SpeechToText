package com.india.speechtotext.retrofitsdk.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleResponse {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("name")
    @Expose
    private String name;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
