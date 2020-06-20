package com.gideas.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.gideas.AppInfo;
import com.gideas.R;
import com.gideas.ui.home.adapters.StoriesAdapter;
import com.gideas.ui.home.adapters.VerticalScrollAdapter;
import com.gideas.ui.home.idea_create.JsonPlaceHolderIder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerticalScrollideasActivity extends AppCompatActivity {


    VerticalScrollAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_scrollideas);


        RecyclerView recyclerView = findViewById(R.id.vertical_ideas);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(VerticalScrollideasActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new VerticalScrollAdapter(this, AppInfo.idea_cards);
        recyclerView.setAdapter(adapter);
    }

    public void GoBackFromerticalIdeas(View view) {
        super.onBackPressed();
    }
}
