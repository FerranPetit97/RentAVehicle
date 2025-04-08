import user.admin.AdminController;

public class Main {

    private AdminController adminController;

    public void main(String[] args) {

        adminController = new AdminController();

        System.out.println(adminController.registerUser("Ferran", "ferran@gmail.com", "123456", "admin"));
        System.out.println(adminController.registerUser("Antonio", "antonio@gmail.com", "123456", "manager"));

        System.out.println(adminController.getAllUsers());

    }
}