package woowa.hibernatebatch.relation.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "parent")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ParentEntity {

    @Id
    @Column(name = "id", updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "c1", nullable = false)
    private Integer c1;

    @Column(name = "c2", nullable = false)
    private String c2;

    @Column(name = "c3", nullable = false)
    private String c3;

    @Column(name = "c4", nullable = false)
    private String c4;

    @Column(name = "c5", nullable = false)
    private String c5;

    @ToString.Exclude
    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ChildEntity> childs = new ArrayList<>();

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    public static ParentEntity of(Long id) {
        ParentEntity entity = new ParentEntity();
        entity.id = id;
        entity.c1 = 1;
        entity.c2 = "c2-" + entity.c1;
        entity.c3 = "c3-" + entity.c1;
        entity.c4 = "c4-" + entity.c1;
        entity.c5 = "c5-" + entity.c1;
        return entity;
    }

    public void addChild(ChildEntity child) {
        childs.add(child);
    }

    public void plus() {
        this.c1++;
        this.c2 = "c2-" + c1;
        this.c3 = "c3-" + c1;
        this.c4 = "c4-" + c1;
        this.c5 = "c5-" + c1;

        childs.forEach(ChildEntity::plus);
    }

}
