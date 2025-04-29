package user;

public class UserFactory {
    private static UserController instance;

    public static synchronized UserController getInstance(User user) {
        if (instance == null) {
            UserService userService = new UserService(user);
            instance = new UserController(userService);
        }
        return instance;
    }
}
