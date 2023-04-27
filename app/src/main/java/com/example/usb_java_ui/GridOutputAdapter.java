package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class GridOutputAdapter extends BaseAdapter {

    private Context o_context;
    private ArrayList<GridItemBraille> o_array_item;


    public GridOutputAdapter(Context context){
        this.o_context = context;
        this.o_array_item = new ArrayList<GridItemBraille>();

    }


    @Override
    public int getCount() {
        return this.o_array_item.size();
    }

    @Override
    public Object getItem(int position) {
        return this.o_array_item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) o_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.grid_item_braille, parent, false);

        ImageView imageView = convertView.findViewById(R.id.img_braille);
        imageView.setImageResource(o_array_item.get(position).getResId());

        return convertView;
    }

    public void setBItem(int resId) {

        this.o_array_item.add(new GridItemBraille(resId));
    }


}
