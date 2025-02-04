package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.CandidateDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service.CandidateService;

import java.util.Set;

@RestController
//@RequestMapping(path = "api/e-vote")
@CrossOrigin("*")
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping(path = "/candidates")
    public ResponseEntity<Set<CandidateDTO>> getAllCandidates() {
        Set<CandidateDTO> candidateDTOSet = this.candidateService.getAllCandidates();
        return ResponseEntity.ok(candidateDTOSet);
    }

    @GetMapping(path = "/candidate/{id}")
    public ResponseEntity<?> getCandidateById(@PathVariable int id) {
        CandidateDTO candidateDTO = this.candidateService.getCandidateById(id);
        if (candidateDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidate not found!");
        } else {
            return ResponseEntity.ok(candidateDTO);
        }
    }

    @PostMapping("/create-candidate")
    public ResponseEntity<?> createCandidate(@RequestBody CandidateDTO candidateDTO, @RequestBody MultipartFile fileProgram, @RequestBody MultipartFile imageCandidate) {
        try {
            CandidateDTO createdCandidateDTO = this.candidateService.createCandidate(candidateDTO, fileProgram, imageCandidate);
            return ResponseEntity.ok(createdCandidateDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error add candidate !" + e.getMessage());
        }
    }

    @PutMapping(path = "/update-candidate")
    public ResponseEntity<CandidateDTO> updateCandidate(@RequestBody CandidateDTO candidateDTO) {
        CandidateDTO updatedCandidateDTO = this.candidateService.updateCandidate(candidateDTO);
        return ResponseEntity.ok(updatedCandidateDTO);
    }

    @DeleteMapping(path = "/delete-candidate/{id}")
    public ResponseEntity<String> deleteCandidateById(@PathVariable int id) {
        this.candidateService.deleteCandidate(id);
        return ResponseEntity.ok("Deleted Candidate!");
    }
}
