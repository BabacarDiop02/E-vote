package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper.UserMapper;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.User;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Validation;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.ValidationRepository;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;
@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationService {
    private final ValidationRepository validationRepository;
    private final UserMapper userMapper;
    private final NotificationService notificationService;

    public void saveValidation(User user) {
        Validation validation = new Validation();
        validation.setUser(user);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        validation.setCode(code);
        validation = this.validationRepository.save(validation);
        this.notificationService.sendCode(validation);
    }

    public Validation readAccordingToCode(String code) {
        return this.validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Your code is invalid"));
    }

    @Scheduled(cron = "0 */60 * * * *")
    @Transactional
    public void removeValidationAfterActivation() {
        this.validationRepository.deleteAllByActivationIsBeforeOrExpirationIsBefore(Instant.now(), Instant.now());
        log.info("Successfully removed validation");
    }
}
