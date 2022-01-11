package com.malevtool.Application.Servlets;

import Utils.JSON.JSONBuilder;
import Utils.Properties.PropertyReader;
import Utils.Properties.PropertyType;
import com.malevtool.Entities.MailingAccount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexServlet {

    @GetMapping({"/", "/index", "/info", "/status"})
    public String getStatusInfo() {
        return new JSONBuilder().addAVP("status","OK").getString();
    }

    @GetMapping("/account/info")
    @ResponseBody
    public MailingAccount getCurrentAccountInfo() {
        return new MailingAccount("0",
                PropertyReader.getPropertyValue(PropertyType.MAILSERVICE, "mail.smtp.user"),
                PropertyReader.getPropertyValue(PropertyType.MAILSERVICE, "mail.smtp.password"));
    }
} /* f[f[f[f[f[f[f ахахахахахахха ищи себя */
