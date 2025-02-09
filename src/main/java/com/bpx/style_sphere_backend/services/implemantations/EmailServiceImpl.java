package com.bpx.style_sphere_backend.services.implemantations;

import com.bpx.style_sphere_backend.models.dtos.OrderOutputDTO;
import com.bpx.style_sphere_backend.services.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendEmail(String recipient, String subject, String status, OrderOutputDTO order) throws MessagingException {

        Context context = new Context();
        context.setVariable("name", recipient);
        context.setVariable("status", status);
        context.setVariable("orders", order.getOrderItemOutputList());

        String htmlContent = templateEngine.process("order-email-template", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}
