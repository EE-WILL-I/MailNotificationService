package com.malevtool.Entities;

public class Message {
    private MailingAccount account;
    private String title;
    private String content;
    private String[] recipients;

    public Message(MailingAccount account, String title, String content, String[] recipients) {
        this.account = account;
        this.title = title;
        this.content = content;
        this.recipients = recipients;
    }

    public Message() { this(new MailingAccount(), "", "", null); }

    public void setAccount(MailingAccount account) { this.account = account; }

    public void setTitle(String title) { this.title = title; }

    public void setContent(String content) { this.content = content; }

    public void setRecipients(String[] recipients) { this.recipients = recipients; }

    public MailingAccount getAccount() { return account; }

    public String getTitle() { return title; }

    public String  getContent() { return content; }

    public String[] getRecipients() { return recipients; }

    public String toString() {
        return "Account: " + account.getLogin() + "\nTitle: " + title
                + "\nContent: " + content + "\nRecipients: " + recipients.length;
    }
}
