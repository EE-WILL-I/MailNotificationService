package com.malevtool.Application.MailService;

import com.malevtool.Application.Connection.AccountManager;
import com.malevtool.Entities.MailingAccount;

import javax.mail.PasswordAuthentication;
import java.util.Properties;

public class Authenticator extends javax.mail.Authenticator {
    private final Properties properties;
    public Authenticator(Properties properties) {
        super();
        this.properties = properties;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        MailingAccount account = AccountManager.getAccount();
        return new PasswordAuthentication(account.getLogin(), account.getPassword());
    }
}

