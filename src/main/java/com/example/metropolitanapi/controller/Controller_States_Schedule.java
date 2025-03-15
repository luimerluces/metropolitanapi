package com.example.metropolitanapi.controller;
import com.example.metropolitanapi.input.States_Schedule_Input;
import com.example.metropolitanapi.output.StatesScheduleResponse;
import com.example.metropolitanapi.output.States_Schedule_Out;
import com.example.metropolitanapi.services.Services_States_Schedule;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jacidi")
public class Controller_States_Schedule {
    @Autowired
    private final Services_States_Schedule statesScheduleService;
    private static final Logger logger = LoggerFactory.getLogger(Controller_States_Schedule.class);

    @Autowired
    public Controller_States_Schedule(Services_States_Schedule statesScheduleService) {
        this.statesScheduleService = statesScheduleService;
    }

    @GetMapping("/states_schedules")
    public Mono<StatesScheduleResponse> Get_States_Schedule(){
        return statesScheduleService.getStatesSchedule()
                .collectList()
                .map(StatesScheduleResponse::new);
    }

    @GetMapping("/states_schedule_id/{id}")
    public Mono<StatesScheduleResponse> Get_States_Schedule_id(@PathVariable("id") int id) {
        List<States_Schedule_Out> list_states=statesScheduleService.getStatesScheduleById(id);
        return Mono.just(new StatesScheduleResponse(list_states));
    }

    @PostMapping("/create_states_schedule")
    public ResponseEntity<Map<String, String>> createStatesSchedule(@Valid @RequestBody States_Schedule_Input input, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        Map<String, String> response = new HashMap<>();
        String resultado = statesScheduleService.states_schedule_insert(input.getName());
        response.put("message", resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/update_states_schedule")
    public ResponseEntity<Map<String, String>> update_states_schedule(@Valid @RequestBody States_Schedule_Input input, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        int id = input.getId().orElse(0);
        Map<String, String> response = new HashMap<>();
        String resultado = statesScheduleService.states_schedule_update(id,input.getName());
        response.put("message", resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/states_schedule_delete/{id}")
    public ResponseEntity<Map<String, String>>delete_states_schedule(@PathVariable("id") int id) {
        Map<String, String> response = new HashMap<>();
        String resultado = statesScheduleService.States_Schedule_delete(id);
        response.put("message", resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
