package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Validation;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Integer> {
    Optional <Validation> findByCode(String code);
    void deleteAllByActivationIsBeforeOrExpirationIsBefore(Instant activation, Instant expiration);
}
