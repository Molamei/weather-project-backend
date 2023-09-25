package com.example.backend.service.impl;

import com.example.backend.dto.request.RegisterAdminDTO;
import com.example.backend.exceptions.InternalErrorsException;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.model.entity.ERole;
import com.example.backend.model.entity.User;
import com.example.backend.model.repo.UserRepo;
import com.example.backend.service.SuperAdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<RegisterAdminDTO> getAllAdmins() {
        List<User> allUserAdmins = userRepo.findByRoles(ERole.ADMIN);
        List<RegisterAdminDTO> allAdmins = new ArrayList<>();

        for (User user : allUserAdmins) {
            RegisterAdminDTO adminDTO = new RegisterAdminDTO();
            adminDTO.setAdminId(String.valueOf(user.getId()));
            adminDTO.setName(user.getName());
            adminDTO.setEmail(user.getEmail());
            adminDTO.setPassword(user.getPassword());
            adminDTO.setPhone(user.getPhone());
            allAdmins.add(adminDTO);
        }
        return allAdmins;
    }


    @Override
    public RegisterAdminDTO addAdmin(RegisterAdminDTO adminUser) {
        var userAdmin = new User();
        if (userRepo.findByEmail(adminUser.getEmail()).isPresent())
            throw new UserAlreadyExistException("Email already exsist");
        else {

            userAdmin.setName(adminUser.getName());
            userAdmin.setEmail(adminUser.getEmail());
            userAdmin.setPassword(passwordEncoder.encode(adminUser.getPassword()));
            userAdmin.setPhone(adminUser.getPhone());
            userAdmin.setRoles(ERole.ADMIN);
            userRepo.save(userAdmin);

            var addAdmin = new RegisterAdminDTO();
            addAdmin.setAdminId(String.valueOf(userAdmin.getId()));
            addAdmin.setName(userAdmin.getName());
            addAdmin.setEmail(userAdmin.getEmail());
            addAdmin.setPhone(adminUser.getPhone());
            addAdmin.setPassword(passwordEncoder.encode(adminUser.getPassword()));
            return addAdmin;
        }


    }

    @Override
    public RegisterAdminDTO updateAdmin(int userAdminId, RegisterAdminDTO adminUser) {
        Optional<User> getAdUser = userRepo.findById(userAdminId);
        if (getAdUser.isPresent()) {
            getAdUser.get().setName(adminUser.getName());
            getAdUser.get().setEmail(adminUser.getEmail());
            getAdUser.get().setPassword(passwordEncoder.encode(adminUser.getPassword()));
            getAdUser.get().setRoles(ERole.ADMIN);
            userRepo.save(getAdUser.get());


            var updatedAd = new RegisterAdminDTO();

            updatedAd.setAdminId(String.valueOf(getAdUser.get().getId()));
            updatedAd.setName(adminUser.getName());
            updatedAd.setEmail(adminUser.getEmail());
           updatedAd.setPhone(adminUser.getPhone());
            updatedAd.setPassword(passwordEncoder.encode(adminUser.getPassword()));

            return updatedAd;
        }
        throw new UserNotFoundException("User id"+userAdminId+" not found");
    }

    @Override
    public void deleteAdmin(Integer userAdminId) {
        Optional<User> deletedAdUser = userRepo.findById(userAdminId);
        if (deletedAdUser.isPresent()) {
            if (deletedAdUser.get().getRoles().name().equals(ERole.ADMIN.name())) {
                userRepo.delete(deletedAdUser.get());
            } else
                throw new UserNotFoundException("Id " + userAdminId + "its not an id of admin ");
        } else
            throw new UserNotFoundException(" there is no admin of id = " + userAdminId);
    }
}
