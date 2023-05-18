package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleAdapterWord extends RecyclerView.Adapter<ViewHolderWord> {

    private ArrayList<ListItemWord> w_rcyItemWord;

    public RecycleAdapterWord(){
        w_rcyItemWord = new ArrayList<ListItemWord>();
    }


    @NonNull
    @Override
    public ViewHolderWord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_word, parent, false);

        return new ViewHolderWord(context, view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderWord holder, int position) {
        int idx = w_rcyItemWord.get(position).getIndexOfWord();
        String word = w_rcyItemWord.get(position).getWordText();

        holder.txtIndex.setText(""+idx);
        holder.txtWord.setText(word);
    }

    @Override
    public int getItemCount() {
        return w_rcyItemWord.size();
    }

    public void setRecycleItemWord(int newIndex, String newText){
        w_rcyItemWord.add(new ListItemWord(newIndex,newText));
    }

}

