package org.example.repository;


import org.example.entity.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, Long> {

    public ProcessedEvent findByEventId(String eventId);

    @Query("SELECT p FROM ProcessedEvent p WHERE p.processedAt < :cutoffDate")
    List<ProcessedEvent> findOldEvents(@Param("cutoffDate") LocalDateTime cutoffDate);

    void deleteByProcessedAtBefore(LocalDateTime cutoffDate);
}
