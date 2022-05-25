package com.example.mailsender.mail;

import com.example.mailsender.model.Student;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Component
public class StudentsMailComponent extends MailComponent {

    private TemplateEngine templateEngine;

    public StudentsMailComponent(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        super(javaMailSender);
        this.templateEngine = templateEngine;
    }

    public void sendSimpleWelcomeEmail(Student student) {
        MailMessage mailMessage = MailMessage.builder()
                .to(student.getEmail())
                .from("igor.souza1.bezerra@gmail.com")
                .subject("Aula Experts Club - Spring Mail Sender")
                .message(String.format("Olá, %s! Espero que você tenha curtido a aula!", student.getName()))
                .build();
        this.sendSimpleMail(mailMessage);
    }

    public void sendWelcomeEmail(Student student) {
        Context context = new Context();
        context.setVariable("name", student.getName());
        context.setVariable("email", student.getEmail());
        context.setVariable("birthday", student.getBirthday());
        context.setVariable("date", LocalDateTime.now());

        String templateHtml = this.templateEngine.process("welcome-template", context);

        MailMessage mailMessage = MailMessage.builder()
                .to(student.getEmail())
                .from("igor.souza1.bezerra@gmail.com")
                .subject("Aula Experts Club - Spring Mail Sender")
                .bodyFile("headerLogo", new ClassPathResource("static/images/Logo_ExpertsClub.png"))
                .attachment("Apresentacao.pptx", new ClassPathResource("static/docs/Apresentacao_Spring_Mail_Thymeleaf.pptx"))
                .message(templateHtml)
                .build();
        this.sendAdvancedMail(mailMessage);
    }
}
