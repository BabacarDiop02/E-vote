package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Elector;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service.ElectorService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/e-vote/elector")
@CrossOrigin("*")
public class ElectorController {
    private final ElectorService electorService;

    public ElectorController(ElectorService electorService) {
        this.electorService = electorService;
    }

    @GetMapping
    public ResponseEntity<List<Elector>> getAllElector() {
        List<Elector> electors = electorService.getAllElector();
        return ResponseEntity.ok(electors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getElectorById(@PathVariable int id) {
        Elector elector = electorService.getElectorById(id);
        if (elector == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Elector not found!");
        } else {
            return ResponseEntity.ok(elector);
        }
    }

    @PostMapping
    public ResponseEntity<Elector> createElector(@RequestBody Elector elector) {
        Elector newelector = electorService.addElector(elector);
        return ResponseEntity.ok(newelector);
    }

    @PutMapping
    public ResponseEntity<Elector> updateElector(@RequestBody Elector elector) {
        Elector electorUpdated = electorService.updateElector(elector);
        return ResponseEntity.ok(electorUpdated);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importElectors(@RequestParam("file") MultipartFile file) {
        try {
            electorService.addElectorFromExcel(file);
            return ResponseEntity.ok("Electors added successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error while importing" + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteElector(@PathVariable int id) {
        electorService.deleteElectorById(id);
        return ResponseEntity.ok("Elector deleted successfully");
    }
}
