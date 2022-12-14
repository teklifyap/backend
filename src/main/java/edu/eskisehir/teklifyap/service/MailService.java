package edu.eskisehir.teklifyap.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public MailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public String build(String template, Map<String, String> content) {
        Context context = new Context();
        for (String iter : content.keySet())
            context.setVariable(iter, content.get(iter));
        return templateEngine.process(template, context);
    }

    @Async
    public void sendMail(String to, String subject, String template, Map<String, String> context)
            throws MailException, MessagingException, UnsupportedEncodingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setText(build(template, context), true);

        helper.setFrom(new InternetAddress("teklifyap2022@gmail.com", "teklifyap"));
        helper.setTo(to);
        helper.setSubject(subject);
        javaMailSender.send(mimeMessage);
        log.info("sendMail(" + to + ") -> ( subject : " + subject + ", template : " + template + ", context : " + context);

    }

    @Async
    public void sendMailWithAttachment(String to, String subject, File attachment)
            throws MailException, MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        helper.setFrom(new InternetAddress("teklifyap2022@gmail.com", "teklifyap"));
        helper.setTo(to);
        helper.setSubject(subject);

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Thank you for your export. Please find attached the export offer for your reference.");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachment);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("offer-export-" + LocalDate.now() + ".pdf");
        multipart.addBodyPart(messageBodyPart);
        mimeMessage.setContent(multipart);
        javaMailSender.send(mimeMessage);

        attachment.delete();
    }

}
