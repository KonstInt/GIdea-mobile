package com.gideas.idea_create;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gideas.AppInfo;
import com.gideas.R;

import java.util.ArrayList;

public class NewIdeaAdditional extends AppCompatActivity {


    String[] list_items_addit;
    boolean[] checked_items_addit;
    ArrayList<Integer> userListItems_addit = new ArrayList<>();
    TextView itm_t_addit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea_additional);

        list_items_addit = getResources().getStringArray(R.array.predictable_results);
        checked_items_addit = new boolean[list_items_addit.length];

        itm_t_addit = findViewById(R.id.items_addit);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }




    public void back_from_additional_idea(View view) {
        super.onBackPressed();
    }
}


