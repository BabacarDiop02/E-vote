package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.ElectionDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper.ElectionMapper;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Election;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.ElectionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElectionService {
    private final ElectionRepository electionRepository;
    private final ElectionMapper electionMapper;

    public List<ElectionDTO> getAllElections() {
        List<Election> elections = electionRepository.findAll();
        return elections.stream().map(ElectionMapper::toElectionDTO).collect(Collectors.toList());
    }

    public ElectionDTO getElectionById(Long id) {
        Election election = electionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Election not found"));
        return ElectionMapper.toElectionDTO(election);
    }

    public ElectionDTO createElection(ElectionDTO electionDTO) {
        Election election = electionMapper.toElection(electionDTO);
        return ElectionMapper.toElectionDTO(electionRepository.save(election));
    }
}
