package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;


    public String buildTrelloCardEmail(String message) {
        List<String> funcjonality= new ArrayList<>();
        funcjonality.add("You can manage this task");
        funcjonality.add("Provides connection to trello Acount");
        funcjonality.add("Aplication allows sending tasks to trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("goodbye_message", "Papa");
        context.setVariable("preview_message", "Hello");
        context.setVariable("task_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_funcjonality", funcjonality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
