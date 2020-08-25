package woowa.hibernatebatch.single.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.DigestUtils;

import javax.persistence.*;
import java.util.Base64;
import java.util.UUID;

@Getter
@Entity
@Table(name = "uuid")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UUIDEntity {

    @Id
    @EqualsAndHashCode.Include
    @GenericGenerator(name = "UUID_generator", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID_generator")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID id;

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

    @Column(name = "c6", nullable = false)
    private String c6;

    @Column(name = "c7", nullable = false)
    private String c7;

    @Column(name = "c8", nullable = false)
    private String c8;

    @Column(name = "c9", nullable = false)
    private String c9;

    @Column(name = "c10", nullable = false)
    private String c10;

    @Column(name = "c11", nullable = false)
    private String c11;

    @Column(name = "c12", nullable = false)
    private String c12;

    @Column(name = "c13", nullable = false)
    private String c13;

    @Column(name = "c14", nullable = false)
    private String c14;

    @Column(name = "c15", nullable = false)
    private String c15;

    public static UUIDEntity of() {
        final UUIDEntity entity = new UUIDEntity();
        entity.c1 = 1;
        entity.c2 = randomString();
        entity.c3 = randomString();
        entity.c4 = randomString();
        entity.c5 = randomString();
        entity.c6 = randomString();
        entity.c7 = randomString();
        entity.c8 = randomString();
        entity.c9 = randomString();
        entity.c10 = randomString();
        entity.c11 = randomString();
        entity.c12 = randomString();
        entity.c13 = randomString();
        entity.c14 = randomString();
        entity.c15 = randomString();
        return entity;
    }

    public void plus() {
        this.c1++;
        this.c2 = randomString();
        this.c3 = randomString();
        this.c4 = randomString();
        this.c5 = randomString();
        this.c6 = randomString();
        this.c7 = randomString();
        this.c8 = randomString();
        this.c9 = randomString();
        this.c10 = randomString();
        this.c11 = randomString();
        this.c12 = randomString();
        this.c13 = randomString();
        this.c14 = randomString();
        this.c15 = randomString();
    }

    public static String randomString() {
        String random = UUID.randomUUID().toString();
        return random.repeat(7);
    }

}
