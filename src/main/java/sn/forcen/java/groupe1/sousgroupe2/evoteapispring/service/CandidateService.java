package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        return candidates.stream().map(this.candidateMapper::toDTO).collect(Collectors.toSet());
    }

    public CandidateDTO getCandidateById(int id) {
        return this.candidateMapper.toDTO(this.candidateRepository.findByIdAndEnabledTrue(id).orElseThrow(() -> new RuntimeException("Candidate not found")));
    }

    @Transactional
    public CandidateDTO createCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = this.candidateMapper.toEntity(candidateDTO);
        return this.candidateMapper.toDTO(this.candidateRepository.save(candidate));
    }

    @Transactional
    public CandidateDTO updateCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = this.candidateRepository.findByIdAndEnabledTrue(candidateDTO.getId()).orElseThrow(() -> new RuntimeException("Candidate not found"));
        if (candidateDTO.getFirstName() != null) candidate.setFirstName(candidateDTO.getFirstName());
        if (candidateDTO.getLastName() != null) candidate.setLastName(candidateDTO.getLastName());
        if (candidateDTO.getPart() != null) candidate.setPart(candidateDTO.getPart());
        if (candidateDTO.getProgramNameFile() != null) candidate.setProgramNameFile(candidateDTO.getProgramNameFile());

        Candidate candidateUpdate = this.candidateRepository.save(candidate);
        return this.candidateMapper.toDTO(candidateUpdate);
    }

    @Transactional
    public void deleteCandidate(int id) {
        Candidate candidate = this.candidateRepository.findByIdAndEnabledTrue(id).orElseThrow(() -> new RuntimeException("Candidate not found"));
        candidate.setEnabled(false);
        this.candidateRepository.save(candidate);
    }
}
