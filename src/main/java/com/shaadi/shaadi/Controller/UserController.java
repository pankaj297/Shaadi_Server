package com.shaadi.shaadi.Controller;

import com.shaadi.shaadi.Model.User;
import com.shaadi.shaadi.Services.CloudinaryService;
import com.shaadi.shaadi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = { "http://localhost:5173", "https://banjaramelava.netlify.app",
        "https://your-frontend.onrender.com" })
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @ModelAttribute User user,
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            @RequestParam(value = "aadhaar", required = false) MultipartFile aadhaar) {
        try {
            // Profile Photo
            if (profilePhoto != null && !profilePhoto.isEmpty()) {
                if (!profilePhoto.getContentType().startsWith("image/")) {
                    return ResponseEntity.badRequest().body("Only images allowed for Profile Photo");
                }
                String profileUrl = cloudinaryService.uploadFile(profilePhoto);
                user.setProfilePhotoPath(profileUrl);
            }

            // Aadhaar
            if (aadhaar != null && !aadhaar.isEmpty()) {
                String aadhaarUrl = cloudinaryService.uploadFile(aadhaar);
                user.setAadhaarPath(aadhaarUrl);
            }

            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error registering user: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error fetching users: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            Optional<User> optionalUser = userService.getUserById(id);
            if (optionalUser.isPresent()) {
                return ResponseEntity.ok(optionalUser.get());
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "User not found with id: " + id);
                return ResponseEntity.status(404).body(errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error fetching user: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/abc/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");

        if ("admin".equals(username) && "admin123".equals(password)) {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Login successful");
            return ResponseEntity.ok(successResponse);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid credentials");
            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @ModelAttribute User user,
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            @RequestParam(value = "aadhaar", required = false) MultipartFile aadhaar) {
        try {
            User existingUser = userService.getUserById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Update fields
            existingUser.setName(user.getName());
            existingUser.setGender(user.getGender());
            existingUser.setDob(user.getDob());
            existingUser.setBirthplace(user.getBirthplace());
            existingUser.setKuldevat(user.getKuldevat());
            existingUser.setGotra(user.getGotra());
            existingUser.setHeight(user.getHeight());
            existingUser.setBloodGroup(user.getBloodGroup());
            existingUser.setEducation(user.getEducation());
            existingUser.setProfession(user.getProfession());
            existingUser.setFatherName(user.getFatherName());
            existingUser.setFatherProfession(user.getFatherProfession());
            existingUser.setMotherName(user.getMotherName());
            existingUser.setMotherProfession(user.getMotherProfession());
            existingUser.setSiblings(user.getSiblings());
            existingUser.setMama(user.getMama());
            existingUser.setKaka(user.getKaka());
            existingUser.setAddress(user.getAddress());
            existingUser.setMobile(user.getMobile());

            // Profile Photo upload
            if (profilePhoto != null && !profilePhoto.isEmpty()) {
                if (!profilePhoto.getContentType().startsWith("image/")) {
                    return ResponseEntity.badRequest().body("Only image files allowed for Profile Photo");
                }
                String profileUrl = cloudinaryService.uploadFile(profilePhoto);
                existingUser.setProfilePhotoPath(profileUrl);
            }

            // Aadhaar upload
            if (aadhaar != null && !aadhaar.isEmpty()) {
                String aadhaarUrl = cloudinaryService.uploadFile(aadhaar);
                existingUser.setAadhaarPath(aadhaarUrl);
            }

            User updatedUser = userService.saveUser(existingUser);
            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error updating user: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "User deleted successfully!");
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error deleting user: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}