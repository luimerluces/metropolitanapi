package com.example.metropolitanapi.repository;

import com.example.metropolitanapi.output.States_Schedule_Out;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class Repository_States_Schedule {
    @Autowired
    private static JdbcTemplate jdbcTemplate;

    public List<States_Schedule_Out> getStatesSchedule() {
        String sql = "SELECT * FROM public.get_states_schedule()";
        return jdbcTemplate.query(sql, new StatesScheduleRowMapper());
    }


    public List<States_Schedule_Out> getStatesScheduleById(int id) {
        String sql = "SELECT * FROM public.get_states_schedule_id(?)";
        return jdbcTemplate.query(sql, new StatesScheduleRowMapper(), id);
    }


    private static final class StatesScheduleRowMapper implements RowMapper<States_Schedule_Out> {
        @Override
        public States_Schedule_Out mapRow(ResultSet rs, int rowNum) throws SQLException {
            States_Schedule_Out statesSchedule = new States_Schedule_Out();
            statesSchedule.setId(rs.getLong("id"));
            statesSchedule.setName(rs.getString("name"));
            return statesSchedule;
        }
    }

    public String states_schedule_insert(String name) {
        String sql = "SELECT * FROM public.states_schedule_insert(?)";
        return jdbcTemplate.queryForObject(sql, String.class,name);
    }

    public String states_schedule_update(int id,String name) {
        String sql = "SELECT * FROM public.states_schedule_update(?,?)";
        return jdbcTemplate.queryForObject(sql, String.class,name,id);
    }

    public String states_schedule_delete(int id) {
        String sql = "SELECT * FROM public.states_schedule_delete(?)";
        return jdbcTemplate.queryForObject(sql, String.class,id);
    }

    @Autowired
    public Repository_States_Schedule(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
