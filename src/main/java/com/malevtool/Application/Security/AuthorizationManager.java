package com.malevtool.Application.Security;

import Utils.Logging.Logger;
import Utils.Properties.PropertyReader;
import Utils.Properties.PropertyType;

import java.util.Base64;
import java.util.Locale;

public class AuthorizationManager {
    private static String encodedUser;
    private static boolean useAuth = true;

    public static String encodeCredentials(String user, String pass) {
        return  Base64.getEncoder().encodeToString((user + ":" + pass).getBytes());
    }

    public static boolean authorize(String credentials) {
        if(useAuth) {
                return encodedUser.equals(credentials);
            }
        return true;
    }

    public static boolean loadConfiguredUserCredentials() {
        String disable = PropertyReader.getPropertyValue(PropertyType.SERVER, "app.disableSecurity");
        if(disable.toLowerCase(Locale.ROOT).equals("true")) {
            useAuth = false;
            return true;
        }
        String user = PropertyReader.getPropertyValue(PropertyType.SERVER, "auth.user");
        String pass = PropertyReader.getPropertyValue(PropertyType.SERVER, "auth.pass");
        if(user.isEmpty() || pass.isEmpty())
            return false;
        encodedUser = encodeCredentials(user, pass);
        return true;
    }

    public static void loadDefaultUserCredentials() {
        encodedUser = encodeCredentials("mnssuperuser", "MNSADMIN01");
    }

    public static boolean updateCredentials(String user, String pass) {
        try {
            PropertyReader.saveProperty(PropertyType.SERVER, "auth.user", user);
            PropertyReader.saveProperty(PropertyType.SERVER, "auth.pass", pass);
            encodedUser = encodeCredentials(user, pass);
            return true;
        } catch (Exception e) {
            Logger.log(AuthorizationManager.class, e.getMessage(), 2);
            return false;
        }
    }

    public static void setUseAuth(boolean value) { useAuth = value; }
}
