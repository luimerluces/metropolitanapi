package com.example.metropolitanapi.controller;
import com.example.metropolitanapi.input.Activity_Input;
import com.example.metropolitanapi.output.ActivityResponse;
import com.example.metropolitanapi.output.Activity_Out;
import com.example.metropolitanapi.services.Services_Activity;
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
public class Controller_Activity {
    @Autowired
    private final Services_Activity activityService;

    private static final Logger logger = LoggerFactory.getLogger(Controller_Activity.class);

    public Controller_Activity(Services_Activity activityService) {
        this.activityService = activityService;
    }
    @GetMapping("/activitys")
    public Mono<ActivityResponse> Get_States(){
        return activityService.getActivity()
                .collectList()
                .map(ActivityResponse::new);
    }
    @GetMapping("/activity/{id}")
    public Mono<ActivityResponse> Get_Spaces_id(@PathVariable("id") int id) {
        List<Activity_Out> list_activity=activityService.getActivityById(id);
        return Mono.just(new ActivityResponse(list_activity));
    }

    @PostMapping("/create_activity")
    public ResponseEntity<Map<String, String>> createSpace(@Valid @RequestBody Activity_Input input, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        Map<String, String> response = new HashMap<>();
        String resultado = activityService.activity_insert(input.getName(),input.getScheduled(),input.getSpaces_id());
        response.put("message", resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/update_activity")
    public ResponseEntity<Map<String, String>> update_activity(@Valid @RequestBody Activity_Input input, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        int id = input.getId().orElse(0);
        Map<String, String> response = new HashMap<>();
        String resultado = activityService.activity_update(id,input.getName(),input.getScheduled(),input.getSpaces_id());
        response.put("message", resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/activity_delete/{id}")
    public ResponseEntity<Map<String, String>>delete_states_schedule(@PathVariable("id") int id) {
        Map<String, String> response = new HashMap<>();
        String resultado = activityService.activity_delete(id);
        response.put("message", resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
