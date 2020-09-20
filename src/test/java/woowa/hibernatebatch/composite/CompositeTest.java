package woowa.hibernatebatch.composite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import woowa.hibernatebatch.TestSupport;
import woowa.hibernatebatch.single.model.IdentityEntity;
import woowa.hibernatebatch.single.model.NonIdentityEntity;
import woowa.hibernatebatch.single.model.UUIDEntity;
import woowa.hibernatebatch.single.repository.IdentityEntityRepository;
import woowa.hibernatebatch.single.repository.NonIdentityEntityRepository;
import woowa.hibernatebatch.single.repository.UUIDEntityRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class CompositeTest extends TestSupport {

    @Autowired
    private IdentityEntityRepository identityEntityRepository;

    @Autowired
    private NonIdentityEntityRepository nonIdentityEntityRepository;

    @Autowired
    private UUIDEntityRepository uuidEntityRepository;

    @DisplayName("자동증가 엔티티와 자동증가 아닌 엔티티 번갈아가며 한건씩 save 호출")
    @Test
    void saveIdentityAndNonIdentity() throws Exception {
        final int size = 5;
        final List<IdentityEntity> identityEntities = Stream.generate(IdentityEntity::of)
                .limit(size)
                .collect(Collectors.toList());

        final List<NonIdentityEntity> nonIdentityEntities = LongStream.rangeClosed(1, size)
                .mapToObj(NonIdentityEntity::of)
                .collect(Collectors.toList());

        for (int i = 0; i < size; i++) {
            identityEntityRepository.save(identityEntities.get(i));
            nonIdentityEntityRepository.save(nonIdentityEntities.get(i));
        }
    }

    @DisplayName("자동증가 엔티티와 자동증가 아닌 엔티티 번갈아가며 saveAll 호출")
    @Test
    void saveAllIdentityAndNonIdentity() throws Exception {
        final int size = 5;
        final List<IdentityEntity> identityEntities = Stream.generate(IdentityEntity::of)
                .limit(size)
                .collect(Collectors.toList());

        final List<NonIdentityEntity> nonIdentityEntities = LongStream.rangeClosed(1, size)
                .mapToObj(NonIdentityEntity::of)
                .collect(Collectors.toList());

        nonIdentityEntityRepository.saveAll(nonIdentityEntities);
        identityEntityRepository.saveAll(identityEntities);
    }

    @DisplayName("자동증가 아닌 엔티티 번갈아가며 한건씩 save 호출")
    @Test
    void saveNonIdentityAndUUID() throws Exception {
        final int size = 5;
        final List<NonIdentityEntity> nonIdentityEntities = LongStream.rangeClosed(1, size)
                .mapToObj(NonIdentityEntity::of)
                .collect(Collectors.toList());

        final List<UUIDEntity> uuidEntities = Stream.generate(UUIDEntity::of)
                .limit(size)
                .collect(Collectors.toList());

        for (int i = 0; i < size; i++) {
            uuidEntityRepository.save(uuidEntities.get(i));
            nonIdentityEntityRepository.save(nonIdentityEntities.get(i));
        }

        flush();
    }

    @DisplayName("자동증가 아닌 엔티티 번갈아가며 saveAll 호출")
    @Test
    void saveAllNonIdentityAndUUID() throws Exception {
        final int size = 5;
        final List<NonIdentityEntity> nonIdentityEntities = LongStream.rangeClosed(1, size)
                .mapToObj(NonIdentityEntity::of)
                .collect(Collectors.toList());

        final List<UUIDEntity> uuidEntities = Stream.generate(UUIDEntity::of)
                .limit(size)
                .collect(Collectors.toList());

        nonIdentityEntityRepository.saveAll(nonIdentityEntities);
        uuidEntityRepository.saveAll(uuidEntities);

        flush();
    }

    @DisplayName("자동증가 아닌 엔티티 번갈아가며 절반씩 saveAll 호출")
    @Test
    void saveAllNonIdentityAndUUID2() throws Exception {
        final int size = 10;
        final Map<Boolean, List<NonIdentityEntity>> nonIdentityEntities = LongStream.rangeClosed(1, size)
                .mapToObj(NonIdentityEntity::of)
                .collect(Collectors.collectingAndThen(Collectors.toList(), this::splitByOdd));

        final Map<Boolean, List<UUIDEntity>> uuidEntities = Stream.generate(UUIDEntity::of)
                .limit(size)
                .collect(Collectors.collectingAndThen(Collectors.toList(), this::splitByOdd));


        nonIdentityEntityRepository.saveAll(nonIdentityEntities.get(true));
        uuidEntityRepository.saveAll(uuidEntities.get(true));

        nonIdentityEntityRepository.saveAll(nonIdentityEntities.get(false));
        uuidEntityRepository.saveAll(uuidEntities.get(false));

        flush();
    }

    private  <T> Map<Boolean, List<T>> splitByOdd(List<T> inputs) {
        final AtomicInteger counter = new AtomicInteger(0);
        return inputs.stream()
                .collect(Collectors.groupingBy(s -> counter.getAndIncrement() % 2 == 0));
    }

    @DisplayName("자동증가 엔티티와 자동증가 아닌 엔티티 업데이트")
    @Test
    void updateAllIdentityEntityAndNonIdentityEntity() throws Exception {
        final int size = 4;
        insertTestValues(INSERT_IDENTITY, identityParameters(size));
        insertTestValues(INSERT_NON_IDENTITY, nonIdentityParameters(size));

        final List<IdentityEntity> identityEntities = identityEntityRepository.findAll();
        identityEntities.forEach(IdentityEntity::plus);

        final List<NonIdentityEntity> nonIdentityEntities = nonIdentityEntityRepository.findAll();
        nonIdentityEntities.forEach(NonIdentityEntity::plus);

        flush();
    }

}
