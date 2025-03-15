package com.example.metropolitanapi.output;

import lombok.Data;

import java.util.List;

@Data

public class ActivityResponse {
    private List<Activity_Out> activity;
    private String message;

    public ActivityResponse(List<Activity_Out> activity) {
        this.activity = activity;
    }
}
