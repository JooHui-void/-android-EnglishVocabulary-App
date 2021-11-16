package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppDatabase mDb;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(this);

        mWordDao = mDb.wordDao();
        mWordBookDao = mDb.wordBookDao();

        List<Word> wordList = mWordDao.getData();
//
//       for(int i=0; i<wordList.size(); i++){
//           Log.d("Test", wordList.get(i).getWordEng() + "\n"
//           +wordList.get(i).getWordKor()+"\n");
//       }



    }
}