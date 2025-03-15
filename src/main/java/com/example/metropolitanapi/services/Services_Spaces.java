package com.example.metropolitanapi.services;

import com.example.metropolitanapi.output.Space_Out;
import com.example.metropolitanapi.repository.Repository_Spaces;
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
public class Services_Spaces {

    private static final Logger logger = LoggerFactory.getLogger(Services_Spaces.class);

    private final Repository_Spaces spaceRepository;


    public Services_Spaces(Repository_Spaces spaceRepository, Validator validator, Repository_Spaces statesRepository) {
        this.spaceRepository = spaceRepository;
    }


    public Flux<Space_Out> getStates() {
        return Flux.fromIterable(spaceRepository.getStates());
    }
    public List<Space_Out> getSpaceById(int id) {
        return spaceRepository.getSpacesById(id);
    }
    public String spaces_insert(String name,String description) {
        String insert = spaceRepository.spaces_insert(name,description);
        return insert;
    }
    public String spaces_update(int id,String name,String description) {
        String update = spaceRepository.spaces_update(id,name,description);
        return update;
    }

    public String spaces_delete(int id) {
        String delete = spaceRepository.spaces_delete(id);
        return delete;
    }
}
