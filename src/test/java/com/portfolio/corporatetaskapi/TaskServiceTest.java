package com.portfolio.corporatetaskapi;

import com.portfolio.corporatetaskapi.dto.TaskRequestDTO;
import com.portfolio.corporatetaskapi.dto.TaskResponseDTO;
import com.portfolio.corporatetaskapi.exception.ResourceNotFoundException;
import com.portfolio.corporatetaskapi.model.Task;
import com.portfolio.corporatetaskapi.model.TaskStatus;
import com.portfolio.corporatetaskapi.model.User;
import com.portfolio.corporatetaskapi.repository.TaskRepository;
import com.portfolio.corporatetaskapi.repository.UserRepository;
import com.portfolio.corporatetaskapi.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_ShouldAssignUserAndReturnDTO() {
        User user = User.builder()
                .id(1L)
                .fullName("Carlos Mendoza")
                .email("carlos@company.com")
                .role("Backend Developer")
                .build();

        TaskRequestDTO request = new TaskRequestDTO();
        request.setTitle("Implement Spring Security");
        request.setDescription("Configure JWT authentication");
        request.setDueDate(LocalDate.now().plusDays(5));
        request.setAssignedUserId(1L);

        Task savedTask = Task.builder()
                .id(10L)
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TaskStatus.PENDING)
                .dueDate(request.getDueDate())
                .assignedUser(user)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponseDTO response = taskService.createTask(request);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("Carlos Mendoza", response.getAssignedUserName());
        assertEquals(TaskStatus.PENDING, response.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getTaskById_WhenNotFound_ShouldThrowException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(99L));
    }
}
