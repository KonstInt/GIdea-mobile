package com.gideas;


import com.gideas.ui.home.idea_create.IdeaSet;
import com.gideas.ui.home.news_create.NewsSet;
import com.gideas.ui.home.survs.SurveySet;

import java.util.ArrayList;

//#0758DA #0950C3
public class AppInfo {
    public static boolean nightModeState = false;
    public static String department = "math";

    public static ArrayList<IdeaSet> idea_cards = new ArrayList<>();

    public static ArrayList<NewsSet> news_cards = new ArrayList<>();

    public static ArrayList<SurveySet> survey_cards = new ArrayList<>();
}
