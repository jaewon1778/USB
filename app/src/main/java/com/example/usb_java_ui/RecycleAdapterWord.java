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
    private DBManager dbManager;

    public RecycleAdapterWord(Context context){
        w_rcyItemWord = new ArrayList<ListItemWord>();
        dbManager = new DBManager();
        dbManager.checkDB(context);
    }

    interface OnItemClickListener{
        void onDeleteClick(View v, int position); //뷰와 포지션값
    }
    //리스너 객체 참조 변수
    private OnItemClickListener mListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
    @NonNull
    @Override
    public ViewHolderWord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_word, parent, false);

        ViewHolderWord newVHW = new ViewHolderWord(context, view);
        newVHW.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onDeleteClick(View v, int position) {
//                if (position!=RecyclerView.NO_POSITION){
//                    if (mListener!=null){
//                        mListener.onDeleteClick (view,position);
//                    }
//                }
                dbManager.deleteWord(DBManager.TABLE_WORD, w_rcyItemWord.get(position).getWordText());
                w_rcyItemWord.remove(position);
                modRecycleItemWord(position, w_rcyItemWord.size());
                notifyItemRemoved (position);
                notifyItemRangeChanged(position, w_rcyItemWord.size());
            }
        });

        return newVHW;
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
    public void modRecycleItemWord(int position, int maxPosition){
        for (int i = position; i<maxPosition; i++){
            int newP = w_rcyItemWord.get(i).getIndexOfWord() - 1;
            w_rcyItemWord.get(i).setIndexOfWord(newP);
        }
    }



}

