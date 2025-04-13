package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service.ElectionService;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class ElectionController {
    private final ElectionService electionService;
}
