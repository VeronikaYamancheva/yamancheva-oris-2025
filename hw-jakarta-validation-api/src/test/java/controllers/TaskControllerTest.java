package controllers;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.itis.vhsroni.controllers.TaskController;
import ru.itis.vhsroni.dto.TaskDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    private static final String TITLE_CASE = "TITLE";

    private static final String DESC_CASE = "DESC";

    private final TaskController controller = new TaskController();

    @Test
    void testShowForm() {
        Model model = mock(Model.class);
        String viewName = controller.showForm(model);
        assertEquals("task", viewName);
        verify(model, times(1)).addAttribute(eq("taskDto"), any(TaskDto.class));
    }

    @Test
    void testSubmitForm() {
        TaskDto dto = new TaskDto();
        dto.setTitle(TITLE_CASE);
        dto.setDescription(DESC_CASE);
        String viewName = controller.submitForm(dto);
        assertEquals("redirect:/task/result", viewName);
    }

    @Test
    void testShowSuccessPage() {
        Model model = mock(Model.class);
        String viewName = controller.showSuccessPage(model);
        assertEquals("success", viewName);
        verify(model, times(1)).addAttribute("message", "Успех!!!");
    }
}
