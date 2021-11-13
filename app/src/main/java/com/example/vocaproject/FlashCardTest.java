package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import butterknife.OnClick;

public class FlashCardTest extends AppCompatActivity {
    TextView engWord;
    TextView korWord;

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
        setContentView(R.layout.activity_flash_card_test);

        mDb = AppDatabase.getInstance(this);
        mWordDao = mDb.wordDao();

    }

    private void shuffleIndex(int count){
        Random r = new Random();

        for(int i = 0; i<count; i++){
            mWordIndex.set(i, r.nextInt(count));
            for(int j = 0; j<i; j++){
                if(mWordIndex.get(i) == mWordIndex.get(j)){
                    i--;
                }
            }
        }
    }

    protected void startTest(int day){
        int startIndex = day * 15 + 1;
        int endIndex = (day + 1) * 15;
        wordIndex = 0;

        mTestWord = mWordDao.getDailyData(startIndex, endIndex);
        mHandler.post(mSettingNewQuestionRunnable);
    }

    private Runnable mSettingNewQuestionRunnable = new Runnable(){

        @Override
        public void run(){
            shuffleIndex(15);
            engWord.setText(mTestWord.get(mWordIndex.get(wordIndex)).getWordEng());
        }
    };

    @OnClick(/*알아요버튼아이디*/)
    public void onOKButton(){
        mTestWord.get(wordIndex).setIsCorrect(1);
        //넘어가기
    }

    @OnClick(/*몰라요버튼아이디*/)
    public void onNotOKButton(){
        mTestWord.get(wordIndex).setIsCorrect(0);
        //넘어가기
    }
}