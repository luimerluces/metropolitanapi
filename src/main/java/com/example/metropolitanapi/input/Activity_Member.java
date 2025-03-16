package com.example.metropolitanapi.input;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Activity_Member {
    @Min(value = 1, message = "The value must be positive")
    private int id_state;
    @Min(value = 1, message = "The value must be positive")
    private int id_activity;
}
