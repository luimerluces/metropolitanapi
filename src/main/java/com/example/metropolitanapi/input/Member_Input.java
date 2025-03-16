package com.example.metropolitanapi.input;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ToString
public class Member_Input {
    @Min(value = 1, message = "The value must be positive")
    private int id_member;
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters.")
    private String name_member;
    @Min(value = 1, message = "The value must be positive")
    private int dni_member;
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters.")
    private String city_member;
    @NotEmpty(message = "The list of activities cannot be empty.")
    private List<Activity_Member> activity_Member;
}
