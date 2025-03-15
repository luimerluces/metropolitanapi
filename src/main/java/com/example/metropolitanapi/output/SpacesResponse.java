package com.example.metropolitanapi.output;

import lombok.Data;

import java.util.List;

@Data

public class SpacesResponse {
    private List<Space_Out> spaces;
    private String message;

    public SpacesResponse(List<Space_Out> spaces) {
        this.spaces = spaces;
    }
}
