package com.india.speechtotext.retrofitsdk.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.india.speechtotext.retrofitsdk.pojo.Dictionary;

import java.util.List;

public class DictionaryResponse implements Parcelable {
    @SerializedName("dictionary")
    @Expose
    private List<Dictionary> dictionary = null;
    public final static Parcelable.Creator<DictionaryResponse> CREATOR = new Creator<DictionaryResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DictionaryResponse createFromParcel(Parcel in) {
            return new DictionaryResponse(in);
        }

        public DictionaryResponse[] newArray(int size) {
            return (new DictionaryResponse[size]);
        }

    }
            ;

    protected DictionaryResponse(Parcel in) {
        in.readList(this.dictionary, (Dictionary.class.getClassLoader()));
    }

    public DictionaryResponse() {
    }

    public List<Dictionary> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<Dictionary> dictionary) {
        this.dictionary = dictionary;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(dictionary);
    }

    public int describeContents() {
        return 0;
    }

}
