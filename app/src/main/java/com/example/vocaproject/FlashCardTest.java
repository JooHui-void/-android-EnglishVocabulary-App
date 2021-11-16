package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import butterknife.OnClick;

public class FlashCardTest extends AppCompatActivity {
    private final static int WORDBOOK_DAY_NUMBER = 15;
    private final static int DAILY_VOCA_NUMBER = 15;

    TextView engWord;
    TextView korWord;

    private AppDatabase mDb;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;

    private List<Word> mTestWord;
    private List<WordBook> mTestWordBook;
    private int wordIndex = 0;
    private int day;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_test);

        Intent intent = getIntent();
        day = intent.getIntExtra("VocaDay", 0);

        mDb = AppDatabase.getInstance(this);
        mWordDao = mDb.wordDao();
        mWordBookDao = mDb.wordBookDao();
        startTest();
    }

    // 인덱스, 단어장, 단어 초기화
    private void startTest() {
        wordIndex = 0;

        if (true/*전체 테스트면*/) {
            mTestWordBook = mWordBookDao.getAllWordBook();
            mTestWord = mWordDao.getData();
        } else {
            mTestWordBook = mWordBookDao.getWordBook(day);
            mTestWord = mWordDao.getDailyData(day);
        }
        Collections.shuffle(mTestWord);
    }

    // 테스트의 끝인지 체크
    private boolean isEnd(){
        if(wordIndex == mTestWord.size()-1)
            return true;
        else
            return false;
    }

    // 테스트의 끝이면 결과를 DB에 업데이트 후 종료
    private void endTest() {
        int incorrectNum;

        for (int i = 0; i < mTestWord.size(); i++) {
            mWordDao.setUpdateWord(mTestWord.get(i));
        }

        if (true/*전체 테스트면*/) {
            for(day = 1; day <= WORDBOOK_DAY_NUMBER; day++){
                incorrectNum = mWordDao.getIncorrect(day);
                mTestWordBook.get(day-1).setIncorrectNumber(incorrectNum);
                mTestWordBook.get(day-1).setCorrectNumber(DAILY_VOCA_NUMBER - incorrectNum);
                mWordBookDao.setUpdateWordBook(mTestWordBook.get(day));
            }
        }else {
            incorrectNum = mWordDao.getIncorrect(day);
            mTestWordBook.get(0).setIncorrectNumber(incorrectNum);
            mTestWordBook.get(0).setCorrectNumber(DAILY_VOCA_NUMBER - incorrectNum);
            mWordBookDao.setUpdateWordBook(mTestWordBook.get(0));
        }

        Toast.makeText(this, "짝짝짝, 단어 테스트 종료!", Toast.LENGTH_SHORT);
        endActivity();
    }

    private void endActivity(){
        finish();
    }

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