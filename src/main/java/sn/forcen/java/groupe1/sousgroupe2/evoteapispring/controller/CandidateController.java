package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.CandidateDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service.CandidateService;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

@RestController
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

    @GetMapping("/files/{subDir}/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String subDir, @PathVariable String fileName) throws IOException, IOException {
        Resource file = this.candidateService.getFile(subDir, fileName);
        // Détecter automatiquement le type MIME du fichier
        String contentType = Files.probeContentType(file.getFile().toPath());

        // Si le type est inconnu, mettre un type par défaut
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Type générique pour les fichiers binaires
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    @PostMapping("/create-candidate")
    public ResponseEntity<?> createCandidate(@RequestPart CandidateDTO candidateDTO, @RequestPart("fileProgram") MultipartFile fileProgram, @RequestPart("imageCandidate") MultipartFile imageCandidate) {
        try {
            CandidateDTO createdCandidateDTO = this.candidateService.createCandidate(candidateDTO, fileProgram, imageCandidate);
            return ResponseEntity.ok(createdCandidateDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error add candidate !" + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    @PutMapping(path = "/update-candidate")
    public ResponseEntity<?> updateCandidate(@RequestPart CandidateDTO candidateDTO, @RequestPart("fileProgram") MultipartFile fileProgram, @RequestPart("imageCandidate") MultipartFile imageCandidate) {
        try {
            CandidateDTO updatedCandidateDTO = this.candidateService.updateCandidate(candidateDTO, fileProgram, imageCandidate);
            return ResponseEntity.ok(updatedCandidateDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error add candidate !" + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    @DeleteMapping(path = "/delete-candidate/{id}")
    public ResponseEntity<String> deleteCandidateById(@PathVariable int id) {
        this.candidateService.deleteCandidate(id);
        return ResponseEntity.ok("Deleted Candidate!");
    }
}
