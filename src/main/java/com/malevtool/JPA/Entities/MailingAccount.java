package com.malevtool.JPA.Entities;

import com.malevtool.JSON.JSONBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;

public class MailingAccount implements JSONData {
    private String id;
    private String login;

    public MailingAccount(String id, String login) {
        this.id = id;
        this.login = login;
    }

    public MailingAccount() {
        this("", "");
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toJSONString() {
        JSONBuilder builder = new JSONBuilder();
        builder.addAVP("id", id)
                .addAVP("login", login);
        return builder.getString();
    }

    @Override
    public MailingAccount[] fromJSONString(String json) throws ParseException, IllegalArgumentException {
        ArrayList<MailingAccount> accounts = new ArrayList<>();
        JSONArray data = (JSONArray) new JSONParser().parse(json);
        if(data.size() == 0)
            throw new IllegalArgumentException("Empty json");
        Iterator<JSONObject> iterator = data.iterator();
        while(iterator.hasNext()) {
            JSONObject obj = iterator.next();
            String id = (String) obj.get("id");
            String login = (String) obj.get("login");
            accounts.add(new MailingAccount(id, login));
        }
        return accounts.toArray(new MailingAccount[0]);
    }
}
