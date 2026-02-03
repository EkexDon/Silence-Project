package com.silence.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores the core identity and persona of the user.
 * Created during the 10-question onboarding phase.
 */
@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A concatenated or summarized version of the onboarding answers.
     * Used by the AI as the baseline persona.
     */
    @Column(columnDefinition = "TEXT")
    private String baselinePersona;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_profile_id")
    private List<OnboardingAnswer> onboardingAnswers = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    public UserProfile() {
    }

    public void addAnswer(String question, String answer) {
        this.onboardingAnswers.add(new OnboardingAnswer(question, answer));
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaselinePersona() {
        return baselinePersona;
    }

    public void setBaselinePersona(String baselinePersona) {
        this.baselinePersona = baselinePersona;
    }

    public List<OnboardingAnswer> getOnboardingAnswers() {
        return onboardingAnswers;
    }

    public void setOnboardingAnswers(List<OnboardingAnswer> onboardingAnswers) {
        this.onboardingAnswers = onboardingAnswers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
