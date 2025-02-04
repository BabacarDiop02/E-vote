package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.UserDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper.UserMapper;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Role;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.User;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.UserRole;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.ElectorRepository;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ElectorRepository electorRepository;

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
        return this.userMapper.toUserDTO(this.userRepository.save(user));
    }
}
