package com.loicmaria.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import com.loicmaria.batch.proxies.ApiProxy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendEmailTasklet implements Tasklet {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ApiProxy apiProxy;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext){
        List<String> emails = apiProxy.getExpiredBookings();
        for (String email : emails) {
            sendEmail(email);
        }
        return RepeatStatus.FINISHED;
    }

    private void sendEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Rappel de retour de livre");
        message.setText("N'oubliez pas de retourner votre livre à la bibliothèque.");
        mailSender.send(message);
    }
}
