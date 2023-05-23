package com.example.usb_java_ui;

public class ListItemWord {
    private int indexOfWord;
    private String wordText;

    public ListItemWord(int newIndex, String newText){
        this.indexOfWord = newIndex;
        this.wordText = newText;
    }

    public int getIndexOfWord() {
        return indexOfWord;
    }

    public String getWordText() {
        return wordText;
    }

    public void setIndexOfWord(int indexOfWord) {
        this.indexOfWord = indexOfWord;
    }
}
