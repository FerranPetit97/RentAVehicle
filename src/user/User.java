package user;

public class User {
  private String id;
  private String name;
  private String email;
  private String password;
  private String role;

  public User(int id, String name, String email, String password, String role) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public int getId() {
    return Integer.parseInt(id);
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getRole() {
    return role;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User{id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
        + ", role=" + role + "}";
  }

}