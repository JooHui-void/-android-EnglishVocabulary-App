package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainTestActivity extends AppCompatActivity{

    private AppDatabase mDb;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        mDb = AppDatabase.getInstance(this);
        mWordDao = mDb.wordDao();
        mWordBookDao = mDb.wordBookDao();
    }

/*  이거말고 다른 방법 있으면 찾기..
    @OnClick({})
    public void onAlphabetBtnClick(View v){
        int day = 15;

        switch(v.getId()){
            case 버튼1:
                day--;
            case 버튼2:
                day--;
            case 버튼3:
                day--;
            case 버튼4:
                day--;
            case 버튼5:
                day--;
            case 버튼6:
                day--;
            case 버튼7:
                day--;
            case 버튼8:
                day--;
            case 버튼9:
                day--;
            case 버튼10:
                day--;
            case 버튼11:
                day--;
            case 버튼12:
                day--;
            case 버튼13:
                day--;
            case 버튼14:
                day--;
            case 버튼15:
                Intent intent = new Intent(this, AlphabetTest.class);
                intent.putExtra("VocaDay", day);
                startActivity(intent);
        }
    }
*/
}