package com.example.metropolitanapi.controller;
import com.example.metropolitanapi.input.Member_Input;
import java.io.IOException;
import com.example.metropolitanapi.output.MemberResponse;
import com.example.metropolitanapi.services.Services_Member;
import com.example.metropolitanapi.util.Util;
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
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jacidi")
public class Controller_Member {
    @Autowired
    private final Services_Member memberService;
    private final Util u;

    private static final Logger logger = LoggerFactory.getLogger(Controller_Member.class);

    public Controller_Member(Services_Member memberService, Util u) {
        this.memberService = memberService;
        this.u = u;
    }


    @GetMapping("/members")
    public Mono<MemberResponse> Get_Members() {
        return memberService.getMembers()
                .collectList()
                .map(members -> new MemberResponse(members, "Successful operation"));
    }

    @GetMapping("/member/{id}")
    public Mono<MemberResponse> Get_Member(@PathVariable("id") int id) {
        return memberService.getMember(id)
                .collectList()
                .map(members -> new MemberResponse(members, "Successful operation"));
    }

    @PostMapping("/create_member")
    public ResponseEntity<Map<String, String>> createmember(@Valid @RequestBody Member_Input input, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        Map<String, String> response = new HashMap<>();
        String json_Activity_Member = u.convertListToJson(input.getActivity_Member());
        String resultado = memberService.member_insert(input.getName_member(), String.valueOf(input.getDni_member()), input.getCity_member(), json_Activity_Member, input.getId_member());
        response.put("message", resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/member_delete/{id}")
    public ResponseEntity<Map<String, String>>delete_member(@PathVariable("id") int id) {
        Map<String, String> response = new HashMap<>();
        String resultado = memberService.member_delete(id);
        response.put("message", resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
