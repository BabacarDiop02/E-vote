package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class ElectorDTO {
    private int id;
    private String nationalIdentificationNumber;

    // État civil
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String placeOfBirth;

    // Données éléctoral
    private String voterNumber;
    private String region;
    private String department;
    private String borough;
    private String town;
    private String votingPlace;
    private Long pollingStation;
}
