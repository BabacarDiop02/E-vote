package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.UserProfile;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Elector;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.User;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.ElectorRepository;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final ElectorRepository electorRepository;
    private final UserRepository userRepository;

    public UserProfile getUserProfile(String username) {
        UserProfile userProfile = new UserProfile();

        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Elector elector = this.electorRepository.findByNationalIdentificationNumberAndFirstNameAndLastName(user.getNationalIdentificationNumber(), user.getFirstName(), user.getLastName()).orElseThrow(() -> new RuntimeException("Elector not found"));

        userProfile.setNationalIdentificationNumber(elector.getNationalIdentificationNumber());
        userProfile.setEmail(user.getEmail());
        userProfile.setPassword(user.getPassword());
        userProfile.setFirstName(elector.getFirstName());
        userProfile.setLastName(elector.getLastName());
        userProfile.setDateOfBirth(elector.getDateOfBirth());
        userProfile.setPlaceOfBirth(elector.getPlaceOfBirth());
        userProfile.setVoterNumber(elector.getVoterNumber());
        userProfile.setRegion(elector.getRegion());
        userProfile.setDepartment(elector.getDepartment());
        userProfile.setBorough(elector.getBorough());
        userProfile.setTown(elector.getTown());
        userProfile.setVotingPlace(elector.getVotingPlace());
        userProfile.setPollingStation(elector.getPollingStation());
        return userProfile;
    }
}
