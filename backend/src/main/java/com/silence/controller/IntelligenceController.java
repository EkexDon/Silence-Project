package com.silence.controller;

import com.silence.domain.Entry;
import com.silence.service.EntryService;
import com.silence.service.IntelligenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Controller for AI-driven insights and reflections.
 */
@RestController
@RequestMapping("/api/v1/intelligence")
@CrossOrigin(origins = "http://localhost:3000")
public class IntelligenceController {

    private final IntelligenceService intelligenceService;
    private final EntryService entryService;

    public IntelligenceController(IntelligenceService intelligenceService, EntryService entryService) {
        this.intelligenceService = intelligenceService;
        this.entryService = entryService;
    }

    /**
     * Generates a summary of the user's journey.
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, String>> getJourneySummary() {
        List<Entry> entries = entryService.getAllEntries();
        String summary = intelligenceService.generateJourneySummary(entries);
        return ResponseEntity.ok(Map.of("summary", summary));
    }
}
