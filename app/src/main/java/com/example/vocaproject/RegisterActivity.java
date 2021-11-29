package com.example.vocaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private Button mBtnRegister;
    private EditText email, password, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("VocaProject");
        mBtnRegister = (Button) findViewById(R.id.registerButton);
        email = (EditText) findViewById(R.id.idText);
        password = (EditText) findViewById(R.id.passwordText);
        userName = (EditText) findViewById(R.id.myName);

        mBtnRegister.setEnabled(false);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(email.length()>0 && password.length()>5 && userName.length()>0) {
                    mBtnRegister.setEnabled(true);
                    mBtnRegister.setBackgroundColor(Color.BLACK);
                } else {
                    mBtnRegister.setEnabled(false);
                    mBtnRegister.setBackgroundColor(Color.GRAY);
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
                if(email.length()>0 && password.length()>5 && userName.length()>0) {
                    mBtnRegister.setEnabled(true);
                    mBtnRegister.setBackgroundColor(Color.BLACK);
                } else {
                    mBtnRegister.setEnabled(false);
                    mBtnRegister.setBackgroundColor(Color.GRAY);
                }
            }
        });

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(email.length()>0 && password.length()>5 && userName.length()>0) {
                    mBtnRegister.setEnabled(true);
                    mBtnRegister.setBackgroundColor(Color.BLACK);
                } else {
                    mBtnRegister.setEnabled(false);
                    mBtnRegister.setBackgroundColor(Color.GRAY);
                }
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(@NonNull View view) {
                String strEmail = email.getText().toString();
                String strPwd = password.getText().toString();
                String strName = userName.getText().toString();

                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);
                            account.setName(strName);

                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                            Toast.makeText(RegisterActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "회원가입 실패.. 사용할 수 없는 이메일입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}