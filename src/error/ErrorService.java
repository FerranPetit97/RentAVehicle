package error;

public class ErrorService {

  public static void logError(String errorMessage) {
    System.err.println("Error: " + errorMessage);
  }

  public static void handleError(Exception e) {
    logError(e.getMessage());
  }
}
