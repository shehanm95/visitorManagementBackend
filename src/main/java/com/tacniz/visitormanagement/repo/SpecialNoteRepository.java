package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.SpecialNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpecialNoteRepository extends JpaRepository<SpecialNote, Long> {

    // Find notes by service point
    List<SpecialNote> findByServicePointId(Long servicePointId);

    // Find notes by officer (creator)
    List<SpecialNote> findByOfficerId(Long officerId);

    // Find notes by visit
    List<SpecialNote> findByVisitId(Long visitId);

    // Find notes created between dates
    List<SpecialNote> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);

    // Find notes reviewed by specific officer
    @Query("SELECT sn FROM SpecialNote sn JOIN sn.reviewedBy rb WHERE rb.id = :officerId")
    List<SpecialNote> findNotesReviewedByOfficer(@Param("officerId") Long officerId);

    // Find notes by content keyword search
    @Query("SELECT sn FROM SpecialNote sn WHERE LOWER(sn.noteContent) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<SpecialNote> searchByContent(@Param("keyword") String keyword);

    // Find recent notes with pagination
    @Query("SELECT sn FROM SpecialNote sn ORDER BY sn.dateTime DESC")
    List<SpecialNote> findRecentNotes();

    // Count notes by service point
    long countByServicePointId(Long servicePointId);
}
