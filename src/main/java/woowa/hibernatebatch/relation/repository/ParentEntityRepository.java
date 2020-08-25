package woowa.hibernatebatch.relation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import woowa.hibernatebatch.relation.model.ParentEntity;

public interface ParentEntityRepository extends JpaRepository<ParentEntity, Long> {

    @Query("SELECT DISTINCT p FROM ParentEntity p join fetch p.childs ORDER BY p.id")
    Slice<ParentEntity> findAllParent(Pageable pageable);

}
