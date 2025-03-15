package com.example.metropolitanapi.repository;

import com.example.metropolitanapi.output.Activity_Out;
import com.example.metropolitanapi.output.Space_Out;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class Repository_Activity {
    @Autowired
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public Repository_Activity(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Activity_Out> getActivity() {
        String sql = "SELECT * FROM public.get_activity()";
        return jdbcTemplate.query(sql, new ActivityRowMapper());
    }
    private static final class ActivityRowMapper implements RowMapper<Activity_Out> {
        @Override
        public Activity_Out mapRow(ResultSet rs, int rowNum) throws SQLException {
            Activity_Out activity = new Activity_Out();
            activity.setId(rs.getLong("id"));
            activity.setName(rs.getString("name"));
            activity.setSpaces(rs.getString("space"));
            activity.setScheduled(rs.getDate("scheduled"));
            return activity;
        }
    }

    public List<Activity_Out> getActivityById(int id) {
        String sql = "SELECT * FROM public.get_activity_id(?)";
        return jdbcTemplate.query(sql, new Repository_Activity.ActivityRowMapper(), id);
    }

    public String activity_insert(String name, Date scheduled, int spaces) {
        String sql = "SELECT * FROM public.activity_insert(?,?,?)";
        return jdbcTemplate.queryForObject(sql, String.class,name,scheduled,spaces);
    }

    public String activity_update(int id,String name, Date scheduled, int spaces) {
        String sql = "SELECT * FROM public.activity_update(?,?,?,?)";
        return jdbcTemplate.queryForObject(sql, String.class,id,name,scheduled,spaces);
    }
    public String actiity_delete(int id) {
        String sql = "SELECT * FROM public.activity_delete(?)";
        return jdbcTemplate.queryForObject(sql, String.class,id);
    }

}
