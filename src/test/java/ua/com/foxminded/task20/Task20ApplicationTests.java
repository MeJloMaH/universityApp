package ua.com.foxminded.task20;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class Task20ApplicationTests extends BaseDaoTest {

	@Autowired
	JdbcTemplate template;

	@Test
	void migrationDone() {

		List<String> tables = template.queryForStream(
				"SELECT * FROM information_schema.tables WHERE table_schema = 'public';", 
				new RowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("table_name");
					}
				})
				.collect(Collectors.toList());

		assertTrue(tables.containsAll(Arrays.asList(
				"groups", 
				"users", 
				"rooms", 				 
				"subjects", 
				"lessons", 
				"flyway_schema_history")));

	}

}
