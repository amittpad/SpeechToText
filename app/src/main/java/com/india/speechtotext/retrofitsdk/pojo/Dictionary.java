package com.india.speechtotext.retrofitsdk.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dictionary implements Parcelable {
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("frequency")
    @Expose
    private Integer frequency;
    public final static Parcelable.Creator<Dictionary> CREATOR = new Creator<Dictionary>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Dictionary createFromParcel(Parcel in) {
            return new Dictionary(in);
        }

        public Dictionary[] newArray(int size) {
            return (new Dictionary[size]);
        }

    }
            ;

    protected Dictionary(Parcel in) {
        this.word = ((String) in.readValue((String.class.getClassLoader())));
        this.frequency = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Dictionary() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(word);
        dest.writeValue(frequency);
    }

    public int describeContents() {
        return 0;
    }
}
