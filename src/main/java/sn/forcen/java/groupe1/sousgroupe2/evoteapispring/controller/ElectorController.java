package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.ElectorDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service.ElectorService;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping(path = "api/e-vote")
@CrossOrigin("*")
public class ElectorController {
    private final ElectorService electorService;

    public ElectorController(ElectorService electorService) {
        this.electorService = electorService;
    }

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

    @PostMapping(path = "/create-elector")
    public ResponseEntity<ElectorDTO> createElector(@RequestBody ElectorDTO electorDTO) {
        ElectorDTO newelectorDTO = this.electorService.addElector(electorDTO);
        return ResponseEntity.ok(newelectorDTO);
    }

    @PutMapping(path = "/update-elector")
    public ResponseEntity<ElectorDTO> updateElector(@RequestBody ElectorDTO electorDTO) {
        ElectorDTO electorDTOUpdated = this.electorService.updateElector(electorDTO);
        return ResponseEntity.ok(electorDTOUpdated);
    }

    @PostMapping("/import-electors")
    public ResponseEntity<String> importElectors(@RequestBody MultipartFile file) {
        try {
            this.electorService.addElectorFromExcel(file);
            return ResponseEntity.ok("Electors added successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error while importing" + e.getMessage());
        }
    }

    @DeleteMapping("/delete-elector/{id}")
    public ResponseEntity<String> deleteElectorById(@PathVariable int id) {
        this.electorService.deleteElectorById(id);
        return ResponseEntity.ok("Elector deleted successfully");
    }
}
