package com.malevtool.Entities;

public class MailingAccount {
    private String id;
    private String login;
    private String password;

    public MailingAccount(String id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public MailingAccount() {
        this("", "", "");
    }

    public String getId() {
        return id;
    }

    public String getLogin() { return login; }

    public String getPassword() { return password; }

    public void setId(String id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) { this.password = password; }
}
