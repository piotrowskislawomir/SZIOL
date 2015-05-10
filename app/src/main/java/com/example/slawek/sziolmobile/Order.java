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

    public Order (String id, String title, String description, String status, int customerId) {
       this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.customerId = customerId;
    }
        public String getId(){ return id; }
        public String getTitle(){ return title; }
        public String getDescription(){ return description; }
        public String getStatus(){ return status; }
        public int getCustomerId(){ return customerId; }
 }
