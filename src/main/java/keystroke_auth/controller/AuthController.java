package keystroke_auth.controller;

import keystroke_auth.model.AuthRequest;
import keystroke_auth.model.UserProfile;
import keystroke_auth.repository.UserRepository;
import keystroke_auth.service.BiometricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BiometricService biometricService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody AuthRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            response.put("success", false);
            response.put("message", "Username already exists.");
            return ResponseEntity.badRequest().body(response);
        }

        UserProfile user = new UserProfile(request.getUsername(), request.getPassword(), request.getTimings());
        userRepository.save(user);

        response.put("success", true);
        response.put("message", "User registered and biometric baseline enrolled successfully!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest request) {
        Map<String, Object> response = new HashMap<>();

        Optional<UserProfile> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        UserProfile user = userOpt.get();

        if (!user.getPassword().equals(request.getPassword())) {
            response.put("success", false);
            response.put("message", "Invalid password credentials.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        double distance = biometricService.calculateEuclideanDistance(user.getBaselineTimings(), request.getTimings());
        boolean isAuthentic = biometricService.isGenuineTypist(user.getBaselineTimings(), request.getTimings());

        response.put("euclideanDistance", Math.round(distance * 100.0) / 100.0);

        if (isAuthentic) {
            response.put("success", true);
            response.put("message", "Biometric match confirmed. Welcome back!");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Imposter Alert! Correct password, but typing rhythm failed verification.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
}