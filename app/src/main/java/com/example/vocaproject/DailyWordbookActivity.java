package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    int date = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_wordbook);

        ListView wordList = (ListView)findViewById(R.id.dailyWord_listview);


        Toolbar myToolbar=findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        AppDatabase db = AppDatabase.getInstance(this);
        WordDao WDao = db.wordDao();
        WordBookDao WBDao = db.wordBookDao();

        Intent intent = getIntent();
        date = intent.getIntExtra("DAY",0);

        List<Word> words = WDao.getDailyData(date);

        WordAdapter adapter = new WordAdapter(this, R.layout.custom_word_item, words,this);
        wordList.setAdapter(adapter);
    }


    @Override
    public void onWordItemClick(Word word){
        AppDatabase db = AppDatabase.getInstance(this);
        WordDao WDao = db.wordDao();
        WDao.setUpdateWord(word);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //여기서부터 작성
        Intent intent;
        switch (item.getItemId()) {
            case R.id.play:
                intent = new Intent(this, FlashCardTest.class);
                intent.putExtra("VocaDay",date);
                startActivity(intent);
            case R.id.menu:


            default:
                return super.onOptionsItemSelected(item);

        }
    }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.wordlisttoolbar, menu);
            return true;
        }
}