package com.gideas.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gideas.AppInfo;
import com.gideas.R;
import com.gideas.ui.home.adapters.IdeasAdapter;
import com.gideas.ui.home.adapters.NewsAdapter;
import com.gideas.ui.home.adapters.StoriesAdapter;
import com.gideas.ui.home.adapters.SurvsAdapter;
import com.gideas.ui.home.idea_create.IdeaSet;
import com.gideas.ui.home.news_create.NewsSet;
import com.gideas.ui.home.survs.SurveySet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {



    private TextView howMachIdeas, goto_vertical;
    private TextView userName;
    private CircleImageView userProfileImage;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    StoriesAdapter adapter;
    IdeasAdapter adapter1;
    NewsAdapter adapter3;
    SurvsAdapter adapter4;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();



        userName = root.findViewById(R.id.header_username);
        howMachIdeas = root.findViewById(R.id.header_ideas_num);
        userProfileImage = root.findViewById(R.id.header_ava);

        goto_vertical = root.findViewById(R.id.goto_vertical);
        goto_vertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent g = new Intent(getActivity(), VerticalScrollideasActivity.class);
                startActivity(g);

            }
        });

        LoadUserData();

        ArrayList<Integer> viewColors = new ArrayList<>();
        viewColors.add(Color.WHITE);
        viewColors.add(Color.WHITE);
        viewColors.add(Color.WHITE);
        viewColors.add(Color.WHITE);
        viewColors.add(Color.WHITE);

        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Общайся");
        animalNames.add("Делись");
        animalNames.add("Оценивай");
        animalNames.add("Комментируй");
        animalNames.add("И все");



        // set up the RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.heaer_stories);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new StoriesAdapter(getContext(), viewColors, animalNames);
        recyclerView.setAdapter(adapter);


        final RecyclerView ideasRecycler = root.findViewById(R.id.heaer_ideas);
        LinearLayoutManager horizontalLayoutManagerIdeas
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ideasRecycler.setLayoutManager(horizontalLayoutManagerIdeas);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://digitalcontest2020.eu-central-1.elasticbeanstalk.com/digitalcontest/api/").addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolderIder jsonPlaceHolder = retrofit.create(JsonPlaceHolderIder.class);
        final Call<List<com.gideas.ui.home.idea_create.IdeaSet>> get_ideas = jsonPlaceHolder.getIdeas();

        get_ideas.enqueue(new Callback<List<com.gideas.ui.home.idea_create.IdeaSet>>() {
            @Override
            public void onResponse(Call<List<com.gideas.ui.home.idea_create.IdeaSet>> call, Response<List<com.gideas.ui.home.idea_create.IdeaSet>> response) {
                if(!response.isSuccessful())
                    return;

                AppInfo.idea_cards = (ArrayList<com.gideas.ui.home.idea_create.IdeaSet>) response.body();

                adapter1 = new IdeasAdapter(getContext(), AppInfo.idea_cards);
                ideasRecycler.setAdapter(adapter1);

            }

            @Override
            public void onFailure(Call<List<com.gideas.ui.home.idea_create.IdeaSet>> call, Throwable t){

            }
        });



        final RecyclerView ideasRecycler3 = root.findViewById(R.id.heaer_news);
        LinearLayoutManager verticalLayoutManagerNews
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ideasRecycler3.setLayoutManager(verticalLayoutManagerNews);


        final Call<List<NewsSet>> get_news = jsonPlaceHolder.getNews();

        get_news.enqueue(new Callback<List<NewsSet>>() {
            @Override
            public void onResponse(Call<List<NewsSet>> call, Response<List<NewsSet>> response) {
                if(!response.isSuccessful())
                    return;

                AppInfo.news_cards = (ArrayList<NewsSet>) response.body();

                adapter3 = new NewsAdapter(getContext(), AppInfo.news_cards);
                ideasRecycler3.setAdapter(adapter3);
                ideasRecycler3.setLayoutFrozen(true);

            }

            @Override
            public void onFailure(Call<List<NewsSet>> call, Throwable t){

            }
        });



        final RecyclerView ideasRecycler4 = root.findViewById(R.id.sv);
        LinearLayoutManager verticalLayoutManagerPolls
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ideasRecycler4.setLayoutManager(verticalLayoutManagerPolls);



        final Call<List<SurveySet>> get_polls = jsonPlaceHolder.getPoll();

        get_polls.enqueue(new Callback<List<SurveySet>>() {
            @Override
            public void onResponse(Call<List<SurveySet>> call, Response<List<SurveySet>> response) {
                if(!response.isSuccessful())
                    return;

                AppInfo.survey_cards = (ArrayList<SurveySet>) response.body();


                adapter4 = new SurvsAdapter(getContext(), AppInfo.survey_cards);
                ideasRecycler4.setAdapter(adapter4);
                ideasRecycler4.setLayoutFrozen(true);
            }

            @Override
            public void onFailure(Call<List<SurveySet>> call, Throwable t){

            }
        });

        return root;
    }


    @Override
    public void onStart() {

        super.onStart();




    }


    private void LoadUserData() {

        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("status") && dataSnapshot.hasChild("image"))
                {
                    userName.setText(dataSnapshot.child("name").getValue().toString());
                    //inputUserName.setText(dataSnapshot.child("name").getValue().toString());
                    //howMachIdeas.setText(dataSnapshot.child("status").getValue().toString());
                    String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(retrieveProfileImage).into(userProfileImage);

                    //ui.ava = dataSnapshot.child("image").getValue().toString();
                }
                else if (dataSnapshot.exists() && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("status") ){
                    userName.setText(dataSnapshot.child("name").getValue().toString());
                    howMachIdeas.setText(dataSnapshot.child("status").getValue().toString());
                }
                else {

                }


            }      @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
