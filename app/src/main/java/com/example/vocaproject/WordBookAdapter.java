package com.example.vocaproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordBookAdapter extends RecyclerView.Adapter<WordBookAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.)
        public TextView titleView;
        @BindView(R.id.)
        public TextView menuView1;
        @BindView(R.id.)
        public TextView menuView2;
        @BindView(R.id.)
        public TextView menuView3;
        @BindView(R.id.)
        public TextView viewNumber;
        @BindView(R.id.)
        public TextView correctNumber;
        @BindView(R.id.)
        public TextView inCorrectNumber;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setData(WordBook wordBook){
            viewNumber.setText(wordBook.getViewNumber());
            correctNumber.setText(wordBook.getCorrectNumber());
            inCorrectNumber.setText(wordBook.getIncorrectNumber());
        }

    }
}


