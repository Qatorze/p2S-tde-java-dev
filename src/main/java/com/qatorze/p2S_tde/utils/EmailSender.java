package com.qatorze.p2S_tde.utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Component
public class EmailSender {

    private final JavaMailSender mailSender;

    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Invia una mail con il contenuto fornito.
     *
     * @param to       L'indirizzo email del destinatario.
     * @param subject  Il soggetto dell'email.
     * @param body     Il corpo dell'email.
     */
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // Imposta il corpo come HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Log o gestione dell'errore in produzione
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
