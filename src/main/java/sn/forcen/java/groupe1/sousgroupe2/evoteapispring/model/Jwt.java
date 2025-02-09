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
import lombok.ToString;

@Entity
@Table(name = "java_web_token")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class Jwt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean disable;
    private boolean expire;
    private String token;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    private User user;
}
