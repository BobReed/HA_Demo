package com.devreed.homeaway.core;

import java.util.UUID;

/**
 * Created by rreed on 5/24/2014.
 */
public class Person {

    private UUID id;
    private String firstName;
    private String lastName;
    private String title;

    //todo factoids could be their own class
    private String Factoid;
    private String FactoidDesc;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFactoid() {
        return Factoid;
    }

    public void setFactoid(String factoid) {
        Factoid = factoid;
    }

    public String getFactoidDesc() {
        return FactoidDesc;
    }

    public void setFactoidDesc(String factoidDesc) {
        FactoidDesc = factoidDesc;
    }
}
