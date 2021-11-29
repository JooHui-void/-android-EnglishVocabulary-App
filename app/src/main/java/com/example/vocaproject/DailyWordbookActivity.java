package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DailyWordbookActivity extends AppCompatActivity
        implements OnWordItemClick
//                ,AdapterView.OnItemClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_wordbook);

        ListView wordList = (ListView)findViewById(R.id.dailyWord_listview);

        AppDatabase db = AppDatabase.getInstance(this);
        WordDao WDao = db.wordDao();
        WordBookDao WBDao = db.wordBookDao();

        Intent intent = getIntent();
        int date = intent.getIntExtra("DAY",0);

        List<Word> tmp_words = WDao.getDailyData(date);
        ArrayList<Word> words = new ArrayList<>();
        for(int i=0; i<tmp_words.size(); i++) words.add(tmp_words.get(i));
        // words 정렬하기

        WordAdapter adapter = new WordAdapter(this, R.layout.custom_word_item, words,this);
        wordList.setAdapter(adapter);
    }
    @Override
    public void onWordItemClick(Word word){
        AppDatabase db = AppDatabase.getInstance(this);
        WordDao WDao = db.wordDao();
        WDao.setUpdateWord(word);
    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//        Intent intent = new Intent(this, FlashCardTest.class);
//        intent.putExtra("DAY",day);
//
//        startActivity(intent);
//    }
}