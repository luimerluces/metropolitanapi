package com.example.metropolitanapi.repository;

import com.example.metropolitanapi.output.Space_Out;
import com.example.metropolitanapi.output.States_Schedule_Out;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Repository_Spaces {
    @Autowired
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public Repository_Spaces(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Space_Out> getStates() {
        String sql = "SELECT * FROM public.get_spaces()";
        return jdbcTemplate.query(sql, new SpacesRowMapper());
    }
    private static final class SpacesRowMapper implements RowMapper<Space_Out> {
        @Override
        public Space_Out mapRow(ResultSet rs, int rowNum) throws SQLException {
            Space_Out states = new Space_Out();
            states.setId(rs.getLong("id"));
            states.setName(rs.getString("name"));
            states.setDescription(rs.getString("description"));
            return states;
        }
    }
    public List<Space_Out> getSpacesById(int id) {
        String sql = "SELECT * FROM public.get_spaces_id(?)";
        return jdbcTemplate.query(sql, new Repository_Spaces.SpacesRowMapper(), id);
    }

    public String spaces_insert(String name,String description) {
        String sql = "SELECT * FROM public.spaces_insert(?,?)";
        return jdbcTemplate.queryForObject(sql, String.class,name,description);
    }
    public String spaces_update(int id,String name,String description) {
        String sql = "SELECT * FROM public.spaces_update(?,?,?)";
        return jdbcTemplate.queryForObject(sql, String.class,id,name,description);
    }

    public String spaces_delete(int id) {
        String sql = "SELECT * FROM public.spaces_delete(?)";
        return jdbcTemplate.queryForObject(sql, String.class,id);
    }

}
