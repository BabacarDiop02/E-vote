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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Elector;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service.ElectorService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/e-vote")
@CrossOrigin("*")
public class ElectorController {
    private final ElectorService electorService;

    public ElectorController(ElectorService electorService) {
        this.electorService = electorService;
    }

    @GetMapping(path = "/electors")
    public ResponseEntity<List<Elector>> getAllElector() {
        List<Elector> electors = this.electorService.getAllElector();
        return ResponseEntity.ok(electors);
    }

    @GetMapping("/elector/{id}")
    public ResponseEntity<?> getElectorById(@PathVariable int id) {
        Elector elector = this.electorService.getElectorById(id);
        if (elector == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Elector not found!");
        } else {
            return ResponseEntity.ok(elector);
        }
    }

    @PostMapping(path = "/create-elector")
    public ResponseEntity<Elector> createElector(@RequestBody Elector elector) {
        Elector newelector = this.electorService.addElector(elector);
        return ResponseEntity.ok(newelector);
    }

    @PutMapping(path = "/update-elector")
    public ResponseEntity<Elector> updateElector(@RequestBody Elector elector) {
        Elector electorUpdated = this.electorService.updateElector(elector);
        return ResponseEntity.ok(electorUpdated);
    }

    @PostMapping("/import-electors")
    public ResponseEntity<String> importElectors(@RequestParam("file") MultipartFile file) {
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
