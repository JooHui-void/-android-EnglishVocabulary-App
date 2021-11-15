package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeaningTest extends AppCompatActivity {
    private final static int WORDBOOK_DAY_NUMBER = 15;
    private final static int DAILY_VOCA_NUMBER = 15;
    private final static int ANSWER_NUMBER = 4;

    //@BindViews({/*정답버튼4개아이디*/})
    List<Button> answerBtns;

    private AppDatabase mDb;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;

    private List<Integer> mWordIndex;
    private List<String> mRandomAnswer;
    private List<Word> mTestWord;
    private List<WordBook> mTestWordBook;
    private int wordIndex = 0;

    private int day;
    private int startIndex;
    private int endIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning_test);
        ButterKnife.bind(this);

        mDb = AppDatabase.getInstance(this);
        mWordDao = mDb.wordDao();
        mWordBookDao = mDb.wordBookDao();
        startTest();
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

        for(int i = 0; i < ANSWER_NUMBER; i++)
            answerBtns.get(i).setText(mRandomAnswer.get(i));
    }

    // 인덱스, 단어장, 단어 초기화
    private void startTest(){
        wordIndex = 0;

        if(true/*전체 테스트면*/){
            startIndex = 1;
            endIndex = mWordDao.getAllNumber();

            mTestWordBook = mWordBookDao.getAllWordBook();
            mTestWord = mWordDao.getData();
        }
        else {
            startIndex = (day - 1) * DAILY_VOCA_NUMBER + 1;
            endIndex = day * DAILY_VOCA_NUMBER;

            mTestWordBook = mWordBookDao.getWordBook(day);
            mTestWord = mWordDao.getDailyData(startIndex, endIndex);
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

        if (true/*전체 테스트면*/) {
            for(day = 0; day < WORDBOOK_DAY_NUMBER; day++){
                startIndex = day * DAILY_VOCA_NUMBER + 1;
                endIndex = (day + 1) * DAILY_VOCA_NUMBER;

                incorrectNum = mWordDao.getIncorrect(startIndex, endIndex);
                mTestWordBook.get(day).setIncorrectNumber(incorrectNum);
                mTestWordBook.get(day).setCorrectNumber(DAILY_VOCA_NUMBER - incorrectNum);
                mWordBookDao.setUpdateWordBook(mTestWordBook.get(day));
            }
        }else {
            incorrectNum = mWordDao.getIncorrect(startIndex, endIndex);
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

    @OnClick({/*버튼1, 버튼2, 버튼3, 버튼4*/})
    public void onAnswerBtnClick(Button button){
        if(isCorrect(button.getText().toString())){
            Toast.makeText(this, "맞았습니다!", Toast.LENGTH_SHORT);
            mTestWord.get(wordIndex).setIsCorrect(1);
        }
        else {
            Toast.makeText(this, "틀렸습니다!", Toast.LENGTH_SHORT);
            mTestWord.get(wordIndex).setIsCorrect(0);
        }
        //넘어가기
    }

}