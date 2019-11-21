package com.example.myapplication;

public class Message {
    public Message(String message, int source) {
        this.setMessage(message);
        this.setSource(source);
    }

    private String message;
    private int source;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
