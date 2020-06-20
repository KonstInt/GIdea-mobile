package com.gideas.ui.home.idea_create;

import java.math.BigInteger;
import java.util.ArrayList;

public class IdeaSet {



    public int id;
    public int votesYes;
    public int votesNo;
    public String dateProposed;
    public String dateEnd;
    public boolean active;
    public String title;
    public String description;
    public String fullDocumentUrl;
    public String place;
    public String effects;
    public BigInteger minusCost;
    public BigInteger plusCost;
    public ArrayList<Topic> topics;
    public ShortUser shortUser;

    public IdeaSet(int id, int votesYes, int votesNo, String dateProposed, String dateEnd, boolean active, ShortUser shortUser, String title, String description, String fullDocumentUrl, String place, String effects, BigInteger minusCost, BigInteger plusCost, ArrayList<Topic> topics) {
        this.id = id;
        this.votesYes = votesYes;
        this.votesNo = votesNo;
        this.dateProposed = dateProposed;
        this.dateEnd = dateEnd;
        this.active = active;
        this.shortUser = shortUser;
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





