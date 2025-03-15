package com.example.metropolitanapi.services;

import com.example.metropolitanapi.output.Activity_Out;
import com.example.metropolitanapi.output.Space_Out;
import com.example.metropolitanapi.repository.Repository_Activity;
import com.example.metropolitanapi.repository.Repository_Spaces;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class Services_Activity {

    private static final Logger logger = LoggerFactory.getLogger(Services_Activity.class);

    private final Repository_Activity activityRepository;

    public Services_Activity(Repository_Activity activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Flux<Activity_Out> getActivity() {
        return Flux.fromIterable(activityRepository.getActivity());
    }

    public List<Activity_Out> getActivityById(int id) {
        return activityRepository.getActivityById(id);
    }

    public String activity_insert(String name, Date scheduled, int spaces) {
        String insert = activityRepository.activity_insert(name,scheduled,spaces);
        return insert;
    }

    public String activity_update(Integer id, String name, Date scheduled, int spaces) {
        String update = activityRepository.activity_update(id,name,scheduled,spaces);
        return update;
    }

    public String activity_delete(int id) {
        String delete = activityRepository.actiity_delete(id);
        return delete;
    }
}
