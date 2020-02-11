package com.india.speechtotext.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.card.MaterialCardView;
import com.india.speechtotext.R;
import com.india.speechtotext.retrofitsdk.pojo.Dictionary;

import java.util.List;

public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.ViewHolder> {
    private Activity activity;
    private List<Dictionary> dictionaryList;
    int mCount = 0;
    private String foundWord;


    public DictionaryListAdapter(Activity activity, List<Dictionary> dictionaryList, String foundWord) {
        this.activity = activity;
        this.dictionaryList = dictionaryList;
        this.foundWord = foundWord;
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

        if(dictionaryList.get(position).getWord().toLowerCase().matches(foundWord.toLowerCase().replace(".", ""))){
            Log.e("TAG", "--->" + foundWord.toLowerCase().replace(".", ""));
            mCount = dictionaryList.get(position).getFrequency() + 1;
            Log.e("TAG", "--->" + mCount);
            holder.tvWordCount.setText(String.valueOf(mCount));
            holder.tvWordCount.setBackground(activity.getResources().getDrawable(R.drawable.textview_ronunded_red));
           // holder.linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorLightRed));
            holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.colorLightRed));
        }
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
        LinearLayout linearLayout;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tv_word);
            tvWordCount = itemView.findViewById(R.id.tv_word_count);
            cardView = itemView.findViewById(R.id.card_view);
            //linearLayout = (LinearLayout) itemView;
        }
    }
}
