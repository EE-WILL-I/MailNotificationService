package com.malevtool.Application.Configurations;

import Utils.JSON.JSONBuilder;
import Utils.Logging.Logger;
import com.malevtool.Entities.MessageTemplate;
import com.malevtool.Proccessing.SQLExecutor;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class DatabaseServlet {
    private final SQLExecutor executor = SQLExecutor.getInstance();

    @GetMapping("/data/templates")
    @ResponseBody
    public MessageTemplate[] getMessageTemplates() {
        ArrayList<MessageTemplate> templates = new ArrayList<>();
        try {
            ResultSet resultSet = executor.executeSelect(executor.loadSQLResource("get_templates.sql"), "*");
            while(resultSet.next()) {
                templates.add(new MessageTemplate(resultSet.getInt("id"), resultSet.getString("subject"),
                        resultSet.getString("content"), resultSet.getString("signature")));
            }
        } catch (Exception e) {
            Logger.log(this, e.getMessage(), 2);
        }
        return templates.toArray(new MessageTemplate[0]);
    }

    @GetMapping("data/templates/get/{id}")
    public  MessageTemplate getMessageTemplate(@PathVariable String id) {
        MessageTemplate template = new MessageTemplate();
        if(id == null || id.isEmpty())
            return template;
        try {
            ResultSet resultSet = executor.executeSelect(executor.loadSQLResource("get_templates.sql"), id);
            while(resultSet.next()) {
                template = new MessageTemplate(resultSet.getInt("id"), resultSet.getString("subject"),
                        resultSet.getString("content"), resultSet.getString("signature"));
            }
        } catch (Exception e) {
            Logger.log(this, e.getMessage(), 2);
        }
        return template;
    }

    @PostMapping("/data/templates/add")
    public String addTemplate() {
        try {
            executor.executeInsert(executor.loadSQLResource("add_template.sql"), "mail_templates", "New Template", "", "");
            ResultSet resultSet = executor.executeSelect(executor.loadSQLResource("get_last_template.sql"));
            String newId = "0";
            if(resultSet.next())
                newId = String.valueOf(resultSet.getInt(1));
            return new JSONBuilder().addAVP("status", "OK").addAVP("id", newId).getString();
        } catch (SQLException e) {
            return new JSONBuilder().addAVP("status", "error").addAVP("message", e.getMessage()).getString();
        }
    }

    @PutMapping("/data/templates/update/{id}")
    public String updateTemplate(@PathVariable String id,
                                 @RequestBody MessageTemplate template) {
        if(id == null || id.isEmpty())
            return new JSONBuilder().addAVP("status", "error").addAVP("message", "id is required").getString();
        if(template == null)
            return new JSONBuilder().addAVP("status", "error").addAVP("message", "template is not found").getString();
        try {
            executor.executeUpdate(executor.loadSQLResource("save_template.sql"), id, template.getSubject(), template.getContent(), template.getSignature());
            return new JSONBuilder().addAVP("status", "OK").getString();
        } catch (SQLException e) {
            return new JSONBuilder().addAVP("status", "error").addAVP("message", e.getMessage()).getString();
        }
    }

    @DeleteMapping("data/templates/delete/{id}")
    public String deleteTemplate(@PathVariable String id) {
        if(id == null || id.isEmpty())
            return new JSONBuilder().addAVP("status", "error").addAVP("message", "id is required").getString();
        try {
            executor.executeUpdate(executor.loadSQLResource("delete_template.sql"), id);
            return new JSONBuilder().addAVP("status", "OK").getString();
        } catch (SQLException e) {
            return new JSONBuilder().addAVP("status", "error").addAVP("message", e.getMessage()).getString();
        }
    }
}
