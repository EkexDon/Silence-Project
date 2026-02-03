package com.silence.controller;

import com.silence.domain.Entry;
import com.silence.service.EntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Managing Journal Entries.
 * Exposes the API endpoints used by the Next.js frontend.
 */
@RestController
@RequestMapping("/api/v1/entries")
@CrossOrigin(origins = "http://localhost:3000") // CORS handled for the development frontend
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    /**
     * Fetch all past entries for the history timeline.
     * 
     * @return List of persisted entries.
     */
    @GetMapping
    public List<Entry> getAllEntries() {
        return entryService.getAllEntries();
    }

    /**
     * Submit a new entry.
     * Validates length and "one per day" rule.
     * 
     * @param payload Map containing the "content" string.
     * @return ResponseEntity with the created entry or error message.
     */
    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody Map<String, String> payload) {
        String content = payload.get("content");

        // Manual validation for high-feedback error messages
        if (content == null || content.isBlank()) {
            return ResponseEntity.badRequest().body("Content cannot be empty");
        }
        if (content.length() > 300) {
            return ResponseEntity.badRequest().body("Content cannot exceed 300 characters");
        }

        try {
            Entry entry = entryService.createEntry(content);
            return ResponseEntity.ok(entry);
        } catch (IllegalStateException e) {
            // 409 Conflict if already written today
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    /**
     * Lightweight check to see if the user should be locked/unlocked in the UI.
     * 
     * @return Boolean true if today is complete.
     */
    @GetMapping("/today")
    public ResponseEntity<Boolean> hasEntryForToday() {
        return ResponseEntity.ok(entryService.hasEntryForToday());
    }
}
