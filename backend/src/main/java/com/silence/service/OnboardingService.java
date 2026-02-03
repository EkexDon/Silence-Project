package com.silence.service;

import com.silence.domain.UserProfile;
import com.silence.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service to handle the 10-question onboarding process.
 * Defines the atmospheric questions that build the user persona.
 */
@Service
public class OnboardingService {

    private final UserProfileRepository profileRepository;

    public OnboardingService(UserProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    private static final List<String> QUESTIONS = Arrays.asList(
            "What is the color of your silence?",
            "Do you observe the world, or does the world observe you?",
            "If your thoughts were an industrial landscape, what would they look like?",
            "What is the one thing you are most afraid of becoming?",
            "When was the last time you felt truly authentic, not performing?",
            "Do you find beauty in decay or in structure?",
            "What is the noise you are trying to escape?",
            "If you disappeared tomorrow, what impression would you leave behind?",
            "Is your identity a cage or a canvas?",
            "Write the manifesto of your inner self in one sentence.");

    public List<String> getOnboardingQuestions() {
        return QUESTIONS;
    }

    public UserProfile completeOnboarding(Map<String, String> answers) {
        UserProfile profile = new UserProfile();
        StringBuilder baselineBuilder = new StringBuilder();

        for (int i = 0; i < QUESTIONS.size(); i++) {
            String question = QUESTIONS.get(i);
            String answer = answers.getOrDefault(String.valueOf(i), "...");
            profile.addAnswer(question, answer);
            baselineBuilder.append(answer).append(" ");
        }

        profile.setBaselinePersona(baselineBuilder.toString().trim());
        return profileRepository.save(profile);
    }

    public boolean isProfileComplete() {
        return profileRepository.count() > 0;
    }
}
