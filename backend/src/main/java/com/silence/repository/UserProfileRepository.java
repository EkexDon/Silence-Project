package com.silence.repository;

import com.silence.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Layer for the UserProfile.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
