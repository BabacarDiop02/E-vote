package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "electeurs")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class Elector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "elector_id")
    private Long id;

    @Column(name = "numero_identification_national", nullable = false, unique = true)
    private String nationalIdentificationNumber;

    // État civil
    @Column(name = "prenom", nullable = false)
    private String firstName;

    @Column(name = "nom", nullable = false)
    private String lastName;

    @Column(name = "date_de_naissance", nullable = false)
    private Date dateOfBirth;

    @Column(name = "lieu_de_naissance", nullable = false)
    private String placeOfBirth;

    // Données éléctoral

    @Column(name = "numero_electeur", nullable = false, unique = true)
    private String voterNumber;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "departement", nullable = false)
    private String department;

    @Column(name = "commun", nullable = false)
    private String town;

    @Column(name = "lieu_de_vote", nullable = false)
    private String votingPlace;

    @Column(name = "bureau_de_vote", nullable = false)
    private Long pollingStation;

    @Builder.Default
    @Column(name = "activer")
    private boolean enabled = true;
}
