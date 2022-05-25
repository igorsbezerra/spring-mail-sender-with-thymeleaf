package com.example.mailsender.mail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.ObjectUtils;

import javax.mail.internet.MimeMessage;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public abstract class MailComponent {

    private JavaMailSender javaMailSender;

    protected void sendSimpleMail(MailMessage mailMessage) {
        log.info("Sending email.");

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(mailMessage.getTo());
                simpleMailMessage.setFrom(mailMessage.getFrom());
                simpleMailMessage.setSubject(mailMessage.getSubject());
                simpleMailMessage.setText(mailMessage.getMessage());

            this.javaMailSender.send(simpleMailMessage);
            log.info("Simple email sent successfully.");
        } catch (Exception ex) {
            log.error("Error when tried to send the email.");
        }
    }

    protected void sendAdvancedMail(MailMessage mailMessage) {
        log.info("Sending email.");

        try {
            MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setTo(mailMessage.getTo());
                helper.setFrom(mailMessage.getFrom());
                helper.setSubject(mailMessage.getSubject());
                helper.setText(mailMessage.getMessage(), true);
                

            for (Map.Entry<String, ClassPathResource> map : mailMessage.getAttachments().entrySet()) {
                helper.addAttachment(map.getKey(), map.getValue());
            }

            for (Map.Entry<String, ClassPathResource> map : mailMessage.getBodyFiles().entrySet()) {
                helper.addInline(map.getKey(), map.getValue());
            }

            this.javaMailSender.send(mimeMessage);
            log.info("Advanced email sent successfully.");
        } catch (Exception ex) {
            log.error("Error when tried to send the email.");
        }
    }
}
