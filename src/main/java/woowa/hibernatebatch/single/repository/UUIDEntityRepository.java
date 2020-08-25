package woowa.hibernatebatch.single.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woowa.hibernatebatch.single.model.UUIDEntity;

import java.util.UUID;

public interface UUIDEntityRepository extends JpaRepository<UUIDEntity, UUID> {

}
