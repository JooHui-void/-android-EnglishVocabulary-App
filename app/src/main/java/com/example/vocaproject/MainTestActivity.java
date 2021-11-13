package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

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
    TextView engWord;

    private AppDatabase mDb;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;

    private List<Word> mTestWord;
    private List<Integer> mWordIndex;
    private int wordIndex = 0;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        mDb = AppDatabase.getInstance(this);
        mWordDao = mDb.wordDao();

        Button allTestBtn = (Button) findViewById(/*채우기*/);
    }

    /*
    public void onClick(View v){
        int day = 15;

        switch(v.getId()){
            case 버튼1:
            case 버튼2:
            case 버튼3:
            case 버튼4:
            case 버튼5:
            case 버튼6:
            case 버튼7:
            case 버튼8:
            case 버튼9:
            case 버튼10:
            case 버튼11:
            case 버튼12:
            case 버튼13:
            case 버튼14:
            case 버튼15:
                startTest(day);
                break;
        }
    }
    */
}