package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class UserProfile {
    private String nationalIdentificationNumber;
    private String email;
    private String password;

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
