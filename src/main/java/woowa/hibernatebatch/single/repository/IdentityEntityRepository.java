package woowa.hibernatebatch.single.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import woowa.hibernatebatch.single.model.IdentityEntity;

public interface IdentityEntityRepository extends JpaRepository<IdentityEntity, Long> {

    @Query("SELECT ie FROM IdentityEntity ie ORDER BY ie.id")
    Slice<IdentityEntity> findAllIdentityEntities(Pageable pageable);

}
