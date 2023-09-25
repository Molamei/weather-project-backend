package com.example.backend.controller;


import com.example.backend.dto.EditeUserDetailsDTO;
import com.example.backend.dto.UserInfoDTO;
import com.example.backend.service.UserAndAdminDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserDetailsController {

    private final UserAndAdminDetailsService userAndAdminDetailsService;
//("/{userId}/details")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserInfoDTO> getUserDetails(@PathVariable Integer userId) {
        return ResponseEntity.ok(userAndAdminDetailsService.viewUserDetails(userId));
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<EditeUserDetailsDTO> updateUserDetails(
            @PathVariable Integer userId, @RequestBody EditeUserDetailsDTO userDetails) {
        return ResponseEntity.ok(userAndAdminDetailsService.updateUserDetails( userDetails,userId));
    }






















}
