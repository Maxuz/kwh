package tk.maxuz.kwh.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private CategoryDto category;
    private LocalDateTime creationDateTime;
    private LocalDateTime updateDateTime;
}
