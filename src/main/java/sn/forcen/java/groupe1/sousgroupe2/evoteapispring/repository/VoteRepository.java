package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    boolean existsByVoteHash(String voteHash);
}
