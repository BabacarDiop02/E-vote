package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.ElectionDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper.ElectionMapper;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Election;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.ElectionRepository;

@Service
@RequiredArgsConstructor
public class ElectionService {
    private final ElectionRepository electionRepository;
    private final ElectionMapper electionMapper;

    public ElectionDTO createElection(ElectionDTO electionDTO) {
        Election election = electionMapper.toElection(electionDTO);
        return electionMapper.toElectionDTO(electionRepository.save(election));
    }
}
