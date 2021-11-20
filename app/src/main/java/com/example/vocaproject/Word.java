package com.example.vocaproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word")
public class Word implements Comparable<Word> {
    @PrimaryKey(autoGenerate = true)
    private int _id = 0;
    @ColumnInfo
    private int wordDay;
    @ColumnInfo
    private String wordEng;
    @ColumnInfo
    private String wordKor;
    @ColumnInfo(defaultValue = "0")
    private int isCorrect;
    @ColumnInfo(defaultValue = "0")
    private int isChecking;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public int getWordDay() {
        return wordDay;
    }

    public void setWordDay(int wordDay) {
        this.wordDay = wordDay;
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

    @Override
    public int compareTo(Word word){
        return (this.wordEng.compareTo(word.wordEng));
    }

}
