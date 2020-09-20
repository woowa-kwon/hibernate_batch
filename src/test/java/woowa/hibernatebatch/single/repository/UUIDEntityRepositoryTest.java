package woowa.hibernatebatch.single.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import woowa.hibernatebatch.TestSupport;
import woowa.hibernatebatch.single.model.UUIDEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class UUIDEntityRepositoryTest extends TestSupport {

    private static final int SIZE = 3000;

    @Autowired
    private UUIDEntityRepository uuidEntityRepository;

    @BeforeEach
    @Commit
    void setup() {
        truncate();
    }

    @AfterEach
    @Commit
    void tearDown() {
        truncate();
    }

    private void truncate() {
        truncate("uuid");
    }

    @Test
    void save() throws Exception {
        final UUIDEntity uuidEntity = UUIDEntity.of();

        final UUIDEntity save = uuidEntityRepository.saveAndFlush(uuidEntity);

        final UUIDEntity find = uuidEntityRepository.findById(save.getId()).get();
        assertThat(find.getId()).isNotNull();
        assertThat(find.getId()).isEqualTo(save.getId());
        assertThat(find.getC1()).isEqualTo(save.getC1());
        assertThat(find.getC2()).isEqualTo(save.getC2());
        assertThat(find.getC3()).isEqualTo(save.getC3());
        assertThat(find.getC4()).isEqualTo(save.getC4());
        assertThat(find.getC5()).isEqualTo(save.getC5());
        assertThat(find.getC6()).isEqualTo(save.getC6());
        assertThat(find.getC7()).isEqualTo(save.getC7());
        assertThat(find.getC8()).isEqualTo(save.getC8());
        assertThat(find.getC9()).isEqualTo(save.getC9());
        assertThat(find.getC10()).isEqualTo(save.getC10());
        assertThat(find.getC11()).isEqualTo(save.getC11());
        assertThat(find.getC12()).isEqualTo(save.getC12());
        assertThat(find.getC13()).isEqualTo(save.getC13());
        assertThat(find.getC14()).isEqualTo(save.getC14());
        assertThat(find.getC15()).isEqualTo(save.getC15());
    }

    @DisplayName("3000 개 생성하여 한번에 저장")
    @Test
    void saveAll() throws Exception {
        final List<UUIDEntity> uuidEntities = Stream.generate(UUIDEntity::of)
                .limit(SIZE)
                .collect(Collectors.toList());

        uuidEntityRepository.saveAll(uuidEntities);
        flush();
    }

    @DisplayName("3000 개 한번에 업데이트")
    @Test
    void update() throws Exception {
        insertTestValues(INSERT_UUID, uuidParameters(SIZE));

        final List<UUIDEntity> uuidEntities = uuidEntityRepository.findAll();
        uuidEntities.forEach(UUIDEntity::plus);

        flush();
    }

}