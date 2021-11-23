package com.example.vocaproject;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListZip implements View.OnClickListener {
    LinearLayout button;
    ImageView zip_img;
    ImageView expand_img;
    ListView listView;
    boolean isZipped = true;

    ListZip(AppCompatActivity root, int wordbookId, int zipId, int expandId, int listview) {
        this.button = root.findViewById(wordbookId);
        this.zip_img = root.findViewById(zipId);
        this.expand_img = root.findViewById(expandId);
        this.listView = root.findViewById(listview);

        button.setOnClickListener(this);
        // listView setting
    }

    @Override
    public void onClick(View view) {
        Zip();
    }

    private void Zip() {
        if (isZipped) {
            zip_img.setVisibility(View.INVISIBLE);
            expand_img.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            isZipped = false;
        } else {
            zip_img.setVisibility(View.VISIBLE);
            expand_img.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.GONE);
            isZipped = true;
        }
    }

}
