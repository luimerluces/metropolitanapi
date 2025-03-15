package com.example.metropolitanapi.output;

import lombok.Data;

import java.util.List;
@Data
public class StatesScheduleResponse {
    private List<States_Schedule_Out> states_schedule;
    private String message;

    public StatesScheduleResponse() {
    }

     public StatesScheduleResponse(List<States_Schedule_Out> states_schedule) {
        this.states_schedule = states_schedule;
    }

}
