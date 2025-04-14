package ru.itis.vhsroni.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.vhsroni.annotation.BudgetMultiple;
import ru.itis.vhsroni.annotation.WithinYear;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    @NotBlank(message = "{task.title.blank}")
    private String title;

    @NotBlank(message = "{task.description.blank}")
    @Size(max = 500, message = "{task.description.size}")
    private String description;

    @NotNull(message = "{task.priority.null}")
    @Min(value = 1, message = "{task.priority.range}")
    @Max(value = 5, message = "{task.priority.range}")
    private Integer priority;

    @Future(message = "{task.enddate.future}")
    @WithinYear
    private LocalDate endDate;

    @Min(value = 1, message = "{task.budget.min}")
    @BudgetMultiple(value = 100)
    private double budget;

    @NotBlank(message = "{employee.name.blank}")
    @Size(min = 2, max = 30, message = "{employee.name.size}")
    private String name;

    @NotBlank(message = "{employee.position.blank}")
    private String position;

    @Email(message = "{employee.email.invalid}")
    @NotBlank(message = "{employee.email.blank}")
    private String email;

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "{employee.phone.invalid}")
    private String phone;
}