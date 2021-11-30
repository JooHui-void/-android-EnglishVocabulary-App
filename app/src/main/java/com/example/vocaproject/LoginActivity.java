package com.example.vocaproject;

import static com.example.vocaproject.MainActivity.DAILY_VOCA_NUMBER;
import static com.example.vocaproject.MainActivity.WORDBOOK_DAY_NUMBER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static UserAccount mUserAccount;     //유저 정보
    public static List<Integer> incorrectWord;  //유저 틀린단어 기록용
    public static List<Integer> userView;       //유저뷰 기록용

    private ProgressDialog customProgressDialog;

    private AppDatabase mDb;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText email, password;
    private String strEmail, strPwd;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        customProgressDialog = new ProgressDialog(this);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mDb = AppDatabase.getInstance(this);
        mWordDao = mDb.wordDao();
        mWordBookDao = mDb.wordBookDao();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("VocaProject");

        email = (EditText) findViewById(R.id.lgn_email);
        password = (EditText) findViewById(R.id.lgn_password);
        loginBtn = (Button) findViewById(R.id.loginButton);

        loginBtn.setEnabled(false);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(email.length()>0 && password.length()>0) {
                    loginBtn.setEnabled(true);
                    loginBtn.setBackgroundColor(Color.BLACK);
                } else {
                    loginBtn.setEnabled(false);
                    loginBtn.setBackgroundColor(Color.GRAY);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(email.length()>0 && password.length()>0) {
                    loginBtn.setEnabled(true);
                    loginBtn.setBackgroundColor(Color.BLACK);
                } else {
                    loginBtn.setEnabled(false);
                    loginBtn.setBackgroundColor(Color.GRAY);
                }
            }
        });

        //로그인 버튼
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                customProgressDialog.show();
                strEmail = email.getText().toString();
                strPwd = password.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            resetData();
                            startLoading();
                            customProgressDialog.dismiss();
                        } else {
                            customProgressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //회원가입 버튼
        TextView registerBtn = (TextView) findViewById(R.id.registerButton);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    //파이어베이스에 저장된 유저 데이터 불러오기
    private void resetData(){

        List <Word> mTempWord = mDb.wordDao().getData();    //데이터베이스 유저 정보 업데이트를 위한 임시 Word 리스트
        List <WordBook> mTempWordBook = mDb.wordBookDao().getAllWordBook(); //데이터베이스 유저 정보 업데이트를 위한 임시 WordBook 리스트

        mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                int incorrectNum;

                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    mUserAccount = task.getResult().getValue(UserAccount.class);    //로그인한 유저를 불러옴
                    incorrectWord = mUserAccount.getIncorrectWord();                //유저의 데이터베이스에서 틀린단어 리스트를 불러옴
                    userView = mUserAccount.getUserView();                          //유저의 데이터베이스에서 유저뷰 리스트를 불러옴

//                    for(int i=0; i<incorrectWord.size(); i++)
//                        Log.d("Test: ", "test " + i + "번째 " + incorrectWord.get(i));

                    //word 정보 초기화
                    for (int i = 0; i < mTempWord.size()-1; i++) {
                        mTempWord.get(i).setIsCorrect(incorrectWord.get(i+1));
                        mWordDao.setUpdateWord(mTempWord.get(i));
                    }

                    //wordbook 정보 초기화
                    for(int day = 1; day <= WORDBOOK_DAY_NUMBER; day++){
                        incorrectNum = mWordDao.getIncorrect(day);
                        mTempWordBook.get(day-1).setViewNumber(userView.get(day));
                        mTempWordBook.get(day-1).setIncorrectNumber(incorrectNum);
                        if(mTempWordBook.get(day-1).getViewNumber()>0)
                            mTempWordBook.get(day-1).setCorrectNumber(DAILY_VOCA_NUMBER - incorrectNum);
                        else
                            mTempWordBook.get(day-1).setCorrectNumber(0);
                        mWordBookDao.setUpdateWordBook(mTempWordBook.get(day-1));
                    }
                }
            }
        });
    }
    private void startLoading(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(new Intent(getApplicationContext(), TabActivity.class));
                finish();
                Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
            }
        }, 500);
    }
}