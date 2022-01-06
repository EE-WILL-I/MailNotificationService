package com.malevtool.Application.Connection;

import Utils.Logging.Logger;
import Utils.Properties.PropertyReader;
import Utils.Properties.PropertyType;
import com.malevtool.Application.MailService.MailSender;
import com.malevtool.Entities.MailingAccount;

public class AccountManager {
    private static String login;
    private static String password;

    public static boolean loadConfiguredAccountCredentials() {
        String log = PropertyReader.getPropertyValue(PropertyType.MAILSERVICE, "mail.smtp.user");
        String pass = PropertyReader.getPropertyValue(PropertyType.MAILSERVICE, "mail.smtp.password");
        if(log.isEmpty() || pass.isEmpty())
            return false;
        updateAccount(log, pass);
        return true;
    }

    public static void loadDefaultAccountCredentials() {
        updateAccount("mailnotificationservice@mail.ru", "bWnNUdWLDmcNrnng8kh0");
    }

    public static boolean updateAccount(String login, String password) {
        try {
            PropertyReader.saveProperty(PropertyType.MAILSERVICE, "mail.smtp.user", login);
            PropertyReader.saveProperty(PropertyType.MAILSERVICE, "mail.smtp.password", password);
            AccountManager.login = login;
            AccountManager.password = password;
            MailSender.getInstance().update(PropertyReader.getProperties(PropertyType.MAILSERVICE));
            return true;
        } catch (Exception e) {
            Logger.log(AccountManager.class, e.getMessage(), 2);
            return false;
        }
    }

    public static MailingAccount getAccount() {
        return new MailingAccount("0", login, password);
    }
}
