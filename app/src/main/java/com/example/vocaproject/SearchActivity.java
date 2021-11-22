package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
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

        ListView wordList = (ListView)findViewById(R.id.dailyWord_listview);
        EditText searchWord = findViewById(R.id.search_word);
        ArrayList<Word> words = new ArrayList<>();
        WordAdapter adapter = new WordAdapter(this, R.layout.custom_word_item, words);
        wordList.setAdapter(adapter);

        searchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search((String) charSequence);
                for(int j=0; j<mSearchWord.size(); j++) words.add(mSearchWord.get(j));
                // words 정렬하기

                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                words.clear();
            }
        });
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