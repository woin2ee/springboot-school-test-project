package iducs.springboot.yjwboard.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board202012069")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;
    private String content;

    private Long views;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity writer;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void addOneToViews() {
        if (views == null) { views = 0L; }
        views += 1;
    }
}
