package com.gideas.idea_create;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderIder {

    @GET("document/all ")
    Call<List<IdeaSet>> getIdeas();

    @GET("getinf")
    Call<List<IdeaSet>> getInf();

    @GET("getrus")
    Call<List<IdeaSet>> getRus();



}
