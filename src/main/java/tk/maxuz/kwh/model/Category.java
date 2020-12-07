package tk.maxuz.kwh.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "category")
@Data
public class Category {
    @Id
    @SequenceGenerator(name = "category_id_seq", sequenceName = "category_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
