package com.example.vocaproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word")
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    @ColumnInfo
    private String wordEng;
    @ColumnInfo
    private String wordKor;
    @ColumnInfo(defaultValue = "0")
    private int isCorrect;
    @ColumnInfo(defaultValue = "0")
    private int isChecking;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordEng() {
        return wordEng;
    }

    public void setWordEng(String wordEng) {
        this.wordEng = wordEng;
    }

    public String getWordKor() {
        return wordKor;
    }

    public void setWordKor(String wordKor) {
        this.wordKor = wordKor;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    public int getIsChecking() {
        return isChecking;
    }

    public void setIsChecking(int isChecking) {
        this.isChecking = isChecking;
    }
}
