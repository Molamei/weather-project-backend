package com.example.backend.service.impl;

import com.example.backend.dto.EditeUserDetailsDTO;
import com.example.backend.dto.UserInfoDTO;
import com.example.backend.exceptions.InternalErrorsException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.model.entity.ERole;
import com.example.backend.model.entity.User;
import com.example.backend.model.repo.UserRepo;
import com.example.backend.service.UserAndAdminDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAndAdminAndAdminDetailsServiceImpl implements UserAndAdminDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserInfoDTO viewUserDetails(Integer userId) {
        UserInfoDTO userInfo = new UserInfoDTO();
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            if (user.get().getRoles().name().equals(ERole.USER.name()) ||
                    user.get().getRoles().name().equals(ERole.ADMIN.name())) {
                userInfo.setName(user.get().getName());
                userInfo.setEmail(user.get().getEmail());
                userInfo.setPassword(user.get().getPassword());
                userInfo.setPhone(user.get().getPhone());
                return userInfo;
            } else
                throw new UserNotFoundException("Id " + userId + " its not an id of yours");
        } else
            throw new UserNotFoundException("Cannot find the user id " + userId);
    }

    @Override
    public EditeUserDetailsDTO updateUserDetails(EditeUserDetailsDTO userDetailsDTO, Integer userId) {
        EditeUserDetailsDTO updatedUserDetails = new EditeUserDetailsDTO();
        Optional<User> updatedUser = userRepo.findById(userId);
        if (updatedUser.isPresent()) {
            if (updatedUser.get().getRoles().name().equals(ERole.USER.name()) ||
                    updatedUser.get().getRoles().name().equals(ERole.ADMIN.name())) {
                updateOptional(userDetailsDTO, updatedUserDetails, updatedUser);
            } else
                throw new UserNotFoundException("Id " + userId + " its not an id of yours to update"); // It's impossible anyway
        } else
            throw new InternalErrorsException(" unexpected error happened while updating your information ");


        return updatedUserDetails;
    }

    @Override
    public UserInfoDTO viewAdminDetails(Integer userId) {
        UserInfoDTO userInfo = new UserInfoDTO();
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            if (user.get().getRoles().name().equals(ERole.ADMIN.name())) {
                userInfo.setName(user.get().getName());
                userInfo.setEmail(user.get().getEmail());
                userInfo.setPassword(user.get().getPassword());
                userInfo.setPhone(user.get().getPhone());
                return userInfo;
            } else
                throw new UserNotFoundException("Id " + userId + " its not an id of yours");
        } else
            throw new UserNotFoundException("Cannot find admin id " + userId);

    }

    @Override
    public EditeUserDetailsDTO updateAdminDetails(EditeUserDetailsDTO adminDetails, Integer adminId) {
        EditeUserDetailsDTO updateAdminDetails = new EditeUserDetailsDTO();
        Optional<User> updatedAdmin = userRepo.findById(adminId);
        if (updatedAdmin.isPresent()) {
            if (updatedAdmin.get().getRoles().name().equals(ERole.ADMIN.name())) {
                // TODO some validation to username and password front end ez
                updateOptional(adminDetails, updateAdminDetails, updatedAdmin);
            } else
                throw new UserNotFoundException("Id " + adminId + " its not an id of yours to update");
        } else
            throw new InternalErrorsException(" unexpected error happened while updating your information ");
        return updateAdminDetails;
    }

    private void updateOptional(
            EditeUserDetailsDTO optionalDetails,
            EditeUserDetailsDTO updatedOptionalDetails,
            Optional<User> updatedOptional) {
        updatedOptionalDetails.setName(optionalDetails.getName());
        updatedOptionalDetails.setPassword(optionalDetails.getPassword());
        updatedOptionalDetails.setPhone(optionalDetails.getPhone());

        updatedOptional.get().setName(optionalDetails.getName());
        updatedOptional.get().setPassword(passwordEncoder.encode(optionalDetails.getPassword()));
        updatedOptional.get().setPhone(optionalDetails.getPhone());
        userRepo.save(updatedOptional.get());

    }
}
