package com.example.metropolitanapi.output;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
public class MemberResponse {
    private List<Member_Out> members;
    private String message;

    public MemberResponse(List<Member_Out> members, String message) {
        this.members = members;
        this.message = message;
    }
}
