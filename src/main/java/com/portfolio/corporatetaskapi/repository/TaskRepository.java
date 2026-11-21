package com.portfolio.corporatetaskapi.repository;

import com.portfolio.corporatetaskapi.model.Task;
import com.portfolio.corporatetaskapi.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    @Query("SELECT t FROM Task t JOIN FETCH t.assignedUser u WHERE u.id = :userId AND t.status = :status")
    List<Task> findTasksByUserAndStatus(@Param("userId") Long userId, @Param("status") TaskStatus status);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignedUser.id = :userId AND t.status = 'COMPLETED'")
    long countCompletedTasksByUser(@Param("userId") Long userId);
}
