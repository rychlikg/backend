package com.grzegorz.rychlik.backend.service;


import com.grzegorz.rychlik.backend.model.dao.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final ITemplateEngine iTemplateEngine;
    private final TemplateService templateService;

    @Async
    public void sendEmail(Map<String,Object> variables, String templateName, String emailReceiver){
        Template template = templateService.findByName(templateName);
        Context context = new Context(Locale.getDefault(), variables);
        String body = iTemplateEngine.process(template.getBody(), context);
        javaMailSender.send(mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(emailReceiver);
            mimeMessageHelper.setSubject(template.getSubject());
            mimeMessageHelper.setText(body,true);
        });
    }
}
