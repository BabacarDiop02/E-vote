package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Administrator;

import java.util.Optional;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    Optional<Administrator> findByEmail(String email);
}
