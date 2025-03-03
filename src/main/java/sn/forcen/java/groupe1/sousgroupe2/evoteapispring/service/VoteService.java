package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Candidate;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Vote;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.CandidateRepository;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.VoteRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final CandidateRepository candidateRepository;

    public String recordVote(Integer candidateId, String username) {
        String voteHash = this.hashVote(username);

        if (voteRepository.existsByVoteHash(voteHash)) {
            return "You have already voted!";
        }

        Candidate candidate = this.candidateRepository.findById(candidateId).orElseThrow(() -> new RuntimeException("Candidate not found"));
        candidate.setVoice(candidate.getVoice() + 1);
        this.candidateRepository.save(candidate);

        Vote vote = new Vote();
        vote.setCandidate(candidate);
        vote.setVoteHash(voteHash);
        this.voteRepository.save(vote);

        return "Vote successfully recorded!";
    }

    private String hashVote(String username) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // On utilise SHA-256
            byte[] hash = md.digest(username.getBytes()); // On génère le hash

            // On transforme le tableau de bytes en une chaîne hexadécimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString(); // Le hash sous forme de chaîne
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
