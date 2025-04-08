package user;

import java.util.ArrayList;

public class UserService {
  private static final String DEFAULT_ROLE = "user";

  private static int INIT_INDEX = 1;

  ArrayList<User> users = new ArrayList<>();

  public User addUser(String username, String email, String password, String role) {
    if (username == null || email == null || password == null ||
        username.isEmpty() || email.isEmpty() || password.isEmpty()) {
      return null;
    }

    User user = new User(INIT_INDEX++, username, email, password, role != null ? role : DEFAULT_ROLE);

    saveTheUser(user);

    return user;
  }

  public ArrayList<User> getAllUsers() {
    return users;
  }

  private void saveTheUser(User user) {
    users.add(user);
  }

}