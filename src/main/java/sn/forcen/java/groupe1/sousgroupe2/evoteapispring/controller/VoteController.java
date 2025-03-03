package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service.VoteService;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PreAuthorize("hasAuthority('ROLE_ELECTOR')")
    @PostMapping("/voter")
    public ResponseEntity<Map<String, String>> recordVote(@RequestParam Integer candidateId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String message = this.voteService.recordVote(candidateId, username);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }
}
