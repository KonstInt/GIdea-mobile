package com.gideas.ui.home.idea_create;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderIder {

    @GET("document//all_user_short")
    Call<List<IdeaSet>> getIdeas();

    @GET("news/all")
    Call<List<IdeaSet>> getNews();

    @GET("getrus")
    Call<List<IdeaSet>> getRus();



}
