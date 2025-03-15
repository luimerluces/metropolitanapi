package com.example.metropolitanapi.output;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Member_Out {
    private Long id_member;
    private String name_member;
    private Long dni_member;
    private String city_member;
    private List<Activity_Member> activity_Member;

    public Member_Out(Long id_member, String name_member, Long dni_member, String city_member, List<Activity_Member> activity_Member) {
        this.id_member = id_member;
        this.name_member = name_member;
        this.dni_member = dni_member;
        this.city_member = city_member;
        this.activity_Member = activity_Member;
    }

    public Member_Out() {
    }
}
