package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "validation")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Instant creation;
    private Instant expiration;
    private Instant activation;
    private String code;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    private User user;
}
