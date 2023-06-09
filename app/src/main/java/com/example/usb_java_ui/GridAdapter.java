package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private int keyType;
    private Context m_context;
    private ArrayList<GridItem> m_array_item;

    public GridAdapter(Context context){
        this.keyType = 0;
        this.m_context = context;
        this.m_array_item = new ArrayList<GridItem>();

    }

    @Override
    public int getCount() {
        return this.m_array_item.size();
    }

    @Override
    public Object getItem(int position) {
        return this.m_array_item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.grd_item_con, parent, false);

        Button button = convertView.findViewById(R.id.btn_item_con);
        button.setText(m_array_item.get(position).getItemString());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = getItemString(position);
                Intent intent = new Intent(m_context, Output.class);
                intent.putExtra("keyStr", str);
                intent.putExtra("keyType", keyType);
                m_context.startActivity(intent);
            }
        });
        button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                notify();
            }
        });

        return convertView;
    }

    public void setItem(String strItem) {
        this.m_array_item.add(new GridItem(strItem));
    }

    public String getItemString(int position) {
        return this.m_array_item.get(position).getItemString();
    }

    public ArrayList<GridItem> getM_array_item(){
        return this.m_array_item;
    }
    public ArrayList<View> getItemView(View convertView, ViewGroup parent){
        ArrayList<View> itemView = new ArrayList<>();
        for (int i = 0; i< this.m_array_item.size(); i++){
            itemView.add(getView(i,convertView,parent));
        }
        return itemView;
    }
    public View[] getItemViewArr(View convertView, ViewGroup parent){
        View[] itemView = new View[this.m_array_item.size()];
        for (int i = 0; i< this.m_array_item.size(); i++){
            itemView[i] = getView(i,convertView,parent);
        }
        return itemView;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }
}
