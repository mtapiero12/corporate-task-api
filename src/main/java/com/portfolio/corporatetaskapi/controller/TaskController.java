package com.portfolio.corporatetaskapi.controller;

import com.portfolio.corporatetaskapi.dto.TaskRequestDTO;
import com.portfolio.corporatetaskapi.dto.TaskResponseDTO;
import com.portfolio.corporatetaskapi.model.TaskStatus;
import com.portfolio.corporatetaskapi.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Corporate task lifecycle and assignment endpoints")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Retrieve all tasks or filter by status")
    public ResponseEntity<List<TaskResponseDTO>> getTasks(@RequestParam(required = false) TaskStatus status) {
        if (status != null) {
            return ResponseEntity.ok(taskService.getTasksByStatus(status));
        }
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve task details by ID")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    @Operation(summary = "Create and optionally assign a new task")
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update the status of an existing task")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task by ID")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
