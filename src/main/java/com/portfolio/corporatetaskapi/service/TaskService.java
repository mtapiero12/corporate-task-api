package com.portfolio.corporatetaskapi.service;

import com.portfolio.corporatetaskapi.dto.TaskRequestDTO;
import com.portfolio.corporatetaskapi.dto.TaskResponseDTO;
import com.portfolio.corporatetaskapi.exception.ResourceNotFoundException;
import com.portfolio.corporatetaskapi.model.Task;
import com.portfolio.corporatetaskapi.model.TaskStatus;
import com.portfolio.corporatetaskapi.model.User;
import com.portfolio.corporatetaskapi.repository.TaskRepository;
import com.portfolio.corporatetaskapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return mapToDTO(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO request) {
        User assignedUser = null;
        if (request.getAssignedUserId() != null) {
            assignedUser = userRepository.findById(request.getAssignedUserId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Assigned user not found with id: " + request.getAssignedUserId()));
        }
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.PENDING)
                .dueDate(request.getDueDate())
                .assignedUser(assignedUser)
                .build();
        return mapToDTO(taskRepository.save(task));
    }

    @Transactional
    public TaskResponseDTO updateTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setStatus(status);
        return mapToDTO(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        taskRepository.delete(task);
    }

    private TaskResponseDTO mapToDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .assignedUserId(task.getAssignedUser() != null ? task.getAssignedUser().getId() : null)
                .assignedUserName(task.getAssignedUser() != null ? task.getAssignedUser().getFullName() : "Unassigned")
                .build();
    }
}
