package com.gideas.idea_create;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class IdeaSet {



    int id;
    int votesYes;
    int votesNo;
    String dateProposed;
    String dateEnd;
    boolean active;
    String title;
    String description;
    String fullDocumentUrl;
    String place;
    String effects;
    BigInteger minusCost;
    BigInteger plusCost;
    ArrayList<Topic> topics;

    public IdeaSet(int id, int votesYes, int votesNo, String dateProposed, String dateEnd, boolean active, String title, String description, String fullDocumentUrl, String place, String effects, BigInteger minusCost, BigInteger plusCost, ArrayList<Topic> topics) {
        this.id = id;
        this.votesYes = votesYes;
        this.votesNo = votesNo;
        this.dateProposed = dateProposed;
        this.dateEnd = dateEnd;
        this.active = active;
        this.title = title;
        this.description = description;
        this.fullDocumentUrl = fullDocumentUrl;
        this.place = place;
        this.effects = effects;
        this.minusCost = minusCost;
        this.plusCost = plusCost;
        this.topics = topics;
    }
}



class Topic {
    private Short id;
    private String topic;
}

