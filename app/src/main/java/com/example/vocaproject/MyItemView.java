package com.example.vocaproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.AttributionSource;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyItemView extends LinearLayout {

    TextView day,cor,incor,play;

    public MyItemView(Context context) {
        super(context);
        init(context);
    }
    public MyItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_my_item, this, true);
        day = findViewById(R.id.daynum);
        play = findViewById(R.id.playtime);
        cor = findViewById(R.id.correctword);
        incor = findViewById(R.id.incorrectword);

    }

    public void setDay(String id){
        day.setText(id);
    }

    public void setplay(String id){
        play.setText(id);
    }
    public void setCor(String id){
        cor.setText(id);
    }
    public void setIncor(String id){
        incor.setText(id);
    }


}