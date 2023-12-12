package com.loicmaria.batch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailWriter implements ItemWriter<List<String>> {

    private final JavaMailSender mailSender;

    public EmailWriter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void write(List<? extends List<String>> items){
        for (List<String> emails : items) {
            for (String email : emails) {
                sendEmail(email);
            }
        }
    }

    private void sendEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Rappel de retour de livre");
        message.setText("N'oubliez pas de retourner votre livre à la bibliothèque.");

        mailSender.send(message);
    }
}
