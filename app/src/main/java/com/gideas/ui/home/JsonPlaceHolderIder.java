package com.gideas.ui.home;

import com.gideas.ui.home.idea_create.IdeaSet;
import com.gideas.ui.home.news_create.NewsSet;
import com.gideas.ui.home.survs.SurveySet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderIder {

    @GET("document//all_user_short")
    Call<List<IdeaSet>> getIdeas();

    @GET("news/all_short")
    Call<List<NewsSet>> getNews();

    @GET("poll/all_user_short")
    Call<List<SurveySet>> getPoll();



}
