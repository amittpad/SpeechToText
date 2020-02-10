package com.india.speechtotext.retrofitsdk.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.india.speechtotext.retrofitsdk.pojo.Dictionary;

import java.util.List;

public class DictionaryResponse {
    @SerializedName("dictionary")
    @Expose
    private List<Dictionary> dictionary = null;

    public List<Dictionary> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<Dictionary> dictionary) {
        this.dictionary = dictionary;
    }

}
