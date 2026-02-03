package com.silence.service;

import com.silence.domain.Entry;
import com.silence.repository.EntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for managing journal entries.
 * Enforces the core business rules of the "SILENCE" philosophy.
 */
@Service
public class EntryService {

    private final EntryRepository entryRepository;
    private final OnboardingService onboardingService;
    private final IntelligenceService intelligenceService;

    public EntryService(EntryRepository entryRepository, OnboardingService onboardingService,
            IntelligenceService intelligenceService) {
        this.entryRepository = entryRepository;
        this.onboardingService = onboardingService;
        this.intelligenceService = intelligenceService;
    }

    /**
     * Create a new journal entry.
     * Rule: Only one entry allowed per user per day.
     * AI: Performs a "Silent Observation" before saving.
     * 
     * @param content The journal text.
     * @return The persisted Entry object.
     * @throws IllegalStateException if an entry already exists for today or
     *                               onboarding is incomplete.
     */
    public Entry createEntry(String content) {
        if (!onboardingService.isProfileComplete()) {
            throw new IllegalStateException("You must complete the manifesto (onboarding) first.");
        }

        LocalDate today = LocalDate.now();
        if (entryRepository.existsByEntryDate(today)) {
            throw new IllegalStateException("You have already written your entry for today.");
        }

        Entry entry = new Entry(content);

        // The Silent Observer analyzes the entry before it is written to the void.
        intelligenceService.analyzeAndEnrich(entry);

        return entryRepository.save(entry);
    }

    /**
     * Retrieves all recorded entries for the history feed.
     * 
     * @return List of entries.
     */
    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    /**
     * Helper to check the state for today (used for UI locking).
     * 
     * @return true if today's entry is already submitted.
     */
    public boolean hasEntryForToday() {
        return entryRepository.existsByEntryDate(LocalDate.now());
    }
}
