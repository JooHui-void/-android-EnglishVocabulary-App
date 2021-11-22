package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.Collections;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private AppDatabase mDb;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;

    private List<Word> mAllWord;
    private List<Word> mSearchWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mDb = AppDatabase.getInstance(this);
        mWordDao = mDb.wordDao();
        mWordBookDao = mDb.wordBookDao();

        mAllWord = mWordDao.getData();
        Collections.sort(mAllWord);
    }

    //searchText 포함된 단어 -> mSearchWord 삽입
    private void search(String searchText){
        mSearchWord.clear();

        if(searchText.length() != 0){
            for(Word word: mAllWord)
            {
                if(word.getWordEng().contains(searchText))
                {
                    mSearchWord.add(word);
                }
            }
        }
    }
}