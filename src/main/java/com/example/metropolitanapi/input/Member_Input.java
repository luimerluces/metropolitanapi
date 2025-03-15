package com.example.metropolitanapi.input;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Member_Input {
    private int id_member;
    private String name_member;
    private int dni_member;
    private String city_member;
    private List<Activity_Member> list_Activity_Member;
}
