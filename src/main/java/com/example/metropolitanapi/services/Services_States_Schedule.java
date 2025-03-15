package com.example.metropolitanapi.services;
import com.example.metropolitanapi.output.States_Schedule_Out;
import com.example.metropolitanapi.repository.Repository_States_Schedule;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import reactor.core.publisher.Flux;
import java.util.List;

@Service
@Slf4j
public class Services_States_Schedule {

    private static final Logger logger = LoggerFactory.getLogger(Services_States_Schedule.class);

    private final Repository_States_Schedule statesScheduleRepository;


    public Services_States_Schedule(Repository_States_Schedule statesScheduleRepository, Validator validator) {
        this.statesScheduleRepository = statesScheduleRepository;
    }

    public Flux<States_Schedule_Out> getStatesSchedule() {
        return Flux.fromIterable(statesScheduleRepository.getStatesSchedule());
    }

    public List<States_Schedule_Out> getStatesScheduleById(int id) {
        return statesScheduleRepository.getStatesScheduleById(id);
    }


    public String states_schedule_insert(String name) {
        String insert = statesScheduleRepository.states_schedule_insert(name);
        return insert;
    }

    public String states_schedule_update(int id,String name) {
        String update = statesScheduleRepository.states_schedule_update(id,name);
        return update;
    }

    public String States_Schedule_delete(int id) {
        String delete = statesScheduleRepository.states_schedule_delete(id);
        return delete;
    }

}
