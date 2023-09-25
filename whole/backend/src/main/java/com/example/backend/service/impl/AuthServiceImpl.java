package com.example.backend.service.impl;

import com.example.backend.dto.request.LoginRequestDTO;
import com.example.backend.dto.request.RegisterUserDTO;
import com.example.backend.dto.response.AuthenticationResponseDTO;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.model.entity.ERole;
import com.example.backend.model.entity.User;
import com.example.backend.model.repo.UserRepo;
import com.example.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    // returning a new token after signing up
    public AuthenticationResponseDTO register(RegisterUserDTO registerUserDTO) {
        var user = new User();
        if (userRepo.findByEmail(registerUserDTO.getEmail()).isPresent())
            throw new UserAlreadyExistException("Email already exist");
        else {
            user.setName(registerUserDTO.getName());
            user.setEmail(registerUserDTO.getEmail());
            user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
            user.setPhone(registerUserDTO.getPhone());
            user.setRoles(ERole.USER);
            userRepo.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponseDTO.builder()
                    .userId(user.getId())
//                   .role(user.getRoles().name())
                    .token(jwtToken)
                    .build();
        }


    }
//AuthenticationResponse tokenGenration = new AuthenticationResponse();
//        tokenGenration.setToken(jwtToken);
//        return  tokenGenration;


    public AuthenticationResponseDTO login(LoginRequestDTO request) {
        // this authentication manager will do all the job for me encase the user name of password are not corrent
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(request.getEmail() , request.getPassword()));

//        Authentication auth = SecurityContextHolder.getContext().setAuthentication(auth);

        var user = userRepo.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder()
                // just for testing
                .userId(user.getId())
//               .role(user.getRoles().name())
                .token(jwtToken)
                .build();
    }
}
