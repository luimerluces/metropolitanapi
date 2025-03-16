package com.example.metropolitanapi.util;
import com.example.metropolitanapi.input.Activity_Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
@Service
@Slf4j
public class Util {
    public static String convertToJson(Activity_Member activityMember) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(activityMember);
    }

    public static String convertListToJson(List<Activity_Member> activityMembers) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(activityMembers);
    }
}
