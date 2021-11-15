package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BookmarkActivity extends AppCompatActivity {

    ListZip[] words = new ListZip[2];
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
}