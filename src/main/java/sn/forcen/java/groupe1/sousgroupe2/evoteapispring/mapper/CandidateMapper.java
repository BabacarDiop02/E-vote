package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.CandidateDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Candidate;

@Mapper(componentModel = "Spring")
public interface CandidateMapper {
    CandidateDTO toDTO(Candidate candidate);
    @Mapping(target = "enabled", ignore = true)
    Candidate toEntity(CandidateDTO candidateDTO);
}
