package com.example.hellospring.repository;

import com.example.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate; // 얘를 입력해줘야하는데 직접 주입(injection)을 받을 수 있는 것은 아님

    @Autowired // 생성자가 한개면 autowired 생략 가능
    public JdbcTemplateMemberRepository(DataSource dataSource) { // 데이터 소스를 주입받아서 만들어줌. 이게 권장 방식
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate); // jdbc template를 받아서 생성
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id"); // 테이블 명과 생성되는 키 컬럼을 적어주면 sql 필요없이 할 수 있다.

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName()); // 맵으로 이름을 넣어준다.

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); // 맵 객체를 넣어서 MapSqlParameterSource를 파라미터로 쿼리를 실행하고 키를 반환받는다.
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id); // 결과를 row mapper로 매핑을 해줘야함, 그리고 파라미터를 넣어줌
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
//        return new RowMapper<Member>() {
//            @Override
//            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Member member = new Member();
//                member.setId(rs.getLong("id"));
//                member.setName(rs.getString("name"));
//                return member;
//            }
//        }; alt + enter 하면 lambda 스타일로 바꿀 수 있음

        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
