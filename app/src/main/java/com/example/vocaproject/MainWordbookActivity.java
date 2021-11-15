package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainWordbookActivity extends AppCompatActivity {
    ListZip[] wordbook = new ListZip[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wordbook);

        wordbook[0] = new ListZip(this,
                R.id.wordbook_today,
                R.id.wordbook_icon_zip_today,
                R.id.wordbook_icon_expand_today,
                R.id.wordbook_listview_today);
        wordbook[1] = new ListZip(this,
                R.id.wordbook_done,
                R.id.wordbook_icon_zip_done,
                R.id.wordbook_icon_expand_done,
                R.id.wordbook_listview_done);
        wordbook[2] = new ListZip(this,
                R.id.wordbook_remain,
                R.id.wordbook_icon_zip_remain,
                R.id.wordbook_icon_expand_remain,
                R.id.wordbook_listview_remain);
    }
}
