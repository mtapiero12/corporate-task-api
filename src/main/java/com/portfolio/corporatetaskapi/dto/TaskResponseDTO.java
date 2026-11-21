package com.portfolio.corporatetaskapi.dto;

import com.portfolio.corporatetaskapi.model.TaskStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private Long assignedUserId;
    private String assignedUserName;
}
