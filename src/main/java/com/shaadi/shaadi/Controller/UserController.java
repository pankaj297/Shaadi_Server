package com.shaadi.shaadi.Controller;

import java.util.Optional;
import com.shaadi.shaadi.Helper.UploadHelper;
import com.shaadi.shaadi.Model.User;
import com.shaadi.shaadi.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UploadHelper uploadHelper;

    // 1️⃣ Register user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @ModelAttribute User user,
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            @RequestParam(value = "aadhaar", required = false) MultipartFile aadhaar) {
        try {
            long uniqueNumber = System.currentTimeMillis();

            // Handle Profile Photo
            if (profilePhoto != null && !profilePhoto.isEmpty()) {
                if (!profilePhoto.getContentType().startsWith("image/")) {
                    return ResponseEntity.badRequest().body("🚫 Only image files allowed for Profile Photo");
                }
                String extension = getExtension(profilePhoto.getOriginalFilename());
                String profileFileName = uniqueNumber + "_profile" + extension;
                uploadHelper.uploadFile(profilePhoto, profileFileName);
                user.setProfilePhotoPath(profileFileName);
            }

            // Handle Aadhaar
            if (aadhaar != null && !aadhaar.isEmpty()) {
                String extension = getExtension(aadhaar.getOriginalFilename());
                String aadhaarFileName = uniqueNumber + "_aadhaar" + extension;
                uploadHelper.uploadFile(aadhaar, aadhaarFileName);
                user.setAadhaarPath(aadhaarFileName);
            }

            User savedUser = userService.saveUser(user);

            // Build URLs for frontend
            String profileUrl = (savedUser.getProfilePhotoPath() != null)
                    ? "http://localhost:8080/uploads/" + savedUser.getProfilePhotoPath()
                    : null;

            String aadhaarUrl = (savedUser.getAadhaarPath() != null)
                    ? "http://localhost:8080/uploads/" + savedUser.getAadhaarPath()
                    : null;

            return ResponseEntity.ok(Map.of(
                    "user", savedUser,
                    "profilePhotoUrl", profileUrl,
                    "aadhaarUrl", aadhaarUrl));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error registering user: " + e.getMessage());
        }
    }

    // 2️⃣ Get all users
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 3️⃣ Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userService.getUserById(id);
        return optionalUser.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("User not found"));
    }

    // 4️⃣ Admin Login
    @PostMapping("/abc/login")
    public ResponseEntity<String> loginAdmin(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");
        if ("admin".equals(username) && "admin123".equals(password)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // 5️⃣ Update user
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @ModelAttribute User user,
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            @RequestParam(value = "aadhaar", required = false) MultipartFile aadhaar) {
        try {
            User existingUser = userService.getUserById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Update simple fields
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

            // Update files
            if (profilePhoto != null && !profilePhoto.isEmpty()) {
                String profileFileName = System.currentTimeMillis() + "_profile_" + profilePhoto.getOriginalFilename();
                uploadHelper.uploadFile(profilePhoto, profileFileName);
                existingUser.setProfilePhotoPath(profileFileName);
            }
            if (aadhaar != null && !aadhaar.isEmpty()) {
                String aadhaarFileName = System.currentTimeMillis() + "_aadhaar_" + aadhaar.getOriginalFilename();
                uploadHelper.uploadFile(aadhaar, aadhaarFileName);
                existingUser.setAadhaarPath(aadhaarFileName);
            }

            User updatedUser = userService.saveUser(existingUser);

            // Build URLs for frontend
            String profileUrl = (updatedUser.getProfilePhotoPath() != null)
                    ? "http://localhost:8080/uploads/" + updatedUser.getProfilePhotoPath()
                    : null;

            String aadhaarUrl = (updatedUser.getAadhaarPath() != null)
                    ? "http://localhost:8080/uploads/" + updatedUser.getAadhaarPath()
                    : null;

            return ResponseEntity.ok(Map.of(
                    "user", updatedUser,
                    "profilePhotoUrl", profileUrl,
                    "aadhaarUrl", aadhaarUrl));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating user: " + e.getMessage());
        }
    }

    // 6️⃣ Delete user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting user: " + e.getMessage());
        }
    }

    // 🔹 Helper method
    private String getExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }
}
