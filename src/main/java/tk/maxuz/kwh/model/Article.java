package tk.maxuz.kwh.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "articles")
@Data
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;
}
