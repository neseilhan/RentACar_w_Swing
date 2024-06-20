import business.UserManager;
import core.Helper;
import entity.User;
import view.AdminView;
import view.LoginView;

public class App {
    public static void main(String[] args) {

        Helper.setTheme(); //calling the theme.
        LoginView loginView = new LoginView();
//        UserManager userManager = new UserManager();
//        AdminView adminView = new AdminView(userManager.findByLogin("admin","asd")); //its temporary usage.


    }
}
