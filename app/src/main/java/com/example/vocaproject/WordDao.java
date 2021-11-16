package com.example.vocaproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    @Insert
    void setInsertWord(Word word);

    @Update
    void setUpdateWord(Word word);

    @Delete
    void setDelete(Word word);

    //단어 하나 가져오기
    @Query ("SELECT * FROM word WHERE _id =:id")
    Word getWord(int id);

    //전체 단어 리스트 가져오기
    @Query ("SELECT * FROM word")
    List<Word> getData();

    //전체 단어 개수 가져오기
    @Query("SELECT COUNT (*) FROM word")
    int getAllNumber();

    //부분 단어 리스트 가져오기
    @Query("SELECT * FROM word WHERE _id BETWEEN :minIndex AND :maxIndex")
    List<Word> getDailyData(int minIndex, int maxIndex);

    //틀린 단어 리스트 가져오기
    @Query("SELECT * FROM Word WHERE isCorrect=0 BETWEEN :minIndex AND :maxIndex")
    List<Word> getNotCorrectWord(int minIndex, int maxIndex);

    //틀린 단어 개수 가져오기
    @Query("SELECT COUNT (*) FROM Word WHERE isCorrect=0 BETWEEN :minIndex AND :maxIndex")
    int getIncorrect(int minIndex, int maxIndex);

    //즐겨찾기 단어 리스트 가져오기
    @Query("SELECT * FROM Word WHERE isChecking=1")
    List<Word> getCheckingWord();

    //즐겨찾기 단어 개수 가져오기
    @Query("SELECT COUNT (*) FROM Word WHERE isCorrect=0")
    int getChecking();
}
