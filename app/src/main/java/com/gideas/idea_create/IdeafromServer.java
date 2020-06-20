package com.gideas.idea_create;

import java.math.BigInteger;
import java.util.ArrayList;

public class IdeafromServer {


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
    ArrayList<TopicGet> topics;

    public IdeafromServer(int id, int votesYes, int votesNo, String dateProposed, String dateEnd, boolean active, String title, String description, String fullDocumentUrl, String place, String effects, BigInteger minusCost, BigInteger plusCost, ArrayList<TopicGet> topics) {
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

class TopicGet {
    private Short id;
    private String topic;
}



