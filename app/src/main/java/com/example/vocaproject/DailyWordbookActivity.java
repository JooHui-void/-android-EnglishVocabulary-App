package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DailyWordbookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_wordbook);

        ListView wordList = (ListView)findViewById(R.id.dailyWord_listview);

        AppDatabase db = AppDatabase.getInstance(this);
        WordDao WDao = db.wordDao();
        // 인텐트로 전달 받기
        int date = 0;

        List<Word> tmp_words = WDao.getDailyData(date);
        ArrayList<Word> words = new ArrayList<>();
        for(int i=0; i<tmp_words.size(); i++) words.add(tmp_words.get(i));
        // words 정렬하기

        WordAdapter adapter = new WordAdapter(this, R.layout.custom_word_item, words);
        wordList.setAdapter(adapter);
    }
}