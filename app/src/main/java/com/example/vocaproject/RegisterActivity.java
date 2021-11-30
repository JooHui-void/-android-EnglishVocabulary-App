package com.example.vocaproject;

import static com.example.vocaproject.MainActivity.DAILY_VOCA_NUMBER;
import static com.example.vocaproject.MainActivity.WORDBOOK_DAY_NUMBER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    public static final int PICK_FROM_ALBUM = 1;

    private ProgressDialog customProgressDialog;

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private Button mBtnRegister;
    private EditText email, password, userName;

    private Uri imageUri;
    private String pathUri;
    private File tempFile;
    private ImageView profile;

    private List<Integer> inCorrectWord;
    private List<Integer> checkingWord;
    private List<Integer> userView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AppDatabase db = AppDatabase.getInstance(this);
        WordBookDao WBDao = db.wordBookDao();

        customProgressDialog = new ProgressDialog(this);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mFirebaseAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("VocaProject");
        mBtnRegister = (Button) findViewById(R.id.registerButton);

        email = (EditText) findViewById(R.id.idText);
        password = (EditText) findViewById(R.id.passwordText);
        userName = (EditText) findViewById(R.id.myName);
        profile = (ImageView) findViewById(R.id.Myprofile);

        mBtnRegister.setEnabled(false);

        //프로필 사진 설정
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAlbum();
            }
        });

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

        //회원가이 버튼 클릭 이벤트
        mBtnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(@NonNull View view) {
                customProgressDialog.show();

                String strEmail = email.getText().toString();
                String strPwd = password.getText().toString();
                String strName = userName.getText().toString();

                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final String uid = task.getResult().getUser().getUid();
                            final Uri file = Uri.fromFile(new File(pathUri));

                            StorageReference storageReference = mStorage.getReference()
                                    .child("usersprofileImages").child("uid/"+file.getLastPathSegment());
                            storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    final Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                                    while (!imageUrl.isComplete()) ;

                                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                    UserAccount account = new UserAccount();

                                    resetIncorrectWord();
//                                    resetCheckingWord();
                                    resetUserView();

                                    account.setIdToken(firebaseUser.getUid());
                                    account.setEmailId(firebaseUser.getEmail());
                                    account.setPassword(strPwd);
                                    account.setName(strName);
                                    account.setProfileImageUrl(imageUrl.getResult().toString());
                                    account.setIncorrectWord(inCorrectWord);
//                                    account.setCheckingWord(checkingWord);
                                    account.setUserView(userView);

                                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                                }

                            });
                            customProgressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            customProgressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "회원가입 실패.. 사용할 수 없는 이메일입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void gotoAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) { // 코드가 틀릴경우
            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        tempFile = null;
                    }
                }
            }
            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM: { // 코드 일치
                // Uri
                imageUri = data.getData();
                pathUri = getPath(data.getData());
                profile.setImageURI(imageUri); // 이미지 띄움
                break;
            }
        }
    }

    public String getPath(Uri uri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        return cursor.getString(index);
    }

    private void resetIncorrectWord(){
        inCorrectWord = new ArrayList<Integer>(Collections.nCopies(WORDBOOK_DAY_NUMBER * DAILY_VOCA_NUMBER + 1, -1));
    }

//    private void resetCheckingWord(){
//        checkingWord = new ArrayList<Integer>(Collections.nCopies(WORDBOOK_DAY_NUMBER * DAILY_VOCA_NUMBER, 0));
//    }
//
    private void resetUserView(){
        userView = new ArrayList<Integer>(Collections.nCopies(WORDBOOK_DAY_NUMBER + 1, 0));
    }

}