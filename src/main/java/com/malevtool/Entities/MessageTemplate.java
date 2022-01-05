package com.malevtool.Entities;

public class MessageTemplate {
    private String title;
    private String content;
    private String signature;

    public MessageTemplate(String title, String content, String signature) {
        this.content = content;
        this.signature= signature;
        this.title = title;
    }

    public MessageTemplate() {
        this("", "", "");
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getSignature() {
        return signature;
    }

    public String getTitle() {
        return title;
    }
}
