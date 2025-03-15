package com.example.metropolitanapi.input;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Optional;
@Data
public class Spaces_Input {
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters.")
    private String name;

    @Size(min = 3, max = 50, message = "The description must be between 3 and 50 characters.")
    private String description;

    private Optional<Integer> id = Optional.empty();

}
