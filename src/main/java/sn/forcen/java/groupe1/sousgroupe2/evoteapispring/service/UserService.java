package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.UserDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper.UserMapper;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Role;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.User;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.UserRole;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Validation;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.ElectorRepository;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.UserRepository;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ElectorRepository electorRepository;
    private final ValidationService validationService;
    private final UserDetailsService userDetailsService;

    public UserDTO registerUser(UserDTO userDTO) {
        User user = this.userMapper.toUser(userDTO);
        if (this.electorRepository.findByNationalIdentificationNumberAndFirstNameAndLastName(user.getNationalIdentificationNumber(), user.getFirstName(), user.getLastName()).isEmpty()) throw new RuntimeException("Elector not found");
        if (!user.getEmail().contains("@") && !user.getEmail().contains(".")) throw  new RuntimeException("Email invalid");
        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Optional<User> utilisateurOptional = this.userRepository.findByEmail(user.getEmail());
        if (utilisateurOptional.isPresent()) throw new RuntimeException("Votre email est déja utiliser");

        Role roleUser = new Role();
        roleUser.setLibeller(UserRole.ELECTOR);
        user.setRole(roleUser);

        user = this.userRepository.save(user);
        this.validationService.saveValidation(user);
        return this.userMapper.toUserDTO(user);
    }

    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.readAccordingToCode(activation.get("code"));
        if (Instant.now().isAfter(validation.getExpiration())) throw new RuntimeException("Activation expired");
        User user = this.userRepository.findById(validation.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found"));
        validation.setActivation(Instant.now());
        user.setEnabled(true);
    }

    public void updatePassword(Map<String, String> param) {
        User user = (User) this.userDetailsService.loadUserByUsername(param.get("email"));
        if (this.electorRepository.findByNationalIdentificationNumberAndFirstNameAndLastName(param.get("nin"), param.get("first-name"), param.get("last-name")).isPresent()) this.validationService.saveValidation(user);
    }

    public void newPassword(Map<String, String> param) {
        User user = (User) this.userDetailsService.loadUserByUsername(param.get("email"));
        final Validation validation = this.validationService.readAccordingToCode(param.get("code"));

        if (validation.getUser().getEmail().equals(user.getEmail())) {
            String encodedPassword = this.passwordEncoder.encode(param.get("password"));
            user.setPassword(encodedPassword);
            validation.setActivation(Instant.now());
            this.userRepository.save(user);
        }
    }
}
