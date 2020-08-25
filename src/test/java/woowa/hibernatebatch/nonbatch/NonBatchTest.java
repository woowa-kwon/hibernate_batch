package woowa.hibernatebatch.nonbatch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.annotation.Commit;
import woowa.hibernatebatch.TestSupport;

import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NonBatchTest extends TestSupport {

    private static final String INSERT_ONCE = "INSERT INTO non_batch(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10) VALUES";
    private static final String INSERT_BATCH = "INSERT INTO non_batch(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10) VALUES (:c1, :c2, :c3, :c4, :c5, :c6, :c7, :c8, :c9, :c10)";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM non_batch";

    private static final int SIZE = 2000;

    @Commit
    @BeforeEach
    void setup() {
        truncate();
    }

    @Commit
    @AfterEach
    void tearDown() {
        truncate();
    }

    private void truncate() {
        truncate("non_batch");
    }

    @DisplayName("쿼리를 문자열로 직접 만들어 한번에 실행")
    @Test
    void saveOnce() throws Exception {
        String values = Stream.generate(this::generateValues)
                .limit(SIZE)
                .collect(Collectors.joining(","));

        assertThatThrownBy(() -> namedParameterJdbcTemplate.update(INSERT_ONCE + values, Map.of()))
                .isInstanceOf(TransientDataAccessResourceException.class)
                .hasMessageContaining("max_allowed_packet");
    }

    private String generateValues() {
        StringJoiner joiner = new StringJoiner(",");
        for (int i = 0; i < 10; i++) {
            joiner.add("'" + randomString() + "'");
        }
        return "(" + joiner.toString() + ")";
    }

    @DisplayName("배치로 실행")
    @Test
    void saveBatch() throws Exception {
        namedParameterJdbcTemplate.batchUpdate(INSERT_BATCH, parameters(SIZE));

        final Integer count = namedParameterJdbcTemplate.queryForObject(COUNT_QUERY, Map.of(), Integer.class);

        assertThat(count).isEqualTo(SIZE);
    }

    private SqlParameterSource[] parameters(int size) {
        final SqlParameterSource[] values = new MapSqlParameterSource[size];
        for (int i = 0; i < size; i++) {
            values[i] = new MapSqlParameterSource()
                    .addValue("c1", randomString())
                    .addValue("c2", randomString())
                    .addValue("c3", randomString())
                    .addValue("c4", randomString())
                    .addValue("c5", randomString())
                    .addValue("c6", randomString())
                    .addValue("c7", randomString())
                    .addValue("c8", randomString())
                    .addValue("c9", randomString())
                    .addValue("c10", randomString());
        }
        return values;
    }

    private String randomString() {
        String random = UUID.randomUUID().toString();
        return random.repeat(7);
    }



}
