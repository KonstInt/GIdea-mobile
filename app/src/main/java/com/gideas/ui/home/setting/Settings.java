package com.gideas.ui.home.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.gideas.AppInfo;
import com.gideas.MainActivity;
import com.gideas.R;
import com.gideas.sharedPref.NightSharedPref;


public class Settings extends AppCompatActivity {
    private Switch myswich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        myswich = findViewById(R.id.day_night);
        if(AppInfo.nightModeState == true)
            myswich.setChecked(true);
        myswich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppInfo.nightModeState = isChecked;

                confim(Settings.this,true);
            }
        });


    }


    //Intent g = new Intent(getApplicationContext(), Settings.class);
    //startActivity(g);
    //finish();
    public void confim(Activity activity, boolean animate) {
        NightSharedPref n_state = new NightSharedPref(this);
        n_state.setNightModeState(AppInfo.nightModeState);
        if (animate) {
            Intent restartIntent = new Intent(activity, activity.getClass());

            Bundle extras = activity.getIntent().getExtras();
            if (extras != null) {
                restartIntent.putExtras(extras);
            }
            ActivityCompat.startActivity(
                    activity,
                    restartIntent,
                    ActivityOptionsCompat
                            .makeCustomAnimation(activity, android.R.anim.fade_in, android.R.anim.fade_out)
                            .toBundle()
            );
            activity.finish();
        } else {
            activity.recreate();
        }
    }


    public void BackToMenu(View view) {
        Intent g = new Intent(this, MainActivity.class);
        startActivity(g);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent g = new Intent(this, MainActivity.class);
        startActivity(g);
    }
}



