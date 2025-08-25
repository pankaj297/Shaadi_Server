package com.shaadi.shaadi.Controller;

import java.util.Optional;
import com.shaadi.shaadi.Model.User;
import com.shaadi.shaadi.Services.CloudinaryService;
import com.shaadi.shaadi.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
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
                    return ResponseEntity.badRequest().body("🚫 Only images allowed for Profile Photo");
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

            // Return Cloudinary URLs
            return ResponseEntity.ok(Map.of(
                    "user", savedUser,
                    "profilePhotoUrl", savedUser.getProfilePhotoPath(),
                    "aadhaarUrl", savedUser.getAadhaarPath()));

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
                    return ResponseEntity.badRequest().body("🚫 Only image files allowed for Profile Photo");
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

            // Return Cloudinary URLs directly
            return ResponseEntity.ok(Map.of(
                    "user", updatedUser,
                    "profilePhotoUrl", updatedUser.getProfilePhotoPath(),
                    "aadhaarUrl", updatedUser.getAadhaarPath()));

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

}
