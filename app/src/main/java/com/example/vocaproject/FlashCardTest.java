package com.example.vocaproject;

import static com.example.vocaproject.LoginActivity.incorrectWord;
import static com.example.vocaproject.LoginActivity.mUserAccount;
import static com.example.vocaproject.LoginActivity.userView;
import static com.example.vocaproject.MainActivity.DAILY_VOCA_NUMBER;
import static com.example.vocaproject.MainActivity.WORDBOOK_DAY_NUMBER;
import static com.example.vocaproject.MainTestActivity.mUser;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

public class FlashCardTest extends AppCompatActivity {
    TextView alpha2,kor2;
    RelativeLayout front;
    RelativeLayout back;
    private AppDatabase mDb;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;

    private List<Word> mTestWord;
    private List<WordBook> mTestWordBook;
    private int wordIndex = 0;
    private int day;
    private int cardside;
    private Handler mHandler = new Handler();
    Button know,dontKnow,card;
    TextView wordcnt,wordcnt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_test);
        front = findViewById(R.id.front);
        back = findViewById(R.id.back);
        Intent intent = getIntent();
        day = intent.getIntExtra("VocaDay", 0);
        mDb = AppDatabase.getInstance(this);
        mWordDao = mDb.wordDao();
        mWordBookDao = mDb.wordBookDao();
        startTest();

        wordcnt=(TextView)findViewById(R.id.wordcnt);
        wordcnt2=(TextView)findViewById(R.id.wordcnt2);


       alpha2=(TextView)findViewById(R.id.alphaword2);

       kor2=(TextView)findViewById(R.id.korword2);

       know = findViewById(R.id.know);
       dontKnow = findViewById(R.id.dont_know);
       card = findViewById(R.id.card);
        know.setOnClickListener(onClickListener);
        dontKnow.setOnClickListener(onClickListener);
        card.setOnClickListener(onClickListener);

        setfrontView();
    }
    // 인덱스, 단어장, 단어 초기화
    private void startTest() {
        wordIndex = 0;

        if (day==0) {
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
        if(wordIndex == mTestWord.size()-1) {
            return true;
        }
        else {
            return false;
        }
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
                mWordBookDao.setUpdateWordBook(mTestWordBook.get(day-1));
            }
        }else {
            incorrectNum = mWordDao.getIncorrect(day);

            mTestWordBook.get(0).setIncorrectNumber(incorrectNum);
            mTestWordBook.get(0).setCorrectNumber(DAILY_VOCA_NUMBER - incorrectNum);
            mTestWordBook.get(0).setViewNumber(mTestWordBook.get(0).getViewNumber()+1);
            userView.set(day, userView.get(day)+1);
            mWordBookDao.setUpdateWordBook(mTestWordBook.get(0));
        }

        mUserAccount.setIncorrectWord(incorrectWord);
        mUserAccount.setMyCorrectNum(mWordDao.getCorrectAll());
        FirebaseDatabase.getInstance().getReference("VocaProject").child("UserAccount").child(mUser.getUid()).setValue(mUserAccount);

        Toast.makeText(this, "짝짝짝, 단어 테스트 종료!", Toast.LENGTH_SHORT).show();
        endActivity();
    }

    private void endActivity(){
        finish();
    }


    private void setfrontView(){

        wordcnt.setText(wordIndex+1+"/"+"15");
        TextView alpha1=(TextView)findViewById(R.id.alphaword);
        alpha1.setText(mTestWord.get(wordIndex).getWordEng());
        front.setVisibility(View.VISIBLE);
        back.setVisibility(View.INVISIBLE);

    }

    private void setbackView(){

        wordcnt2.setText(wordIndex+1+"/"+"15");
        alpha2.setText(mTestWord.get(wordIndex).getWordEng());
        kor2.setText(mTestWord.get(wordIndex).getWordKor());
        back.setVisibility(View.VISIBLE);
        front.setVisibility(View.INVISIBLE);

    }

    Button.OnClickListener onClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.card:
                        if (cardside == 0) {
                            setfrontView();
                            cardside = 1;
                        } else {
                            setbackView();
                            cardside = 0;
                        }
                        break;
                    case R.id.know:
                        incorrectWord.set(mTestWord.get(wordIndex).getId(), 1);
                        mTestWord.get(wordIndex).setIsCorrect(1);
                        wordIndex++;
                        setfrontView();
                        break;
                    case R.id.dont_know:
                        incorrectWord.set(mTestWord.get(wordIndex).getId(), 0);
                        mTestWord.get(wordIndex).setIsCorrect(0);
                        wordIndex++;
                        setfrontView();
                        break;

                }

                if (isEnd()) {
                    endTest();
                }
            }
    } ;

}