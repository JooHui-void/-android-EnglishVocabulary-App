package com.example.vocaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DailyWordbookActivity extends AppCompatActivity
        implements OnWordItemClick
{
    int date = 0;
    List<Word> words;
    ListView wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_wordbook);

        wordList = (ListView)findViewById(R.id.dailyWord_listview);


        Toolbar myToolbar=findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        AppDatabase db = AppDatabase.getInstance(this);
        WordDao WDao = db.wordDao();
        WordBookDao WBDao = db.wordBookDao();

        Intent intent = getIntent();
        date = intent.getIntExtra("DAY",0);

        words = WDao.getDailyData(date);

        WordAdapter adapter = new WordAdapter(this, R.layout.custom_word_item, words,this);
        wordList.setAdapter(adapter);

        TextView title = findViewById(R.id.dailyWord_Title);
        title.setText("DAY "+date) ;
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
                intent.putExtra("VocaDay", date);
                Log.d("d", "day : " + date);
                startActivity(intent);
                break;
            case R.id.sort_eng:
                EngSorting engSorting = new EngSorting();
                Collections.sort(words, engSorting);
                setView();
                break;
            case R.id.sort_kor:
                KorSorting korSorting = new KorSorting();
                Collections.sort(words, korSorting);
                setView();
                break;
            case R.id.sort_importance:
                ImpotanceSorting impotanceSorting = new ImpotanceSorting();
                Collections.sort(words, impotanceSorting);
                setView();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.wordlisttoolbar, menu);
        return true;
    }

    private void setView(){
        WordAdapter adapter = new WordAdapter(this, R.layout.custom_word_item, words,this);
        wordList.setAdapter(adapter);
    }
}

class EngSorting implements Comparator<Word> {
    @Override
    public int compare(Word w1, Word w2){
        return w1.getWordEng().compareTo(w2.getWordEng());
    }
}
class KorSorting implements Comparator<Word>{
    @Override
    public int compare(Word w1, Word w2){
        return w1.getWordKor().compareTo(w2.getWordKor());
    }
}
class ImpotanceSorting implements Comparator<Word>{
    @Override
    public int compare(Word w1, Word w2){
        return (w2.getIsChecking() - w1.getIsChecking());
    }
}