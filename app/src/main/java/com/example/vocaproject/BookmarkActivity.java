package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity implements OnWordItemClick{

    ListZip[] words = new ListZip[2];
    List<Word> heartDatas = new ArrayList<>();
    List<Word> incorrectDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        setListView();
    }

    public void setListView() {
        AppDatabase db = AppDatabase.getInstance(this);
        WordDao WDao = db.wordDao();
        WordAdapter adapter;

        heartDatas.clear();
        incorrectDatas.clear();

        List<Word> heartDatas = WDao.getCheckingWord();
        adapter = new WordAdapter(this, R.layout.custom_word_item, heartDatas,this);
        words[0].listView.setAdapter(adapter);

        List<Word> incorrectDatas = WDao.getNotCorrectWordAll();
        adapter = new WordAdapter(this, R.layout.custom_word_item, incorrectDatas, this);
        words[1].listView.setAdapter(adapter);
    }

    @Override
    public void onWordItemClick(Word word){
        AppDatabase db = AppDatabase.getInstance(this);
        WordDao WDao = db.wordDao();
        setListView();
        WDao.setUpdateWord(word);
    }
}