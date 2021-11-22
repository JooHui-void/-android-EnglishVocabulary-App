package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

public class BookmarkActivity extends AppCompatActivity {

    private AppDatabase mDb;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;

    private List<Word> mBookmarkWord;

    ListZip[] words = new ListZip[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        mDb = AppDatabase.getInstance(this);
        mWordDao = mDb.wordDao();
        mWordBookDao = mDb.wordBookDao();

        words[0] = new ListZip(this,
                R.id.word_heart,
                R.id.word_icon_zip_heart,
                R.id.word_icon_expand_heart,
                R.id.word_listview_heart);
        words[1] = new ListZip(this,
                R.id.word_incorrect,
                R.id.word_icon_zip_incorrect,
                R.id.word_icon_expand_incorrect,
                R.id.word_listview_incorrect);

        mBookmarkWord = mWordDao.getCheckingWord();

    }
}