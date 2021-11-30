package com.example.vocaproject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

@Database(entities = {Word.class, WordBook.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    // 데이터에 접근하게 함 -> 데이터 삽입, 조희, 삭제 등
    public abstract WordDao wordDao();
    public abstract WordBookDao wordBookDao();

    private final static String DB_NAME = "Vocabulary.db";
    private static AppDatabase mAppDatabase;

    // DB생성
    public static AppDatabase getInstance(Context context) {
        if (mAppDatabase == null) {
            mAppDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .createFromAsset("database/Word.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return mAppDatabase;
    }
}