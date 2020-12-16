package dev.maxuz.kwh.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "article")
@Data
public class Article {
    @Id
    @SequenceGenerator(name = "article_id_seq", sequenceName = "article_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "article_id_seq")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @JoinColumn(name = "category_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;

    @Column(name = "creation_date_time", nullable = false)
    private LocalDateTime creationDateTime;

    @Column(name = "update_date_time")
    private LocalDateTime updateDateTime;

    @Column(name = "deleted")
    private Boolean deleted;
}
