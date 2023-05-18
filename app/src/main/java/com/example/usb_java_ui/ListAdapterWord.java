package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterWord extends BaseAdapter {

    private Context w_context;
    private ArrayList<ListItemWord> w_array_item;

    public ListAdapterWord(Context context){
        this.w_context = context;
        this.w_array_item = new ArrayList<ListItemWord>();
    }


    @Override
    public int getCount() {
        return w_array_item.size();
    }

    @Override
    public ListItemWord getItem(int i) {
        return w_array_item.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) w_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item_word, viewGroup, false);

        TextView textIndex = view.findViewById(R.id.txt_listItemIndex);
        textIndex.setText(""+w_array_item.get(i).getIndexOfWord());
        TextView textWord = view.findViewById(R.id.txt_listItemWord);
        textWord.setText(w_array_item.get(i).getWordText());



        return view;
    }

    public void setWItem(int newIndex, String newText){
        this.w_array_item.add(new ListItemWord(newIndex, newText));
    }
}
