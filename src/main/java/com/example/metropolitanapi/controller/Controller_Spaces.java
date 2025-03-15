package com.example.metropolitanapi.controller;
import com.example.metropolitanapi.input.Spaces_Input;
import com.example.metropolitanapi.output.Space_Out;
import com.example.metropolitanapi.output.SpacesResponse;
import com.example.metropolitanapi.services.Services_Spaces;
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
public class Controller_Spaces {
    @Autowired
    private final Services_Spaces spacesService;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(Controller_Spaces.class);

    public Controller_Spaces(Services_Spaces spacesService) {
        this.spacesService = spacesService;
    }

    @GetMapping("/spaces")
    public Mono<SpacesResponse> Get_States(){
        return spacesService.getStates()
                .collectList()
                .map(SpacesResponse::new);
    }

    @GetMapping("/spaces/{id}")
    public Mono<SpacesResponse> Get_Spaces_id(@PathVariable("id") int id) {
        List<Space_Out> list_spaces=spacesService.getSpaceById(id);
        return Mono.just(new SpacesResponse(list_spaces));
    }

    @PostMapping("/create_spaces")
    public ResponseEntity<Map<String, String>> createSpace(@Valid @RequestBody Spaces_Input input, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        Map<String, String> response = new HashMap<>();
        String resultado = spacesService.spaces_insert(input.getName(),input.getDescription());
        response.put("message", resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/update_spaces")
    public ResponseEntity<Map<String, String>> update_states_schedule(@Valid @RequestBody Spaces_Input input, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        int id = input.getId().orElse(0);
        Map<String, String> response = new HashMap<>();
        String resultado = spacesService.spaces_update(id,input.getName(),input.getDescription());
        response.put("message", resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/spaces_delete/{id}")
    public ResponseEntity<Map<String, String>>delete_states_schedule(@PathVariable("id") int id) {
        Map<String, String> response = new HashMap<>();
        String resultado = spacesService.spaces_delete(id);
        response.put("message", resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
