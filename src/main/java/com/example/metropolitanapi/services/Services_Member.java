package com.example.metropolitanapi.services;
import com.example.metropolitanapi.output.Activity_Out;
import com.example.metropolitanapi.output.Member_Out;
import com.example.metropolitanapi.repository.Repository_Member;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class Services_Member {

    private static final Logger logger = LoggerFactory.getLogger(Services_Member.class);

    private final Repository_Member memberRepository;

    public Services_Member(Repository_Member memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Flux<Member_Out> getMembers() {
        return Flux.fromIterable(memberRepository.getMembers());
    }

    public Flux<Member_Out> getMember(int id) {
        return Flux.fromIterable(memberRepository.getMember(id));
    }

    public String member_insert(int id_member,String name_member,String dni_member, String city_member) {
        String insert = spaceRepository.spaces_insert(id_member,name_member,dni_member,city_member);
        return insert;
    }

}
