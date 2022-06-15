package iducs.springboot.yjwboard.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long bno;
    private String title;
    private String content;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private Long writerSeq;
    private String writerId;
    private String writerName;
    private String writerEmail;

    private int replyCount;
}
