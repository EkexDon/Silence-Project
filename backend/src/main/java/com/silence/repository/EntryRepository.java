package com.silence.repository;

import com.silence.domain.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Data Access Layer for the Entry entity.
 * Handles persistence and date-based querying.
 */
@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

    /**
     * Find an entry by its specific calendar date.
     * 
     * @param entryDate The date to search for.
     * @return An Optional containing the entry if found.
     */
    Optional<Entry> findByEntryDate(LocalDate entryDate);

    /**
     * Check if an entry already exists for a specific date.
     * 
     * @param entryDate The date to check.
     * @return true if an entry exists, false otherwise.
     */
    boolean existsByEntryDate(LocalDate entryDate);
}
