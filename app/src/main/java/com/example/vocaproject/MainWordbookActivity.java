package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainWordbookActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {
    public static int DAY = 1;

    ListZip[] wordbook = new ListZip[3];
    ArrayList<WordBook> todayData = new ArrayList<>();
    ArrayList<WordBook> doneDatas = new ArrayList<>();
    ArrayList<WordBook> remainDatas = new ArrayList<>();

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

        wordbook[0].listView.setOnItemClickListener(this);
        wordbook[1].listView.setOnItemClickListener(this);
        wordbook[2].listView.setOnItemClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        todayData.clear();
        doneDatas.clear();
        remainDatas.clear();
        setListView();
    }

    public void setListView() {
        AppDatabase db = AppDatabase.getInstance(this);
        WordBookDao WBDao = db.wordBookDao();
        WordbookAdapter adapter;

        // 단어장 목록 불러오기
        List<WordBook> wordbooks = WBDao.getAll();
        for(int i=0;i<wordbooks.size();i++) {
            if (DAY == wordbooks.get(i).getDay()) {
                todayData.add(wordbooks.get(i));
            } else if (wordbooks.get(i).getIncorrectNumber() == 0 && wordbooks.get(i).getViewNumber() > 0) {
                doneDatas.add(wordbooks.get(i));
            } else {
                remainDatas.add(wordbooks.get(i));
            }
        }

        // listview 띄우기
        adapter = new WordbookAdapter
                (this, R.layout.custom_wordbook_item, todayData);
        wordbook[0].listView.setAdapter(adapter);
        adapter = new WordbookAdapter
                (this, R.layout.custom_wordbook_item, doneDatas);
        wordbook[1].listView.setAdapter(adapter);
        adapter = new WordbookAdapter
                (this, R.layout.custom_wordbook_item, remainDatas);
        wordbook[2].listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // 날짜별 단어장 불러오기
        TextView dateText = view.findViewById(R.id.custom_wordbook_item_date);
        int day = Integer.parseInt(dateText.getText().toString());

        Intent intent = new Intent(this, DailyWordbookActivity.class);
        intent.putExtra("DAY",day);

        startActivity(intent);

    }
}
