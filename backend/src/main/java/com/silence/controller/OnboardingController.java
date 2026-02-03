package com.silence.controller;

import com.silence.service.OnboardingService;
import com.silence.domain.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for the Onboarding phase.
 */
@RestController
@RequestMapping("/api/v1/onboarding")
@CrossOrigin(origins = "http://localhost:3000")
public class OnboardingController {

    private final OnboardingService onboardingService;

    public OnboardingController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @GetMapping("/questions")
    public List<String> getQuestions() {
        return onboardingService.getOnboardingQuestions();
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitOnboarding(@RequestBody Map<String, String> answers) {
        if (answers.size() < 10) {
            return ResponseEntity.badRequest().body("All 10 questions must be answered.");
        }
        UserProfile profile = onboardingService.completeOnboarding(answers);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> isComplete() {
        return ResponseEntity.ok(onboardingService.isProfileComplete());
    }
}
