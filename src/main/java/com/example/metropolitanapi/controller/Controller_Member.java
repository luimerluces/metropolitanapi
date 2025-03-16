package com.example.metropolitanapi.controller;

import com.example.metropolitanapi.input.Member_Input;
import com.example.metropolitanapi.input.States_Schedule_Input;
import com.example.metropolitanapi.output.MemberResponse;
import com.example.metropolitanapi.services.Services_Member;
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

    private static final Logger logger = LoggerFactory.getLogger(Controller_Member.class);

    public Controller_Member(Services_Member memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/members")
    public Mono<MemberResponse> Get_Members(){
        return memberService.getMembers()
                .collectList()
                .map(members -> new MemberResponse(members, "Successful operation"));
    }

    @GetMapping("/member/{id}")
    public Mono<MemberResponse> Get_Member(@PathVariable("id") int id){
        return memberService.getMember(id)
                .collectList()
                .map(members -> new MemberResponse(members, "Successful operation"));
    }
    @PostMapping("/create_member")
    public ResponseEntity<Map<String, String>> createmember(@Valid @RequestBody Member_Input input, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        Map<String, String> response = new HashMap<>();
        logger.info(String.valueOf(input.getId_member()));
        logger.info(input.getName_member());
        logger.info(String.valueOf(input.getDni_member()));
        logger.info(input.getCity_member());
        logger.info(input.getActivity_Member().toString());
        String resultado = memberService.member_insert(input.getId_member(),input.getName_member(),String.valueOf(input.getDni_member()),input.getCity_member());
        response.put("message", resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
