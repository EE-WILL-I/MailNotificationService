package com.malevtool.Application.Servlets;

import Utils.JSON.JSONBuilder;
import Utils.Logging.Logger;
import com.malevtool.Application.MailService.MailSender;
import com.malevtool.Entities.Message;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessagingServlet {
    @PostMapping("mail/send")
    @ResponseBody
    public String sendMessage(@RequestBody Message message) {
        Logger.log(this, "Trying to send message: " + message.toString(), 1);
        try {
            MailSender.getInstance().sendMessage(message);
        } catch (Exception e) {
            Logger.log(this, "Couldn't send message. " + e.getMessage());
            return new JSONBuilder().addAVP("status", "error").addAVP("message",
                    e.getMessage()).getString();
        }
        return new JSONBuilder().addAVP("status","OK").addAVP("message",
                message.toString()).getString();
    }

    @PostMapping("mail/sendTemplate/{id}")
    @ResponseBody
    public String sentTemplate(@PathVariable String id) {
        return new JSONBuilder().addAVP("status", "OK").getString();
    }
}
