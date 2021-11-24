package com.example.vocaproject;

import static com.example.vocaproject.MainActivity.DAILY_VOCA_NUMBER;

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
import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
    Context context;
    int resID;
    List<Word> datas;
    OnWordItemClick mCallback;

    public WordAdapter(Context context, int resID, List<Word> datas, @Nullable OnWordItemClick listener) {
        super(context, resID);
        this.context = context;
        this.resID = resID;
        this.datas = datas;
        this.mCallback = listener;
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

        final Word word = datas.get(position);

        engWord.setText(word.getWordEng());
        korWord.setText(word.getWordKor());

        // 북마크 이벤트
        FrameLayout bookmark = holder.bookmark;
        ImageView filledMark = holder.checking_true;
        ImageView emptyMark = holder.checking_false;

        if(word.getIsChecking() == 0){
            filledMark.setVisibility(View.INVISIBLE);
            emptyMark.setVisibility(View.VISIBLE);
        }else{
            filledMark.setVisibility(View.VISIBLE);
            emptyMark.setVisibility(View.INVISIBLE);
        }

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView filledMark = view.findViewById(R.id.custom_word_item_filled);
                ImageView emptyMark = view.findViewById(R.id.custom_word_item_empty);
                if (word.getIsChecking() == 0) {
                    // 북마크로 설정
                    filledMark.setVisibility(View.VISIBLE);
                    emptyMark.setVisibility(View.INVISIBLE);
                    word.setIsChecking(1);
                    // 북마크 업데이트
                } else {
                    // 북마크 해제
                    filledMark.setVisibility(View.INVISIBLE);
                    emptyMark.setVisibility(View.VISIBLE);
                    word.setIsChecking(0);
                    // 북마크 업데이트
                }
                if(mCallback!=null) mCallback.onWordItemClick(word);
            }
        });
        return convertView;
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
            bookmark = root.findViewById(R.id.custom_word_item_bookmark);
        }
    }

}

