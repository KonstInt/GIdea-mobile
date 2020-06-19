package com.gideas.ui.home;

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

import com.gideas.R;
import com.gideas.ui.home.adapters.Idea;
import com.gideas.ui.home.adapters.IdeasAdapter;
import com.gideas.ui.home.adapters.StoriesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {



    private TextView howMachIdeas;
    private TextView userName;
    private CircleImageView userProfileImage;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    StoriesAdapter adapter;
    IdeasAdapter adapter1;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();



        userName = root.findViewById(R.id.header_username);
        howMachIdeas = root.findViewById(R.id.header_ideas_num);
        userProfileImage = root.findViewById(R.id.header_ava);

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



        ArrayList<Idea> ideas = new ArrayList<Idea>();

        ArrayList<String> problems = new ArrayList<>();
        problems.add("fhhhg");

        Idea id = new Idea("fdfdfdff", "ssss", "ffff", problems, 12);

        ideas.add(id);

        RecyclerView ideasRecycler = root.findViewById(R.id.heaer_ideas);
        LinearLayoutManager horizontalLayoutManagerIdeas
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ideasRecycler.setLayoutManager(horizontalLayoutManagerIdeas);
        adapter1 = new IdeasAdapter(getContext(), ideas);
        ideasRecycler.setAdapter(adapter1);

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
                    //userName.setText(dataSnapshot.child("name").getValue().toString());
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
