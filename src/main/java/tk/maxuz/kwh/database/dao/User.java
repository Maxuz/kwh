package tk.maxuz.kwh.database.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "app_user")
public class User {
  @Id
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;
}