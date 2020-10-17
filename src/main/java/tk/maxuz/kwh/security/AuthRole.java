package tk.maxuz.kwh.security;

public enum AuthRole {
  USER("USER");

  private final String name;
  AuthRole(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
