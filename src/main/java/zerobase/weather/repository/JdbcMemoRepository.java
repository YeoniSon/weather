package zerobase.weather.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMemoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Memo save(Memo memo) {
        String sql = "INSERT INTO memo VALUES (?, ?)"; //sql문
        jdbcTemplate.update(sql, memo.getId(), memo.getText());

        return memo;
    }

    public List<Memo> findAll() {
        String sql = "select * from memo";
        return jdbcTemplate.query(sql, memoRowMapper());
    }

    public Optional<Memo> findById(int id) {
        String sql = "select * from memo where id = ?";
        return jdbcTemplate.query(sql, memoRowMapper(), id).stream().findFirst();
    }

    //data를 가져오면 ResultSet으로 가져옴
    private RowMapper<Memo> memoRowMapper() {
        //ResultSet
        // {id = 1, text ='this is memo~'}
        //memo로 변경

        return ((rs, rowNum) -> new Memo(
                rs.getInt("id"),
                rs.getString("text")
        ));
    }
}
