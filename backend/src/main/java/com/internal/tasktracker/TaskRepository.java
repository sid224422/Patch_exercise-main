package com.internal.tasktracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // AND binds tighter than OR, so the LIKE checks have to be grouped.
    // Otherwise the status filter skips title matches and archived rows can leak in.
    @Query(value = "SELECT * FROM tasks WHERE archived = FALSE "
                 + "AND (LOWER(title) LIKE :term ESCAPE '!' OR LOWER(description) LIKE :term ESCAPE '!') "
                 + "AND (:status IS NULL OR status = :status) "
                 + "ORDER BY created_at DESC",
           nativeQuery = true)
    List<Task> searchTasks(@Param("term") String term, @Param("status") String status);
}
