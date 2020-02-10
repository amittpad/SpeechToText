package com.india.speechtotext.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.card.MaterialCardView;
import com.india.speechtotext.R;
import com.india.speechtotext.retrofitsdk.pojo.Dictionary;

import java.util.List;

public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.ViewHolder> {
    private Activity activity;
    private List<Dictionary> dictionaryList;


    public DictionaryListAdapter(Activity activity, List<Dictionary> dictionaryList) {
        this.activity = activity;
        this.dictionaryList = dictionaryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvWord.setText(dictionaryList.get(position).getWord());
        holder.tvWordCount.setText(dictionaryList.get(position).getFrequency().toString());
    }

    @Override
    public int getItemCount() {
        if (dictionaryList == null) {
            return 0;
        }
        return dictionaryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWord, tvWordCount;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tv_word);
            tvWordCount = itemView.findViewById(R.id.tv_word_count);
        }
    }
}
