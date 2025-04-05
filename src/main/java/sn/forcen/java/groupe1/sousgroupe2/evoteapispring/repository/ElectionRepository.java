package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Election;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {
}
