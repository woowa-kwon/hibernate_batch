package woowa.hibernatebatch;

import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import woowa.hibernatebatch.single.model.UUIDEntity;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class TestSupport {

    public static final String INSERT_IDENTITY = "INSERT INTO identity(c1, c2, c3, c4, c5, version) VALUES (:c1, :c2, :c3, :c4, :c5, :version)";
    public static final String INSERT_NON_IDENTITY = "INSERT INTO non_identity(id, c1, c2, c3, c4, c5, version) VALUES (:id, :c1, :c2, :c3, :c4, :c5, :version)";
    public static final String INSERT_PARENT = "INSERT INTO parent(id, c1, c2, c3, c4, c5, version) VALUES (:id, :c1, :c2, :c3, :c4, :c5, :version)";
    public static final String INSERT_CHILD = "INSERT INTO child(id, parent_id, c1, c2, c3, c4, c5, version) VALUES (:id, :parent_id, :c1, :c2, :c3, :c4, :c5, :version)";
    public static final String INSERT_UUID = "INSERT INTO uuid(id, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15) VALUES (:id, :c1, :c2, :c3, :c4, :c5, :c6, :c7, :c8, :c9, :c10, :c11, :c12, :c13, :c14, :c15)";

    @Autowired
    private EntityManager entityManager;

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected void insertTestValues(String sql, SqlParameterSource[] batchParameters) {
        namedParameterJdbcTemplate.batchUpdate(sql, batchParameters);
    }

    protected void truncate(String table) {
        namedParameterJdbcTemplate.update("TRUNCATE " + table, Map.of());
    }

    protected void flush() {
        entityManager.flush();
    }

    protected SqlParameterSource[] identityParameters(int size) {
        final SqlParameterSource[] values = new MapSqlParameterSource[size];
        for (int i = 0; i < size; i++) {
            values[i] = new MapSqlParameterSource()
                    .addValue("c1", 1)
                    .addValue("c2", "c2-" + 1)
                    .addValue("c3", "c3-" + 1)
                    .addValue("c4", "c4-" + 1)
                    .addValue("c5", "c5-" + 1)
                    .addValue("version", 0);
        }
        return values;
    }

    protected SqlParameterSource[] nonIdentityParameters(int size) {
        final SqlParameterSource[] values = new MapSqlParameterSource[size];
        for (int i = 0; i < size; i++) {
            values[i] = new MapSqlParameterSource()
                    .addValue("id", i + 1)
                    .addValue("c1", 1)
                    .addValue("c2", "c2-" + 1)
                    .addValue("c3", "c3-" + 1)
                    .addValue("c4", "c4-" + 1)
                    .addValue("c5", "c5-" + 1)
                    .addValue("version", 0);
        }
        return values;
    }


    public static SqlParameterSource[] parentParameters(int size) {
        final SqlParameterSource[] values = new MapSqlParameterSource[size];
        for (int i = 0; i < size; i++) {
            values[i] = new MapSqlParameterSource()
                    .addValue("id", i + 1)
                    .addValue("c1", 1)
                    .addValue("c2", "c2-" + 1)
                    .addValue("c3", "c3-" + 1)
                    .addValue("c4", "c4-" + 1)
                    .addValue("c5", "c5-" + 1)
                    .addValue("version", 0);
        }
        return values;
    }

    protected SqlParameterSource[] childParameters(int parentSize, int eachSize) {
        final SqlParameterSource[] values = new MapSqlParameterSource[parentSize * eachSize];
        int index = 0;
        for (int parent = 1; parent <= parentSize; parent++) {
            for (int e = 0; e < eachSize; e++) {
                values[index] = childParameter(index + 1, parent);
                index++;
            }
        }
        return values;
    }

    protected MapSqlParameterSource childParameter(int childId, int parentId) {
        return new MapSqlParameterSource()
                .addValue("id", childId)
                .addValue("parent_id", parentId)
                .addValue("c1", 1)
                .addValue("c2", "c2-" + 1)
                .addValue("c3", "c3-" + 1)
                .addValue("c4", "c4-" + 1)
                .addValue("c5", "c5-" + 1)
                .addValue("version", 0);
    }

    protected SqlParameterSource[] uuidParameters(int size) {
        final SqlParameterSource[] values = new MapSqlParameterSource[size];
        final UUIDTypeDescriptor.ToBytesTransformer transformer = UUIDTypeDescriptor.ToBytesTransformer.INSTANCE;
        for (int i = 0; i < size; i++) {
            values[i] = new MapSqlParameterSource()
                    .addValue("id", transformer.transform(UUID.randomUUID()))
                    .addValue("c1", 1)
                    .addValue("c2", UUIDEntity.randomString())
                    .addValue("c3", UUIDEntity.randomString())
                    .addValue("c4", UUIDEntity.randomString())
                    .addValue("c5", UUIDEntity.randomString())
                    .addValue("c6", UUIDEntity.randomString())
                    .addValue("c7", UUIDEntity.randomString())
                    .addValue("c8", UUIDEntity.randomString())
                    .addValue("c9", UUIDEntity.randomString())
                    .addValue("c10", UUIDEntity.randomString())
                    .addValue("c11", UUIDEntity.randomString())
                    .addValue("c12", UUIDEntity.randomString())
                    .addValue("c13", UUIDEntity.randomString())
                    .addValue("c14", UUIDEntity.randomString())
                    .addValue("c15", UUIDEntity.randomString());
        }
        return values;
    }

}
