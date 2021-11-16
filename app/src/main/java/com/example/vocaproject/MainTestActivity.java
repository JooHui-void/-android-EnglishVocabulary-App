package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class MainTestActivity extends AppCompatActivity {
    List<WordBook> booklist;
    private AppDatabase db;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);


        db = AppDatabase.getInstance(this);

        mWordDao = db.wordDao();
        mWordBookDao = db.wordBookDao();
        booklist = mWordBookDao.getAll();

//        for(int i=0; i<wordList.size(); i++){
//           Log.d("Test", wordList.get(i).getDay() + "\n");
//        }
        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ListView listview = findViewById(R.id.listv);
        ExpandableListView expandableListView = findViewById(R.id.expandable);
//        expandableListView.addView();

//        for(int i=0;i<booklist.size();i++){
//            WordBook tmp =booklist.get(i);
//            adapter.addItem(tmp);
//            Log.d("Test", "1" + "\n");
//        }
//        for(int i=0;i<adapter.getCount();i++){
//            Log.d("Test", adapter.getItem(i).getDay() + "\n");
//        }
        ArrayList<WordBook> arrayList= new ArrayList<WordBook>();
        for(int i=0;i<booklist.size();i++){
            arrayList.add(booklist.get(i));
        }

        MyAdapter adapter = new MyAdapter(this,arrayList);
        listview.setAdapter(adapter);

    }
    public class MyAdapter extends BaseAdapter {
        Context mContext = null;
        LayoutInflater mLayoutInflater = null;
        ArrayList<WordBook> book = new ArrayList<WordBook>();

        public MyAdapter(Context context, ArrayList<WordBook> data) {
            mContext = context;
            book = data;
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        public void addItem(WordBook b) {
            book.add(b);
        }

        @Override
        public int getCount() {
            return book.size();
        }

        @Override
        public WordBook getItem(int position) {
            return book.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View conview, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.activity_my_item, parent, false);
            TextView day, cor, incor, play;
            day = view.findViewById(R.id.daynum);
            play = view.findViewById(R.id.playtime);
            cor = view.findViewById(R.id.correctword);
            incor = view.findViewById(R.id.incorrectword);
            if (day == null) {
                Log.d("Test", "wrong");
            }
            WordBook tmp = book.get(position);
            day.setText(Integer.toString(book.get(position).getDay()));
            play.setText(Integer.toString(book.get(position).getViewNumber()));
            cor.setText(Integer.toString(book.get(position).getCheckNumber()));
            incor.setText(Integer.toString(book.get(position).getIncorrectNumber()));

            return view;
        }



    }

}