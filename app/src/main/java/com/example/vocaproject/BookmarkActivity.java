package com.example.vocaproject;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        Toolbar myToolbar;
        myToolbar=findViewById(R.id.toolbar3);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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