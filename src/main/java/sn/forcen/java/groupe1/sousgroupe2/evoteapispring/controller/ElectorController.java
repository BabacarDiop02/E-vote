package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.ElectorDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service.ElectorService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@CrossOrigin("*")
public class ElectorController {
    private final ElectorService electorService;

    public ElectorController(ElectorService electorService) {
        this.electorService = electorService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR')")
    @GetMapping(path = "/electors")
    public ResponseEntity<Set<ElectorDTO>> getAllElector() {
        Set<ElectorDTO> electorsDTO = this.electorService.getAllElector();
        return ResponseEntity.ok(electorsDTO);
    }

    @GetMapping("/elector/{id}")
    public ResponseEntity<?> getElectorById(@PathVariable int id) {
        ElectorDTO electorDTO = this.electorService.getElectorById(id);
        if (electorDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Elector not found!");
        } else {
            return ResponseEntity.ok(electorDTO);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    @PostMapping(path = "/create-elector")
    public ResponseEntity<ElectorDTO> createElector(@RequestBody ElectorDTO electorDTO) {
        ElectorDTO newelectorDTO = this.electorService.addElector(electorDTO);
        return ResponseEntity.ok(newelectorDTO);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    @PutMapping(path = "/update-elector")
    public ResponseEntity<ElectorDTO> updateElector(@RequestBody ElectorDTO electorDTO) {
        ElectorDTO electorDTOUpdated = this.electorService.updateElector(electorDTO);
        return ResponseEntity.ok(electorDTOUpdated);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    @PostMapping("/import-electors")
    public ResponseEntity<Map<String, Object>> importElectors(@RequestParam(name = "file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.electorService.addElectorFromExcel(file);
            log.info("Electors added successfully");

            response.put("success", true);
            response.put("message", "Electors added successfully");

            return ResponseEntity.ok(response);  // ✅ Retourne HTTP 200 avec JSON
        } catch (IOException e) {
            log.error("Electors not added successfully", e);

            response.put("success", false);
            response.put("message", "Error while importing: " + e.getMessage());

            return ResponseEntity.badRequest().body(response);  // ❌ Retourne HTTP 400
        }
    }


    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/delete-elector/{id}")
    public ResponseEntity<String> deleteElectorById(@PathVariable int id) {
        this.electorService.deleteElectorById(id);
        return ResponseEntity.ok("Elector deleted successfully");
    }
}
