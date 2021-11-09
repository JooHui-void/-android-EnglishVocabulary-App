package com.example.vocaproject;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabActivity extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        TabHost tabHost = (TabHost) findViewById(R.id.host);
        tabHost.setup(getLocalActivityManager());

        TabHost.TabSpec spec = tabHost.newTabSpec("tab1")
                .setIndicator(null,getResources().getDrawable(R.drawable.icon_wordbook))
                .setContent(new Intent(this, MainWordbookActivity.class));
        tabHost.addTab(spec);

        // 테스트 액티비티 추가 예정
        spec = tabHost.newTabSpec("tab2")
                .setIndicator(null, getResources().getDrawable(R.drawable.icon_test))
                .setContent(new Intent(this,MainTestActivity.class));
        tabHost.addTab(spec);

        // 즐겨찾기 액티비티 추가 예정
        spec = tabHost.newTabSpec("tab3")
                .setIndicator(null, getResources().getDrawable(R.drawable.icon_heart))
                .setContent(new Intent(this,BookmarkActivity.class));
        tabHost.addTab(spec);

        // 탐색 액티비티 추가 예정
        spec = tabHost.newTabSpec("tab4")
                .setIndicator(null, getResources().getDrawable(R.drawable.icon_search))
                .setContent(new Intent(this,SearchActivity.class));
        tabHost.addTab(spec);

        tabHost.setCurrentTabByTag("tab1");
    }

}