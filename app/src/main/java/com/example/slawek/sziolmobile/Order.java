package com.example.slawek.sziolmobile;

/**
 * Created by Michał on 2015-04-13.
 */
public class Order {

    private String title;
    private String description;
    private String status;
    private int customerId;
    private String id;
    private String executorId;
    private boolean assignToTicket;
    private String teamId;
    private String date;
    private String creatorId;

    public Order(String id, String title) {
        this.title = title;
        this.id = id;

    }

    public Order(String id, String title, String description, String status, int customerId, String executorId, String teamId, String date, String creatorId)
    {
        this.title = title;
        this.id = id;
        this.description = description;
        this.status = status;
        this.customerId = customerId;
        this.executorId = executorId;
        this.teamId = teamId;
        this.date = date;
        this.creatorId = creatorId;
    }

        public Order(String title,String description,String status,int customerId)
    {
        this.title = title;
        this.description = description;
        this.status = status;
        this.customerId = customerId;
    }

    public Order (String id, String title, String description, String status, int customerId, String executorId, boolean assignToTicket) {
       this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.customerId = customerId;
        this.executorId = executorId;
        this.assignToTicket = assignToTicket;
    }

    public String getExecutorId(){ return executorId; }
    public boolean getAssignToTicket(){ return assignToTicket; }

    public String getId(){ return id; }
        public String getTitle(){ return title; }
        public String getDescription(){ return description; }
        public String getStatus(){ return status; }
        public int getCustomerId(){ return customerId; }

    public String toString() {
        return this.title;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

 }
