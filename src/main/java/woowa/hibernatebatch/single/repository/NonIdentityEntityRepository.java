package woowa.hibernatebatch.single.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import woowa.hibernatebatch.single.model.NonIdentityEntity;

public interface NonIdentityEntityRepository extends JpaRepository<NonIdentityEntity, Long> {

    @Query("SELECT nie FROM NonIdentityEntity nie ORDER BY nie.id")
    Slice<NonIdentityEntity> findAllIdentityEntities(Pageable pageable);

}
