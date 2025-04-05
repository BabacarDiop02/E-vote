package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.AuthenticationDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.UserDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.security.JwtUtil;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service.UserService;

import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping(path = "/registration")
    public UserDTO registration(@RequestBody UserDTO userDTO) {
        log.info("registration");
        return this.userService.registerUser(userDTO);
    }

    @PostMapping(path = "/activation")
    public void activation(@RequestBody Map<String, String> activation) {
        this.userService.activation(activation);
    }

    @PostMapping(path = "/connection")
    public String connection(@RequestBody AuthenticationDTO authenticationDTO) {
        final Authentication authenticate = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
        );

        if (authenticate.isAuthenticated()) return this.jwtUtil.generateToken(authenticationDTO.username());
        return null;
    }
}
