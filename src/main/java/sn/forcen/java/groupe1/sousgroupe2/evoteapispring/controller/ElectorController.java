package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.ElectorDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service.ElectorService;

import java.io.IOException;
import java.util.Set;

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
    public ResponseEntity<String> importElectors(@RequestParam(name = "file") MultipartFile file) {
        try {
            this.electorService.addElectorFromExcel(file);
            return ResponseEntity.ok("Electors added successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error while importing" + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/delete-elector/{id}")
    public ResponseEntity<String> deleteElectorById(@PathVariable int id) {
        this.electorService.deleteElectorById(id);
        return ResponseEntity.ok("Elector deleted successfully");
    }
}
