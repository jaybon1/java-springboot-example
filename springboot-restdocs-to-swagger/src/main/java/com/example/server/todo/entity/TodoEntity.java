package com.example.server.todo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TODO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idx", callSuper = false)
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Integer idx;

    @Column(name="content")
    private String content;

    @Column(name="done_yn")
    private Character doneYn;

    @Column(name="delete_yn")
    private Character deleteYn;

    @Column(name="create_date")
    private LocalDateTime createDate;

    @Column(name="update_date")
    private LocalDateTime updateDate;

    @Column(name="delete_date")
    private LocalDateTime deleteDate;


    public void setDoneYn(Character doneYn) {
        this.doneYn = doneYn;
    }

    public void setDeleteYn(Character deleteYn) {
        this.deleteYn = deleteYn;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public void setDeleteDate(LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }
}
