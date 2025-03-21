package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "candidats")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class Candidate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidat_id")
    private int id;

    @Column(name = "prenom", nullable = false)
    private String firstName;

    @Column(name = "nom", nullable = false)
    private String lastName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "profession", nullable = false)
    private String occupation;

    @Lob
    @Column(name = "portrait", columnDefinition = "TEXT")
    private String portrait;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> vote;

    @Builder.Default
    @Column(name = "programme")
    private String programNameFile = "";

    @Builder.Default
    @Column(name = "profile")
    private String profileNameImage = "";

    @Builder.Default
    @Column(name = "activer")
    private boolean enabled = true;
}
