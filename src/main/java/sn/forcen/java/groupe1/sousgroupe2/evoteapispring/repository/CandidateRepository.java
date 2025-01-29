package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Candidate;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    List<Candidate> findByEnabledTrue();
    Optional<Candidate> findByIdAndEnabledTrue(int id);
}
