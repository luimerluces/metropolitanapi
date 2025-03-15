package com.example.metropolitanapi.input;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.Optional;
@Data
public class Activity_Input {
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters.")
    private String name;

    @NotNull(message = "The scheduled date must be in the present or future.")
    private Date scheduled;

    @Min(value = 1, message = "The value must be positive")
    private int spaces_id;

    private Optional<Integer> id = Optional.empty();

}
