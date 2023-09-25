package com.example.backend.controller;


import com.example.backend.dto.EditeUserDetailsDTO;
import com.example.backend.dto.UserInfoDTO;
import com.example.backend.dto.request.RegisterAdminDTO;
import com.example.backend.model.entity.User;
import com.example.backend.service.SuperAdminService;
import com.example.backend.service.UserAndAdminDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather/admins")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    private final SuperAdminService Service;

    @GetMapping("")
    public ResponseEntity<List<RegisterAdminDTO>> getAllAdmins() {
        return ResponseEntity.ok(Service.getAllAdmins());
    }


    @PostMapping("")
    public ResponseEntity<RegisterAdminDTO> addAdmin(@RequestBody RegisterAdminDTO admin) {
        return ResponseEntity.ok(Service.addAdmin(admin));
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<RegisterAdminDTO> updateAdmin(@PathVariable Integer adminId, @RequestBody RegisterAdminDTO admin) {
        return ResponseEntity.ok(Service.updateAdmin(adminId, admin));
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Integer adminId) {
        Service.deleteAdmin(adminId);
        return ResponseEntity.ok("Admin with id " + adminId + " deleted successfully ");
    }

}
