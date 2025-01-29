package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class CandidateDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String part;
    private String programNameFile;
}