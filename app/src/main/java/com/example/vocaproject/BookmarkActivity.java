package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {

    ListZip[] words = new ListZip[2];
    ArrayList<Word> heartDatas = new ArrayList<>();
    ArrayList<Word> incorrectDatas = new ArrayList<>();

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
        FrameLayout bookmark;

        // 단어장 목록 불러오기
        List<Word> checkingWord = WDao.getCheckingWord();
        for (int i = 0; i < checkingWord.size(); i++) {
            heartDatas.add(checkingWord.get(i));
        }
        // **틀린 단어 전체 리스트 가져오는 함수 필요
//        List<Word> incorrectWord = WDao.getNotCorrectWord();
//        for (int i = 0; i < incorrectWord.size(); i++) {
//            incorrectDatas.add(incorrectWord.get(i));
//        }

        // listview 띄우기
        adapter = new WordAdapter
                (this, R.layout.custom_word_item, heartDatas);
        words[0].listView.setAdapter(adapter);
//        adapter = new WordAdapter
//                (this, R.layout.custom_word_item, incorrectDatas);
//        words[1].listView.setAdapter(adapter);
    }
}