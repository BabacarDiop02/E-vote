package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.CandidateDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Candidate;

@Component
public class CandidateMapper {
    public CandidateDTO toDTO(Candidate candidate) {
        CandidateDTO candidateDTO = new CandidateDTO();
        BeanUtils.copyProperties(candidate, candidateDTO);
        return candidateDTO;
    }

    public Candidate toEntity(CandidateDTO candidateDTO) {
        Candidate candidate = new Candidate();
        BeanUtils.copyProperties(candidateDTO, candidate);
        return candidate;
    }
}
