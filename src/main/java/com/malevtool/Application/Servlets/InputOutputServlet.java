package com.malevtool.Application.Servlets;

import Utils.Logging.Logger;
import com.malevtool.Application.MailService.MailSender;
import com.malevtool.Entities.Message;
import com.malevtool.JSON.JSONBuilder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
public class InputOutputServlet {
    @PostMapping("mail/send")
    @ResponseBody
    public String sendMessage(@RequestBody Message message) {
        try {
            MailSender.getInstance().sendMessage(message);
        } catch (MessagingException e) {
            Logger.log(this, e.getMessage());
            return new JSONBuilder().addAVP("status", "error").addAVP("message", e.getMessage()).getString();
        }
        return new JSONBuilder().addAVP("status","OK").addAVP("message", message.toString()).getString();
    }

    @PostMapping("mail/sendTemplate/{id}")
    @ResponseBody
    public String sentTemplate(@PathVariable String id) {
        return new JSONBuilder().addAVP("status", "OK").getString();
    }
}
