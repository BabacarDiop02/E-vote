package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String role;
    private String nationalIdentificationNumber;
    private String email;
    private String password;
    private boolean enabled;
}
