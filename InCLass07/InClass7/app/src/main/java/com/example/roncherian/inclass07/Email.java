package com.example.roncherian.inclass07;

/**
 * Created by roncherian on 23/10/17.
 */

public class Email {

    private String email, summary, subject;

    public Email(String email, String summary, String subject) {
        this.email = email;
        this.summary = summary;
        this.subject = subject;
    }

    public Email(){

    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}