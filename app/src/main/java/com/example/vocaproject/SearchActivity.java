
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

    public class SearchActivity extends AppCompatActivity implements OnWordItemClick{

        private AppDatabase mDb;
        private WordDao mWordDao;
        private WordBookDao mWordBookDao;

        private List<Word> mAllWord;
        private List<Word> mSearchWord = new ArrayList<>();
    WordAdapter adapter;
    ListView wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mDb = AppDatabase.getInstance(this);
        mWordDao = mDb.wordDao();
        mWordBookDao = mDb.wordBookDao();

        mAllWord = mWordDao.getData();
        Collections.sort(mAllWord);

        wordList = (ListView)findViewById(R.id.search_listview);
        adapter = new WordAdapter(this, R.layout.custom_word_item, mSearchWord,this);

        EditText searchWord = findViewById(R.id.search_word);
        searchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s != null) {
                    search(s.toString());
                    if(mSearchWord != null) wordList.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //searchText 포함된 단어 -> mSearchWord 삽입
    private void search(String searchText){
        if(mSearchWord!= null) mSearchWord.clear();
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
        @Override
        public void onWordItemClick(Word word){
            mWordDao.setUpdateWord(word);
        }
}