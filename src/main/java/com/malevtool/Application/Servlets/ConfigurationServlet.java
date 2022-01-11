package com.malevtool.Application.Servlets;

import Utils.JSON.JSONBuilder;
import Utils.Logging.Logger;
import com.malevtool.Application.Connection.AccountManager;
import com.malevtool.Application.Security.AuthorizationManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationServlet {
    private final String allowedRange = "[A-Za-z0-9+/]+";

    @PutMapping("/credentials/update/{user}/{pass}")
    public String updateCredentials(@PathVariable String user, @PathVariable String pass) {
        Logger.log(this, "Trying to update credentials to: " + user + ":" + pass, 1);
        if(user.isEmpty() || pass.isEmpty()) {
            return new JSONBuilder().addAVP("status", "rejected").addAVP("message", "empty value").getString();
        }
        if(user.matches(allowedRange) && pass.matches(allowedRange)) {
            if(AuthorizationManager.updateCredentials(user, pass)) {
                Logger.log(this, "Updated", 1);
                return new JSONBuilder().addAVP("status", "updated").getString();
            } else {
                Logger.log(this, "Failed", 1);
                return new JSONBuilder().addAVP("status", "error").getString();
            }
        }
        return new JSONBuilder().addAVP("status", "rejected").addAVP("message", "illegal literals").getString();
    }

    @PutMapping("/account/update/{login}/{pass}")
    public String updateAccount(@PathVariable String login, @PathVariable String pass) {
        Logger.log(this, "Trying to update account to: " + login + "\t" + pass, 1);
        if(login.isEmpty() || pass.isEmpty()) {
            return new JSONBuilder().addAVP("status", "rejected").addAVP("message", "empty value").getString();
        }
        if(AccountManager.updateAccount(login, pass))
            Logger.log(this, "Updated", 1);
        else
            Logger.log(this, "Failed", 1);
        return new JSONBuilder().addAVP("status", "updated").getString();
    }
}
