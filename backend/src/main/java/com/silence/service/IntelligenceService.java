package com.silence.service;

import com.silence.domain.Entry;
import com.silence.domain.UserProfile;
import com.silence.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * The "Silent Observer" Intelligence Service.
 * Analyzes journal entries against the user profile to produce subtle hints.
 */
@Service
public class IntelligenceService {

    private final UserProfileRepository profileRepository;
    private final Random random = new Random();

    public IntelligenceService(UserProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * MOCK AI Logic: Analyzes content and generates a subtle "Silent Observer"
     * hint.
     * In a production scenario, this would call an LLM (Gemini/OpenAI).
     */
    public void analyzeAndEnrich(Entry entry) {
        List<UserProfile> profiles = profileRepository.findAll();
        if (profiles.isEmpty())
            return;

        UserProfile profile = profiles.get(0);
        String content = entry.getContent().toLowerCase();

        // Use the baseline persona to influence the mood slightly
        // For example, if the user mentioned "industrial" in their persona,
        // they might have a higher resilience score.
        boolean isIndustrialPersona = profile.getBaselinePersona().toLowerCase().contains("industrial");

        // Simple heuristic: Keyword-based mood detection
        int score = isIndustrialPersona ? 60 : 50;
        if (content.contains("sad") || content.contains("alone") || content.contains("dark"))
            score -= 20;
        if (content.contains("happy") || content.contains("light") || content.contains("clear"))
            score += 20;

        entry.setMoodScore(score);

        // Generate a subtle hint matching the Brutalist aesthetic
        entry.setAiFeedback(generateHint(score, content));
    }

    /**
     * Generates a "Reflection" of the user's journey based on all entries.
     * Heuristic: Compares the average mood of the first 3 entries vs last 3.
     */
    public String generateJourneySummary(List<Entry> entries) {
        if (entries.isEmpty())
            return "The silence is uninhabited.";
        if (entries.size() < 3)
            return "The record is still too thin to reflect upon. Keep writing.";

        int firstMood = (int) entries.stream().limit(3).map(Entry::getMoodScore).filter(Objects::nonNull)
                .mapToInt(Integer::intValue).average().orElse(50.0);
        int lastMood = (int) entries.stream().skip(Math.max(0, entries.size() - 3)).map(Entry::getMoodScore)
                .filter(Objects::nonNull).mapToInt(Integer::intValue).average().orElse(50.0);

        if (lastMood > firstMood + 10) {
            return "Your trajectory is ascending. The industrial fog is lifting, revealing structures of clarity.";
        } else if (lastMood < firstMood - 10) {
            return "The silence weighs heavier now. You are sinking deeper into the observation. Don't lose the light.";
        } else {
            return "A state of equilibrium has been reached. You are the steady pulse in the machine.";
        }
    }

    private String generateHint(int score, String content) {
        String[] calmHints = {
                "The structure holds.",
                "Clarity is a temporary state.",
                "Silence is the only truth."
        };
        String[] turbulentHints = {
                "The walls are breathing.",
                "Echoes of previous versions persist.",
                "Action is the antidote to noise."
        };

        if (score < 50) {
            return turbulentHints[random.nextInt(turbulentHints.length)];
        } else {
            return calmHints[random.nextInt(calmHints.length)];
        }
    }
}
