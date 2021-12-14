
package com.example.vocaproject;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        Toolbar myToolbar;
        myToolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.menu_settings:
                    Intent intent = new Intent();
                    ComponentName componentName =new ComponentName(
                            "com.example.vocaproject",
                            "com.example.vocaproject.Setting"
                    );
                    intent.setComponent(componentName);
                    startActivity(intent);
                    return true;
                default:

                    return super.onOptionsItemSelected(item);

            }
        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.toolbar_menu, menu);

            return true;
        }
}