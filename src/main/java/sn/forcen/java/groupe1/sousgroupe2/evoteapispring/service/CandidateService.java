package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import org.springframework.stereotype.Service;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.CandidateDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper.CandidateMapper;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Candidate;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.CandidateRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    public CandidateService(CandidateRepository candidateRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    public Set<CandidateDTO> getAllCandidates() {
        List<Candidate> candidates = this.candidateRepository.findByEnabledTrue();
        return candidates.stream().map(candidateMapper::toDTO).collect(Collectors.toSet());
    }

    public CandidateDTO getCandidateById(int id) {
        return this.candidateMapper.toDTO(this.candidateRepository.findByIdAndEnabledTrue(id).orElseThrow(() -> new RuntimeException("Candidate not found")));
    }

    public CandidateDTO createCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = this.candidateMapper.toEntity(candidateDTO);
        this.candidateRepository.save(candidate);
        return this.candidateMapper.toDTO(candidate);
    }

    public CandidateDTO updateCandidate(CandidateDTO candidateDTO) {
        CandidateDTO candidateDTOUpdate = this.getCandidateById(candidateDTO.getId());
        if (candidateDTO.getFirstName() != null) candidateDTOUpdate.setFirstName(candidateDTO.getFirstName());
        if (candidateDTO.getLastName() != null) candidateDTOUpdate.setLastName(candidateDTO.getLastName());
        if (candidateDTO.getPart() != null) candidateDTOUpdate.setPart(candidateDTO.getPart());
        if (candidateDTO.getProgramNameFile() != null) candidateDTOUpdate.setProgramNameFile(candidateDTO.getProgramNameFile());

        Candidate candidate = this.candidateMapper.toEntity(candidateDTOUpdate);
        this.candidateRepository.save(candidate);
        return candidateDTOUpdate;
    }

    public void deleteCandidate(int id) {
        Candidate candidate = this.candidateRepository.findByIdAndEnabledTrue(id).orElseThrow(() -> new RuntimeException("Candidate not found"));
        candidate.setEnabled(false);
        this.candidateRepository.save(candidate);
    }
}
