package com.loicmaria.batch.tasklet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(SendEmailTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext){
        logger.info("Début de l'envoi des e-mails.");

        List<String> emails = apiProxy.getExpiredBookings();
        for (String email : emails) {
            sendEmail(email);
        }

        logger.info("Nombre d'e-mails envoyés : " + emails.size());
        logger.info("Fin de l'envoi des e-mails.");

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
