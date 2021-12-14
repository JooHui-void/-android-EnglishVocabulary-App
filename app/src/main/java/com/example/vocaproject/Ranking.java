package com.example.vocaproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

class Data implements Comparable<Data>{
    String name;
    int myCorrectNum;
    public Data(){

    }
    public Data(String name,int myCorrectNum){
        this.name=name;
        this.myCorrectNum=myCorrectNum;
    }

    public int getMyCorrectNum() {
        return myCorrectNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMyCorrectNum(int myCorrectNum) {
        this.myCorrectNum = myCorrectNum;
    }

    @Override
    public int compareTo(Data data){
        if(data.myCorrectNum>myCorrectNum){
            return 1;
        }else if(data.myCorrectNum<myCorrectNum){
            return -1;
        }else{
            return 0;
        }
    }

}

public class Ranking extends AppCompatActivity {

    private final ArrayList<Data> dataList = new ArrayList<>();
    NewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ListView listView = findViewById(R.id.recycler);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("VocaProject").child("UserAccount");
        if(databaseReference==null){
            Log.d("test","null");
        }
        adapter = new NewAdapter(this,dataList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String name = ds.child("name").getValue(String.class);
                    int correct=ds.child("myCorrectNum").getValue(int.class);
//
                    Log.d("Test",name+correct);
                    Data a=new Data();
                    a.setMyCorrectNum(correct);
                    a.setName(name);
                    dataList.add(a);

                }
                Collections.sort(dataList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("Test",Integer.toString(dataList.size()));

        listView.setAdapter(adapter);

    }


    public class NewAdapter extends BaseAdapter {
        Context mContext = null;
        LayoutInflater mLayoutInflater = null;
        ArrayList<Data> data = new ArrayList<Data>();

        public NewAdapter(Context context, ArrayList<Data> data) {
            mContext = context;
            this.data=data;
            mLayoutInflater = LayoutInflater.from(mContext);
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Data getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View conview, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.rankingbox, parent, false);
            TextView name,correct,rank;

            name = view.findViewById(R.id.name);
            correct = view.findViewById(R.id.correct);
            rank = view.findViewById(R.id.rank);
            Data d = data.get(position);
            Log.d("test", d.getName());
            name.setText(d.getName());
            correct.setText(Integer.toString(d.getMyCorrectNum()));
            rank.setText(Integer.toString(position+1));

            return view;
        }



    }
}