package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "votes")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class Vote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private Candidate candidate;

    @Column(nullable = false, unique = true)
    private String voteHash;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;
}
