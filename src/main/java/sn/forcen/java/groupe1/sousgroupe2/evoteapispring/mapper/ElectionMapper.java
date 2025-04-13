package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.ElectionDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Election;

import java.util.stream.Collectors;


@Component
public class ElectionMapper {
    public static ElectionDTO toElectionDTO(Election election) {
        ElectionDTO electionDTO = new ElectionDTO();
        BeanUtils.copyProperties(election, electionDTO);

        electionDTO.setType(election.getType().name());
        electionDTO.setStatus(election.getStatus().name());
        electionDTO.setCandidates(
                election.getCandidates()
                        .stream()
                        .map(candidate -> candidate.getFirstName() + ' ' + candidate.getLastName())
                        .collect(Collectors.toList())
        );
        electionDTO.setAllVotes(election.getVotes().size());
        return electionDTO;
    }

    public Election toElection(ElectionDTO electionDTO) {
        Election election = new Election();
        BeanUtils.copyProperties(electionDTO, election);
        return election;
    }
}
