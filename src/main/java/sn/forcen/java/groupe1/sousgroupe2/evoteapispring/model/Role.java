package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private UserRole libeller;
}
