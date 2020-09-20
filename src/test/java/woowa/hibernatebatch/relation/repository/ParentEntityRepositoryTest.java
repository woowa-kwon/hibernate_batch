package woowa.hibernatebatch.relation.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import woowa.hibernatebatch.TestSupport;
import woowa.hibernatebatch.relation.model.ChildEntity;
import woowa.hibernatebatch.relation.model.ParentEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParentEntityRepositoryTest extends TestSupport {

    @Autowired
    private ParentEntityRepository parentRepository;

    @Commit
    @BeforeEach
    void setup() {
        truncate();
    }

    @Commit
    @AfterEach
    void tearDown() {
        truncate();
    }

    private void truncate() {
        truncate("child");
        truncate("parent");
    }

    @Test
    void save() throws Exception {
        final ParentEntity parent = ParentEntity.of(1L);
        final ChildEntity child1 = ChildEntity.of(1L, parent);
        final ChildEntity child2 = ChildEntity.of(2L, parent);
        parent.addChild(child1);
        parent.addChild(child2);

        final ParentEntity save = parentRepository.save(parent);

        final ParentEntity find = parentRepository.findById(save.getId()).get();
        assertThat(find).isEqualTo(save);

        final List<ChildEntity> childs = find.getChilds();
        final ChildEntity findChild1 = childs.get(0);
        final ChildEntity findChild2 = childs.get(1);
        assertThat(findChild1).isEqualTo(child1);
        assertThat(findChild2).isEqualTo(child2);

        assertThat(findChild1.getParent()).isEqualTo(save);
        assertThat(findChild2.getParent()).isEqualTo(save);
    }

    @DisplayName("생성된 엔티티마다 save 호출")
    @Test
    void saveAll() throws Exception {
        long childId = 1;
        for (long i = 1; i <= 5; i++) {
            final ParentEntity parent = ParentEntity.of(i);
            for (long j = 0; j < 3; j++) {
                final ChildEntity child = ChildEntity.of(childId, parent);
                parent.addChild(child);
                childId++;
            }
            parentRepository.save(parent);
        }
        flush();
    }

    @DisplayName("생성된 엔티티를 리스트에 담아서 saveAll 호출")
    @Test
    void saveAll2() throws Exception {
        long childId = 1;
        List<ParentEntity> result = new ArrayList<>();
        for (long i = 1; i <= 5; i++) {
            final ParentEntity parent = ParentEntity.of(i);
            for (long j = 0; j < 3; j++) {
                final ChildEntity child = ChildEntity.of(childId, parent);
                parent.addChild(child);
                childId++;
            }
            result.add(parent);
        }
        parentRepository.saveAll(result);
        flush();
    }

    @Test
    void updateAll() throws Exception {
        final int parentSize = 5;
        insertTestValues(INSERT_PARENT, parentParameters(parentSize));
        insertTestValues(INSERT_CHILD, childParameters(parentSize, 4));

        final List<ParentEntity> parents = parentRepository.findAll();
        parents.forEach(ParentEntity::plus);

        flush();
    }

    @Test
    void updateAll2() throws Exception {
        final int parentSize = 5;
        insertTestValues(INSERT_PARENT, parentParameters(parentSize));
        insertTestValues(INSERT_CHILD, childParameters(parentSize, 4));

        final List<ParentEntity> parents = parentRepository.findAllParentAndChild();
        parents.forEach(ParentEntity::plus);

        flush();
    }

}