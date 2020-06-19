package com.gideas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Tested extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tested);
    }
}
