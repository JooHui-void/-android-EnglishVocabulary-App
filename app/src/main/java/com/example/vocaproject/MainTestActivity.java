package com.example.vocaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


abstract class KnowIndexOnClickListener implements View.OnClickListener {

    protected int index;

    public KnowIndexOnClickListener(int index) {
        this.index = index;
    }
}

public class MainTestActivity extends AppCompatActivity {

    public static FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private static DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference("VocaProject");

    List<WordBook> booklist;
    private AppDatabase db;
    private WordDao mWordDao;
    private WordBookDao mWordBookDao;
    LayoutInflater inflater;
    View view;
    View view2;
    Context context;
    MyAdapter adapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        context=this;
        Toolbar myToolbar=findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = AppDatabase.getInstance(this);

        mWordDao = db.wordDao();
        mWordBookDao = db.wordBookDao();
        booklist = mWordBookDao.getAll();

//        for(int i=0; i<booklist.size(); i++){
//           Log.d("Test", booklist.get(i).getDay() + "\n");
//        }
        inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listview = findViewById(R.id.listv);
        view2 = getLayoutInflater().inflate(R.layout.activity_main_test,null);



        ArrayList<WordBook> arrayList= new ArrayList<WordBook>();
        for(int i=0;i<booklist.size();i++){
            arrayList.add(booklist.get(i));
        }

        adapter = new MyAdapter(this,arrayList);
        listview.setAdapter(adapter);


//        final Button set =view2.findViewById(R.id.setting);
//        set.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                ComponentName componentName =new ComponentName(
//                        "com.example.vocaproject",
//                        "com.example.vocaproject.Setting"
//                );
//                intent.setComponent(componentName);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        booklist = mWordBookDao.getAll();
        ArrayList<WordBook> arrayList= new ArrayList<WordBook>();
        for(int i=0;i<booklist.size();i++){
            arrayList.add(booklist.get(i));
        }
        adapter = new MyAdapter(this,arrayList);
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

            final LinearLayout menu =  view.findViewById(R.id.menu);
            day = view.findViewById(R.id.daynum);
            play = view.findViewById(R.id.playtime);
            cor = view.findViewById(R.id.correctword);
            incor = view.findViewById(R.id.incorrectword);

            WordBook tmp = book.get(position);
            day.setText(Integer.toString(book.get(position).getDay()));
            play.setText(Integer.toString(book.get(position).getViewNumber()));
            cor.setText(Integer.toString(book.get(position).getCorrectNumber()));
            incor.setText(Integer.toString(book.get(position).getIncorrectNumber()));

            final Button spread = (Button)view.findViewById(R.id.spread);
            spread.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(menu.getVisibility()==View.VISIBLE){
                        menu.setVisibility(View.GONE);
                        spread.setBackgroundResource(R.drawable.icon_expand);
                    }else{
                        menu.setVisibility(View.VISIBLE);
                        spread.setBackgroundResource(R.drawable.icon_fold);
                    }
                }
            });
            Button flashcardbutton = view.findViewById(R.id.flash_button);
            Button meaningtestbutton = view.findViewById(R.id.mean_button);
            Button alphabettestbutton = view.findViewById(R.id.alphabet_button);

            Button.OnClickListener onClickListener = new KnowIndexOnClickListener(book.get(position).getDay()) {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    ComponentName componentName;
                    switch (view.getId()) {
                        case R.id.flash_button :
                            componentName =new ComponentName(
                                    "com.example.vocaproject",
                                    "com.example.vocaproject.FlashCardTest"
                            );
                            intent.setComponent(componentName);
                            intent.putExtra("VocaDay",index);
                            startActivity(intent);
                            break ;
                        case R.id.mean_button:
                            componentName =new ComponentName(
                                    "com.example.vocaproject",
                                    "com.example.vocaproject.MeaningTest"
                            );
                            intent.setComponent(componentName);
                            intent.putExtra("VocaDay",index);
                            startActivity(intent);
                            break ;
                        case R.id.alphabet_button:
                            componentName =new ComponentName(
                                    "com.example.vocaproject",
                                    "com.example.vocaproject.AlphabetTest"
                            );
                            intent.setComponent(componentName);
                            intent.putExtra("VocaDay",index);
                            startActivity(intent);
                            break ;
                    }
                }
            } ;

            flashcardbutton.setOnClickListener(onClickListener);
            meaningtestbutton.setOnClickListener(onClickListener);
            alphabettestbutton.setOnClickListener(onClickListener);
            return view;
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent();
                ComponentName componentName =new ComponentName(
                        "com.example.vocaproject",
                        "com.example.vocaproject.Setting"
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
        menuInflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }


}