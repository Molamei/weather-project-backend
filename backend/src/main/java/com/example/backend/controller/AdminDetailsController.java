package com.example.backend.controller;

import com.example.backend.dto.EditeUserDetailsDTO;
import com.example.backend.dto.UserInfoDTO;
import com.example.backend.service.UserAndAdminDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminDetailsController {

    private final UserAndAdminDetailsService userAndAdminDetailsService;
    @GetMapping("/admin/profile/{adminId}")
    public ResponseEntity<UserInfoDTO> getAdminDetails(@PathVariable Integer adminId) {
        return ResponseEntity.ok(userAndAdminDetailsService.viewAdminDetails(adminId));
    }

    @PutMapping("/admin/profile/{adminId}")
    public ResponseEntity<EditeUserDetailsDTO> updateAdminDetails(
            @PathVariable Integer adminId, @RequestBody EditeUserDetailsDTO adminDetails) {
        return ResponseEntity.ok(userAndAdminDetailsService.updateAdminDetails( adminDetails,adminId));
    }
}
