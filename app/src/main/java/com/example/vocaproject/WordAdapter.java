package com.example.vocaproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
        Context context;
        int resID;
        ArrayList<Word> datas;

        public WordAdapter( Context context, int resID, ArrayList<Word> datas) {
            super(context, resID);
            this.context = context;
            this.resID = resID;
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(resID, null);
                WordHolder holder = new WordHolder(convertView);
                convertView.setTag(holder);
            }
            WordHolder holder = (WordHolder) convertView.getTag();

            // 단어 생성
            TextView engWord = holder.engWord;
            TextView korWord = holder.korWord;

            final Word words = datas.get(position);

            engWord.setText(words.getWordEng());
            korWord.setText(words.getWordKor());

            // 북마크 이벤트
            FrameLayout bookmark = holder.bookmark;
            ImageView filled = holder.checking_true;
            ImageView empty = holder.checking_false;

            bookmark.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(words.getIsChecking() == 0){
                        // 북마크 해제 상태
                        filled.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.INVISIBLE);
                        // 북마크 업데이트
                    } else{
                        // 북마크 상태
                        filled.setVisibility(View.INVISIBLE);
                        empty.setVisibility(View.VISIBLE);
                        // 북마크 업데이트
                    }
                }
            });

            return super.getView(position, convertView, parent);
        }

        public static class WordHolder {
            public TextView engWord;
            public TextView korWord;
            public ImageView checking_true;
            public ImageView checking_false;
            public FrameLayout bookmark;

            public WordHolder(View root) {
                engWord = root.findViewById(R.id.custom_word_item_engWord);
                korWord = root.findViewById(R.id.custom_word_item_korWord);
                checking_true = root.findViewById(R.id.custom_word_item_filled);
                checking_false = root.findViewById(R.id.custom_word_item_empty);
                bookmark = root.findViewById(R.id.custom_wordbook_item_numWords);
            }
        }


}
