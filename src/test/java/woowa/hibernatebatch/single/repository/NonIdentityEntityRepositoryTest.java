package woowa.hibernatebatch.single.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import woowa.hibernatebatch.TestSupport;
import woowa.hibernatebatch.single.model.NonIdentityEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

class NonIdentityEntityRepositoryTest extends TestSupport {

    @Autowired
    private NonIdentityEntityRepository nonIdentityEntityRepository;

    @BeforeEach
    void setup() {
        truncate();
    }

    @AfterEach
    void tearDown() {
        truncate();
    }

    private void truncate() {
        truncate("non_identity");
    }

    @Test
    void save() throws Exception {
        NonIdentityEntity entity = NonIdentityEntity.of(1L);

        final NonIdentityEntity save = nonIdentityEntityRepository.saveAndFlush(entity);

        final NonIdentityEntity find = nonIdentityEntityRepository.findById(save.getId()).get();
        assertThat(find.getId()).isEqualTo(save.getId());
        assertThat(find.getC1()).isEqualTo(save.getC1());
        assertThat(find.getC2()).isEqualTo(save.getC2());
        assertThat(find.getC3()).isEqualTo(save.getC3());
        assertThat(find.getC4()).isEqualTo(save.getC4());
        assertThat(find.getC5()).isEqualTo(save.getC5());

        assertThat(find.getVersion()).isNotNull();
    }

    @DisplayName("엔티티 한건씩 save 호출")
    @Test
    void saveAll() throws Exception {
        final List<NonIdentityEntity> nonIdentityEntities = LongStream.rangeClosed(1, 200)
                .mapToObj(NonIdentityEntity::of)
                .collect(Collectors.toList());

        for (NonIdentityEntity nonIdentityEntity : nonIdentityEntities) {
            nonIdentityEntityRepository.save(nonIdentityEntity);
        }
        flush();
    }

    @DisplayName("엔티티 목록 전체를 saveAll 호출")
    @Test
    void saveAll2() throws Exception {
        final List<NonIdentityEntity> nonIdentityEntities = LongStream.rangeClosed(1, 200)
                .mapToObj(NonIdentityEntity::of)
                .collect(Collectors.toList());

        nonIdentityEntityRepository.saveAll(nonIdentityEntities);
        flush();
    }

    @DisplayName("저장한 목록 전체 한번에 업데이트")
    @Test
    void updateAll() throws Exception {
        final int size = 10;
        insertTestValues(INSERT_NON_IDENTITY, nonIdentityParameters(size));

        Pageable pageable = PageRequest.of(0, size);
        while (true) {
            final Slice<NonIdentityEntity> slice = nonIdentityEntityRepository.findAllIdentityEntities(pageable);
            final List<NonIdentityEntity> identityEntities = slice.getContent();
            identityEntities.forEach(NonIdentityEntity::plus);
            if (slice.isLast()) {
                break;
            }
            pageable = slice.nextPageable();
        }

        flush();
    }

}