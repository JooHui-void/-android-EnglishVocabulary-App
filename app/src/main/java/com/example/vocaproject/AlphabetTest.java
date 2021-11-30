package com.example.vocaproject;

import static com.example.vocaproject.LoginActivity.incorrectWord;
import static com.example.vocaproject.LoginActivity.mUserAccount;
import static com.example.vocaproject.LoginActivity.userView;
import static com.example.vocaproject.MainActivity.DAILY_VOCA_NUMBER;
import static com.example.vocaproject.MainActivity.WORDBOOK_DAY_NUMBER;
import static com.example.vocaproject.MainTestActivity.mUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlphabetTest extends AppCompatActivity implements View.OnClickListener{

    //@BindView(/*TextView 아이디*/)
    TextView korAnswer;
    //@BindView(/*EditText 아이디*/)
    EditText mKorAnswer;

    private AppDatabase mDb;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;

    private List<Word> mTestWord;
    private List<WordBook> mTestWordBook;
    private int wordIndex = 0;
    private int day;
    TextView wordcnt;
    Button bt1,bt2,bt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet_test);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        day = intent.getIntExtra("VocaDay", 0);

        Toolbar myToolbar=findViewById(R.id.alphabet_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wordcnt=findViewById(R.id.testcnt);
        korAnswer=findViewById(R.id.kor_word);
        mKorAnswer=findViewById(R.id.type_word);
        mDb = AppDatabase.getInstance(this);
        mWordDao = mDb.wordDao();
        mWordBookDao = mDb.wordBookDao();
        bt1=findViewById(R.id.re);
        bt2=findViewById(R.id.pass);
        bt3=findViewById(R.id.check);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        startTest();
        setView();
    }

    private void setView(){
        wordcnt.setText(wordIndex+1+"/"+mTestWord.size());
        korAnswer.setText(mTestWord.get(wordIndex).getWordKor());
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

    // 내가 적은 답이 틀렸는지 맞았는지 체크
    private boolean isCorrect(String answer){
        if(mTestWord.get(wordIndex).getWordEng().equals(answer))
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
        FirebaseDatabase.getInstance().getReference("VocaProject").child("UserAccount").child(mUser.getUid()).setValue(mUserAccount);

        Toast.makeText(this, "짝짝짝, 단어 테스트 종료!", Toast.LENGTH_SHORT).show();
        endActivity();
    }

    private void endActivity(){
        finish();
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.re:
                mKorAnswer.setText(null);
                break;
            case R.id.pass:
                incorrectWord.set(mTestWord.get(wordIndex).getId(), 0);
                mTestWord.get(wordIndex).setIsCorrect(0);
                Toast.makeText(getApplicationContext(), "답은 "+ mTestWord.get(wordIndex).getWordEng() +" 입니다!", Toast.LENGTH_SHORT).show();
                if(wordIndex<DAILY_VOCA_NUMBER-1){
                    wordIndex++;
                    setView();
                }else{
                    endTest();
                }
                break;
            case R.id.check:
                if(isCorrect(mKorAnswer.getText().toString())){
                    Toast.makeText( getApplicationContext(), "맞았습니다!", Toast.LENGTH_SHORT).show();
                    mKorAnswer.setText(null);
                    incorrectWord.set(mTestWord.get(wordIndex).getId(), 1);
                    mTestWord.get(wordIndex).setIsCorrect(1);
                    if(wordIndex<DAILY_VOCA_NUMBER-1){
                        wordIndex++;
                        setView();
                    }else{
                        endTest();
                    }
                }
                else{
                    Toast.makeText( getApplicationContext(), "틀렸습니다!", Toast.LENGTH_SHORT).show();
                    mKorAnswer.setText(null);
                }

                break;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                ComponentName componentName =new ComponentName(
                        "com.example.vocaproject",
                        "com.example.vocaproject.TabActivity"
                );
                intent.setComponent(componentName);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();


        return true;
    }

}
