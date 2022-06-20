package iducs.springboot.yjwboard.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "member202012069")

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @Column(length = 30, nullable = false)
    private String id;
    @Column(length = 30, nullable = false)
    private String pw;
    @Column(length = 30, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 30, nullable = true)
    private String phone;
    @Column(length = 100)
    private String address;
    @Column(length = 5)
    private String level;
}
