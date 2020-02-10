package com.india.speechtotext;

import com.india.speechtotext.retrofitsdk.response.DictionaryResponse;

public class ResponsesSingleton {
    private static ResponsesSingleton instance;
    private DictionaryResponse dictionaryResponse;

    public ResponsesSingleton() {
    }

    public static ResponsesSingleton getInstance(){
        if(instance == null){
            instance = new ResponsesSingleton();
        }
        return instance;
    }

    public DictionaryResponse getDictionaryResponse() {
        return dictionaryResponse;
    }

    public void setDictionaryResponse(DictionaryResponse dictionaryResponse) {
        this.dictionaryResponse = dictionaryResponse;
    }
}
