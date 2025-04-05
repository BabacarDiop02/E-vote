package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Jwt;

import java.util.List;
import java.util.Optional;

@Repository
public interface JwtRepository extends JpaRepository<Jwt, Integer> {
    Optional<Jwt> findByToken(String token);
    List<Jwt> findByDisableAndExpireAndUser_Email(boolean disable, boolean expire, String email);
    void deleteAllByDisableAndExpire(boolean disable, boolean expire);
}
