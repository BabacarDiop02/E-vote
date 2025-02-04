package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Validation;

@AllArgsConstructor
@Service
public class NotificationService {
    private JavaMailSender javaMailSender;

    public void sendCode(Validation validation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@e-vote.sn");
        message.setTo(validation.getUser().getEmail());
        message.setSubject("Your activation code");
        String text = String.format("Hello %s %s,<br />Your activation code is %s,<br />See you soon", validation.getUser().getFirstName(), validation.getUser().getLastName(), validation.getCode());
        message.setText(text);
        javaMailSender.send(message);
    }
}

