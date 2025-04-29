package utils;

import user.User;

public class Authorization {

  public static boolean isAdmin(User user) {
    return user != null && "admin".equalsIgnoreCase(user.getRole());
  }

  public static boolean isManager(User user) {
    return user != null && "manager".equalsIgnoreCase(user.getRole());
  }

  public static boolean isMechanic(User user) {
    return user != null && "mechanic".equalsIgnoreCase(user.getRole());
  }

  public static boolean isClient(User user) {
    return user != null && "client".equalsIgnoreCase(user.getRole());
  }

  public static boolean isPremium(User user) {
    return user != null && "premium".equalsIgnoreCase(user.getRole());
  }

  public static void checkPermission(User user, String requiredRole) {
    if (user == null || !requiredRole.equalsIgnoreCase(user.getRole())) {
      throw new SecurityException("Permission denied: User does not have the required role (" + requiredRole + ").");
    }
  }
}