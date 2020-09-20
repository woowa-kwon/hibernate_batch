package woowa.hibernatebatch.single.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.annotation.Commit;
import woowa.hibernatebatch.TestSupport;
import woowa.hibernatebatch.single.model.IdentityEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityEntityRepositoryTest extends TestSupport {

    @Autowired
    private IdentityEntityRepository identityEntityRepository;

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
        truncate("identity");
    }

    @Test
    void save() throws Exception {
        IdentityEntity entity = IdentityEntity.of();

        final IdentityEntity save = identityEntityRepository.saveAndFlush(entity);

        final IdentityEntity find = identityEntityRepository.findById(save.getId()).get();
        assertThat(find.getId()).isNotNull();
        assertThat(find.getId()).isEqualTo(save.getId());
        assertThat(find.getC1()).isEqualTo(save.getC1());
        assertThat(find.getC2()).isEqualTo(save.getC2());
        assertThat(find.getC3()).isEqualTo(save.getC3());
        assertThat(find.getC4()).isEqualTo(save.getC4());
        assertThat(find.getC5()).isEqualTo(save.getC5());

        assertThat(find.getVersion()).isNotNull();
        System.out.println(find);
    }

    @DisplayName("자동증가 엔티티 여러개 한번에 저장")
    @Test
    void saveAll() throws Exception {
        final int size = 3;

        final List<IdentityEntity> identityEntities = Stream.generate(IdentityEntity::of)
                .limit(size)
                .collect(Collectors.toList());

        identityEntityRepository.saveAll(identityEntities);
    }

    @DisplayName("조회한 엔티티 전체 업데이트")
    @Test
    void updateAll() throws Exception {
        int size = 4;
        insertTestValues(INSERT_IDENTITY, identityParameters(size));

        final List<IdentityEntity> identityEntities = identityEntityRepository.findAll();
        identityEntities.forEach(IdentityEntity::plus);

        flush();
    }

    @DisplayName("3개만 업데이트")
    @Test
    void updateAll2() throws Exception {
        int size = 3;
        insertTestValues(INSERT_IDENTITY, identityParameters(size));

        final List<IdentityEntity> identityEntities = identityEntityRepository.findAll();
        identityEntities.forEach(IdentityEntity::plus);

        flush();
    }

    @DisplayName("한 트랜잭션에서 분할 조회하여 업데이트")
    @Test
    void updateAll3() throws Exception {
        final int insertSize = 10;
        final int pageSize = 5;
        insertTestValues(INSERT_IDENTITY, identityParameters(insertSize));

        Pageable pageable = PageRequest.of(0, pageSize);
        while (true) {
            final Slice<IdentityEntity> slice = identityEntityRepository.findAllIdentityEntities(pageable);
            final List<IdentityEntity> identityEntities = slice.getContent();
            identityEntities.forEach(IdentityEntity::plus);

            if (slice.isLast()) {
                break;
            }
            pageable = slice.nextPageable();
        }

        flush();
    }

}