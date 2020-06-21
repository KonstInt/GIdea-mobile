package com.gideas.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.gideas.AppInfo;
import com.gideas.R;
import com.gideas.ui.home.adapters.NewsAdapter;

public class VerticalScrollNews extends AppCompatActivity {

    NewsAdapter adapter3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_scroll_news);

        final RecyclerView ideasRecycler3 = findViewById(R.id.vertical_news_recycler);
        LinearLayoutManager verticalLayoutManagerNews
                = new LinearLayoutManager(VerticalScrollNews.this, LinearLayoutManager.VERTICAL, false);
        ideasRecycler3.setLayoutManager(verticalLayoutManagerNews);
        adapter3 = new NewsAdapter(VerticalScrollNews.this, AppInfo.news_cards);
        ideasRecycler3.setAdapter(adapter3);



    }

    public void back_from_news_vl(View view) {
        super.onBackPressed();
    }
}
