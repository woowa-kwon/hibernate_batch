package woowa.hibernatebatch.single.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "non_identity")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NonIdentityEntity {

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

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    public static NonIdentityEntity of(Long id) {
        NonIdentityEntity entity = new NonIdentityEntity();
        entity.id = id;
        entity.c1 = 1;
        entity.c2 = "c2-" + entity.c1;
        entity.c3 = "c3-" + entity.c1;
        entity.c4 = "c4-" + entity.c1;
        entity.c5 = "c5-" + entity.c1;
        return entity;
    }

    public void plus() {
        this.c1++;
        this.c2 = "c2-" + c1;
        this.c3 = "c3-" + c1;
        this.c4 = "c4-" + c1;
        this.c5 = "c5-" + c1;
    }

}
