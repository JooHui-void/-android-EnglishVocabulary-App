package com.example.vocaproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WordbookAdapter extends ArrayAdapter<WordBook> {
    Context context;
    int resID;
    ArrayList<WordBook> datas;

    public WordbookAdapter(Context context, int resID, ArrayList<WordBook> datas) {
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resID, null);
            WordbookHolder holder = new WordbookHolder(convertView);
            convertView.setTag(holder);
        }
        WordbookHolder holder = (WordbookHolder) convertView.getTag();

        TextView dateText = holder.dateText;
        TextView view_numText = holder.view_numText;
        TextView correct_numText = holder.correct_numText;
        TextView incorrect_numText = holder.incorrect_numText;
        TextView word_numText = holder.word_numText;

        final WordBook book = datas.get(position);

        dateText.setText("" + book.getDay());
        view_numText.setText(""+book.getViewNumber());
        correct_numText.setText(""+book.getCorrectNumber());
        incorrect_numText.setText(""+book.getIncorrectNumber());
        word_numText.setText(MainActivity.DAILY_VOCA_NUMBER + "words");

        return convertView;
    }

    public static class WordbookHolder {
        public TextView dateText;
        public TextView view_numText;
        public TextView correct_numText;
        public TextView incorrect_numText;
        public TextView word_numText;

        public WordbookHolder(View root) {
            dateText = root.findViewById(R.id.custom_wordbook_item_date);
            view_numText = root.findViewById(R.id.custom_wordbook_item_numView);
            correct_numText = root.findViewById(R.id.custom_wordbook_item_numCorrect);
            incorrect_numText = root.findViewById(R.id.custom_wordbook_item_numIncorrect);
            word_numText = root.findViewById(R.id.custom_wordbook_item_numWords);
        }
    }
}
