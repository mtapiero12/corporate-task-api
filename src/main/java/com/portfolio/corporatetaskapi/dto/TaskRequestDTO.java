package com.portfolio.corporatetaskapi.dto;

import com.portfolio.corporatetaskapi.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskRequestDTO {
    @NotBlank(message = "Task title is required")
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private Long assignedUserId;
}
