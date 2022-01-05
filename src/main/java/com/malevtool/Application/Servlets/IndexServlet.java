package com.malevtool.Application.Servlets;

import com.malevtool.JPA.Entities.JSONData;
import com.malevtool.JPA.Entities.MailingAccount;
import com.malevtool.JSON.JSONBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class IndexServlet {

    @GetMapping({"/", "/index", "/info"})
    public MailingAccount getDefaultAccountInfo() {
        return getAccountInfo("0");
    }

    @GetMapping("/account/info/{id}")
    @ResponseBody
    public MailingAccount getAccountInfo(@PathVariable String id) {
        JSONData account = new MailingAccount(id, "default@account.test");
        //JSONData account1 = new MailingAccount("666", "ultimate@user.nice");
        JSONBuilder builder = new JSONBuilder();
        String data = builder.addAVP("mode","test")
                .openArray("accounts")
                .addSubJSONElement(account.toJSONString())
                .closeArray().getString();
        return new MailingAccount(id, "test@test.test");
    }

    @PostMapping(value = "/account/provide", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String provideAccount(@RequestBody MailingAccount account) {
        return "{\"id\" : \""+account.getId()+"\"}";
    }
}
