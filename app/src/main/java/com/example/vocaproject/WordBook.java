package com.example.vocaproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wordbook")
public class WordBook {
    @PrimaryKey(autoGenerate = true)
    private int day = 0;
    @ColumnInfo(defaultValue = "0")
    private int checkNumber;
    @ColumnInfo(defaultValue = "0")
    private int correctNumber;
    @ColumnInfo(defaultValue = "0")
    private int incorrectNumber;
    @ColumnInfo(defaultValue = "0")
    private int viewNumber;


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(int checkNumber) {
        this.checkNumber = checkNumber;
    }

    public int getCorrectNumber() {
        return correctNumber;
    }

    public void setCorrectNumber(int correctNumber) {
        this.correctNumber = correctNumber;
    }

    public int getIncorrectNumber() {
        return incorrectNumber;
    }

    public void setIncorrectNumber(int incorrectNumber) {
        this.incorrectNumber = incorrectNumber;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }

}
