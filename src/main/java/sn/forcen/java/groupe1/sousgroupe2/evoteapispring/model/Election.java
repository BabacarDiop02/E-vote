package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "elections")
@NoArgsConstructor @AllArgsConstructor @Getter
@Setter
@Builder
public class Election {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ElectionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ElectionStatus status;
}
