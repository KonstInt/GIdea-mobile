package com.gideas.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.gideas.AppInfo;


public class OtherInfoSharedPref {
    SharedPreferences isStart;
    SharedPreferences testPosition;
    SharedPreferences rightAnswers;
    SharedPreferences Department;

    public OtherInfoSharedPref(Context context)
    {
        Department = context.getSharedPreferences("TEST_SUBJECT", Context.MODE_PRIVATE);
    }

    public void setDepartment(String state) {
        SharedPreferences.Editor editor = Department.edit();
        editor.putString("Subject", state);
        editor.commit();
    }




    public String loadDepartment(){
        String state = Department.getString("Subject", AppInfo.department);
        return state;
    }



}
