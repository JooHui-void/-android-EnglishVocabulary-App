package com.example.vocaproject;

import static com.example.vocaproject.MainActivity.DAILY_VOCA_NUMBER;
import static com.example.vocaproject.MainActivity.WORDBOOK_DAY_NUMBER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeaningTest extends AppCompatActivity implements View.OnClickListener{
    private final static int ANSWER_NUMBER = 4;

    //@BindViews({/*정답버튼4개아이디*/})
    List<Button> answerBtns=new ArrayList<>();

    private AppDatabase mDb;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;

    private List<Integer> mWordIndex=new ArrayList<>();
    private List<String> mRandomAnswer=new ArrayList<>();
    private List<Word> mTestWord=new ArrayList<>();
    private List<WordBook> mTestWordBook=new ArrayList<>();
    private int wordIndex = 0;
    private int answerNum;
    private int day;

    TextView quiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning_test);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        day = intent.getIntExtra("VocaDay", 0);
        mDb = AppDatabase.getInstance(this);
        mWordDao = mDb.wordDao();
        mWordBookDao = mDb.wordBookDao();
        startTest();

        answerBtns.add(findViewById(R.id.bt1));
        answerBtns.add(findViewById(R.id.bt2));
        answerBtns.add(findViewById(R.id.bt3));
        answerBtns.add(findViewById(R.id.bt4));


        quiz= findViewById(R.id.quiz);
        for(int i=0;i<4;i++){
            answerBtns.get(i).setOnClickListener(this);
        }

        setfrontView();

    }

    private void setfrontView(){
        for(int i=0;i<ANSWER_NUMBER;i++){
            answerBtns.get(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#e0e0e0")));
        }
        quiz.setText(mTestWord.get(wordIndex).getWordEng());
        newRandomAnswer(mTestWord.get(wordIndex).getWordKor());

    }

    // 랜덤 단어 버튼 생성을 위한 인덱스 설정
    private void setWordIndex(){

        for(int i = 0; i < mTestWord.size(); i++)
            mWordIndex.add(i);
    }

    // 랜덤 단어 버튼 4개 생성
    private void newRandomAnswer(String solution){
        int random = 0;

        mRandomAnswer.clear();
        mRandomAnswer.add(solution);

        Collections.shuffle(mWordIndex);

        for(int i = 0; i < ANSWER_NUMBER - 1; i++) {
            if(mTestWord.get(mWordIndex.get(random)).getWordKor().equals(mRandomAnswer.get(0))){
                random++;
            }
            mRandomAnswer.add(mTestWord.get(mWordIndex.get(random++)).getWordKor());
        }

        Collections.shuffle(mRandomAnswer);

        for(int i = 0; i < ANSWER_NUMBER; i++){
            answerBtns.get(i).setText(mRandomAnswer.get(i));
            if(mTestWord.get(wordIndex).getWordKor().equals(mRandomAnswer.get(i))){
                answerNum=i;
            }
        }

    }

    // 인덱스, 단어장, 단어 초기화
    private void startTest(){
        wordIndex = 0;

        if(day==0){
            mTestWordBook = mWordBookDao.getAllWordBook();
            mTestWord = mWordDao.getData();
        }
        else {
            mTestWordBook = mWordBookDao.getWordBook(day);
            mTestWord = mWordDao.getDailyData(day);
        }
        setWordIndex();
        Collections.shuffle(mTestWord);
    }

    // 내가 적은 답이 틀렸는지 맞았는지 체크
    private boolean isCorrect(String answer){
        if(mTestWord.get(wordIndex).getWordKor().equals(answer))
            return true;
        else
            return false;
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

        if (day==0) {
            for(day = 1; day <= WORDBOOK_DAY_NUMBER; day++){
                incorrectNum = mWordDao.getIncorrect(day);
                mTestWordBook.get(day-1).setIncorrectNumber(incorrectNum);
                mTestWordBook.get(day-1).setCorrectNumber(DAILY_VOCA_NUMBER - incorrectNum);
                mTestWordBook.get(day-1).setViewNumber(mTestWordBook.get(day-1).getViewNumber()+1);
                mWordBookDao.setUpdateWordBook(mTestWordBook.get(day));
            }
        }else {
            incorrectNum = mWordDao.getIncorrect(day);
            mTestWordBook.get(0).setIncorrectNumber(incorrectNum);
            mTestWordBook.get(0).setCorrectNumber(DAILY_VOCA_NUMBER - incorrectNum);
            mTestWordBook.get(0).setViewNumber(mTestWordBook.get(0).getViewNumber()+1);
            mWordBookDao.setUpdateWordBook(mTestWordBook.get(0));
        }

        Toast.makeText(this, "짝짝짝, 단어 테스트 종료!", Toast.LENGTH_SHORT).show();
        endActivity();
    }

    private void endActivity(){
        finish();
    }


    @Override
    public void onClick(View v) {

        if(isCorrect(((Button) v).getText().toString())){
            v.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#51b34c")));
//          Toast.makeText(this, "맞았습니다!", Toast.LENGTH_SHORT);
            mTestWord.get(wordIndex).setIsCorrect(1);
        }
        else {
//          Toast.makeText(this, "틀렸습니다!", Toast.LENGTH_SHORT);
            mTestWord.get(wordIndex).setIsCorrect(0);
            answerBtns.get(answerNum).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#51b34c")));
            v.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#d86464")));
        }
        if(isEnd()){
            endTest();
        }else{
            wordIndex++;
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    setfrontView();
                } }, 1000);



        }
    }
}