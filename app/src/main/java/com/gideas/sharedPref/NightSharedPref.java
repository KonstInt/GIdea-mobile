package com.gideas.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.gideas.AppInfo;

public class NightSharedPref {
    SharedPreferences theme;
    public NightSharedPref(Context context)
    {
        theme = context.getSharedPreferences("NightModeTheme",Context.MODE_PRIVATE);
    }

    public void setNightModeState(Boolean state) {
        SharedPreferences.Editor editor = theme.edit();
        editor.putBoolean("NightMode", state);
        editor.commit();
    }

    public Boolean loadNightModeState()
    {
      Boolean state = theme.getBoolean("NightMode", AppInfo.nightModeState);
      return state;
    }
}
