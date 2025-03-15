package com.example.metropolitanapi.controller;

import com.example.metropolitanapi.output.MemberResponse;
import com.example.metropolitanapi.services.Services_Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
}
