package com.example.vocaproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

class Data{
    String name;
    int myCorrectNum;
}

public class Ranking extends AppCompatActivity {

    private final List<Data> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("VocaProject");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Data data = ds.getValue(Data.class);
                    dataList.add(data);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        recyclerView.setAdapter(new CusmtomAdapter());
    }
//    private class ViewHolder extends RecyclerView.ViewHolder{
//        private final TextView textView;
//
//    }
}