package com.example.metropolitanapi.repository;
import com.example.metropolitanapi.output.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class Repository_Member {
    @Autowired
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public Repository_Member(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member_Out> getMembers() {
        String sql = "SELECT * FROM public.get_member()";
        return jdbcTemplate.query(sql, new Repository_Member.MemberRowMapper());
    }

    public List<Member_Out> getMember(int id) {
        String sql = "SELECT * FROM public.get_member_id(?)";
        return jdbcTemplate.query(sql, new Repository_Member.MemberRowMapper(),id);
    }

    public static List<Activity_Member> getActivity_Member(int currentId) {
        String sql = "SELECT * FROM public.get_member_activity(?)";
        return jdbcTemplate.query(sql, new Repository_Member.ActivityMemberRowMapper(), currentId);
    }

    private static final class ActivityMemberRowMapper implements RowMapper<Activity_Member> {
        @Override
        public Activity_Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Activity_Member act = new Activity_Member();
            act.setActivity(rs.getString("activity_client"));
            act.setState(rs.getString("state_client"));
            return act;
        }
    }

    private static final class MemberRowMapper implements RowMapper<Member_Out> {
        private Long previousId = null;
        private List<Activity_Member> activity = new ArrayList<>();
        @Override
        public Member_Out mapRow(ResultSet rs, int rowNum) throws SQLException {
            int currentId = rs.getInt("id_client");
            Member_Out member = new Member_Out();
            member.setId_member((long) currentId);
            member.setName_member(rs.getString("name_client"));
            member.setDni_member(Long.valueOf(rs.getString("dni_client")));
            member.setCity_member(rs.getString("city_client"));
            List<Activity_Member> activity = addActivity(currentId);
            member.setActivity_Member(activity);
            return member;
        }
    }
    public static List<Activity_Member> addActivity(int currentId) {
        List<Activity_Member> ac = getActivity_Member(currentId);
        return ac;
    }
    public String member_insert(int id_member,String name_member,String dni_member, String city_member) {
        String sql = "SELECT * FROM public.activity_insert(?,?,?,?)";
        return jdbcTemplate.queryForObject(sql, String.class,id_member,name_member,dni_member,city_member);
    }
}
