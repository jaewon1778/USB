package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderWord extends RecyclerView.ViewHolder {
    public TextView txtIndex;
    public TextView txtWord;
    public ImageButton btnCancel;
    private RecycleAdapterWord.OnItemClickListener mListener = null;


    public void setOnItemClickListener(RecycleAdapterWord.OnItemClickListener listener) {
        this.mListener = listener;
    }
    public ViewHolderWord(Context context, View itemView) {
        super(itemView);

        txtIndex = itemView.findViewById(R.id.txt_listItemIndex);
        txtWord = itemView.findViewById(R.id.txt_listItemWord);
        btnCancel = itemView.findViewById(R.id.btn_listItemCancel);
        btnCancel.setColorFilter(Color.parseColor("#FFFF0000"));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "삭제 : " +txtWord.getText(), Toast.LENGTH_SHORT).show();
                int position = getAdapterPosition ();
                if (position!=RecyclerView.NO_POSITION){
                    if (mListener!=null){
                        mListener.onDeleteClick (view,position);
                    }
                }
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION){
                    String keyStr = (String) txtWord.getText();
                    Intent intent = new Intent(context, Output.class);
                    intent.putExtra("keyStr", keyStr);
                    context.startActivity(intent);
                }
            }
        });

    }
}
