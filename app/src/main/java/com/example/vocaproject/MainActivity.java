package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);

        mWordDao = db.wordDao();
        mWordBookDao = db.wordBookDao();
        List<Word> wordList = mWordDao.getNotCorrectWord();

       for(int i=0; i<wordList.size(); i++){
//           Log.d("Test", wordList.get(i).getWordEng() + "\n"
//           +wordList.get(i).getWordKor()+"\n");
       }


    }
}