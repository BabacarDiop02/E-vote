package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.CandidateDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Candidate;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.VoteRepository;

@Component
@RequiredArgsConstructor
public class CandidateMapper {
    private final VoteRepository voteRepository;
    public CandidateDTO toDTO(Candidate candidate) {
        CandidateDTO candidateDTO = new CandidateDTO();
        BeanUtils.copyProperties(candidate, candidateDTO);
        int voice = this.voteRepository.countVoteByCandidate_Id(candidate.getId());
        candidateDTO.setVoice(voice);
        return candidateDTO;
    }

    public Candidate toEntity(CandidateDTO candidateDTO) {
        Candidate candidate = new Candidate();
        BeanUtils.copyProperties(candidateDTO, candidate);
        return candidate;
    }
}
