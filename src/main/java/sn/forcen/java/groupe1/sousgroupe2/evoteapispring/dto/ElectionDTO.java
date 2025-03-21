package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class ElectionDTO {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String status;
    private int allVotes;
}
