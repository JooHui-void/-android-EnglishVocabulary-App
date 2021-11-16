package com.example.vocaproject;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;

import java.util.List;

@Dao
public interface WordBookDao {
    //단어장 정보 업데이트
    @Update
    void setUpdateWordBook(WordBook wordbook);

    //단어장 모두 가져오기
    @Query("SELECT * FROM wordbook")
    List<WordBook> getAllWordBook();

    //단어장 하나 가져오기
    @Query("SELECT * FROM wordbook WHERE day =:day")
    List<WordBook> getWordBook(int day);

    @Query ("SELECT * FROM wordBook")
    List<WordBook> getAll();


}
