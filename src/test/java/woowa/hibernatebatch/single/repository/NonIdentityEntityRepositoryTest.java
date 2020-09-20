package woowa.hibernatebatch.single.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.util.StopWatch;
import woowa.hibernatebatch.TestSupport;
import woowa.hibernatebatch.single.model.NonIdentityEntity;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
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
        final int size = 5;
        final List<NonIdentityEntity> nonIdentityEntities = createNonIdentityEntities(size);

        for (NonIdentityEntity nonIdentityEntity : nonIdentityEntities) {
            nonIdentityEntityRepository.save(nonIdentityEntity);
        }
        flush();
    }

    @DisplayName("엔티티 목록 전체를 saveAll 호출")
    @Test
    void saveAll2() throws Exception {
        final int size = 5;
        final List<NonIdentityEntity> nonIdentityEntities = createNonIdentityEntities(size);

        nonIdentityEntityRepository.saveAll(nonIdentityEntities);
        flush();
    }

    @DisplayName("500건씩 분할하여 총 10,000건 저장")
    @Test
    void saveLoop() throws Exception {
        final AtomicLong atomicLong = new AtomicLong();
        final int totalSize = 10_000;
        final int chunkSize = 500;
        final int loopSize = totalSize / chunkSize;

        final List<NonIdentityEntity> nonIdentityEntities = createNonIdentityEntities(totalSize);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < loopSize; i++) {
            final int fromIndex = i * chunkSize;
            final int toIndex = fromIndex + chunkSize;
            final List<NonIdentityEntity> subList = nonIdentityEntities.subList(fromIndex, toIndex);
            nonIdentityEntityRepository.saveAll(subList);
            flush();
        }
        stopWatch.stop();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("결과(초) : " + stopWatch.getTotalTimeSeconds());
        System.out.println();
        System.out.println();
        System.out.println();
    }

    private List<NonIdentityEntity> createNonIdentityEntities(int size) {
        final List<NonIdentityEntity> nonIdentityEntities = LongStream.rangeClosed(1, size)
                .mapToObj(NonIdentityEntity::of)
                .collect(Collectors.toList());
        return nonIdentityEntities;
    }

    @DisplayName("저장한 목록 전체 한번에 업데이트")
    @Test
    void updateAll() throws Exception {
        final int size = 10;
        insertTestValues(INSERT_NON_IDENTITY, nonIdentityParameters(size));
        final List<NonIdentityEntity> nonIdentityEntities = nonIdentityEntityRepository.findAll();
        nonIdentityEntities.forEach(NonIdentityEntity::plus);

        flush();
    }

    @DisplayName("10,000 건 저장후 500 건씩 분할 조회하여 업데이트")
    @Test
    void updateLoop() throws Exception {
        final int insertSize = 10_000;
        insertTestValues(INSERT_NON_IDENTITY, nonIdentityParameters(insertSize));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int pageSize = 500;

        Pageable pageable = PageRequest.of(0, pageSize);
        while (true) {
            final Slice<NonIdentityEntity> slice = nonIdentityEntityRepository.findAllIdentityEntities(pageable);
            final List<NonIdentityEntity> nonIdentityEntities = slice.getContent();
            nonIdentityEntities.forEach(NonIdentityEntity::plus);
            flush();
            if (slice.isLast()) {
                break;
            }
            pageable = slice.nextPageable();
        }
        stopWatch.stop();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("결과(초) : " + stopWatch.getTotalTimeSeconds());
        System.out.println();
        System.out.println();
        System.out.println();
    }

}