package dk.agrisysting.agrisysting;

import dk.agrisysting.agrisysting.model.User;
import dk.agrisysting.agrisysting.service.LoginService;
import javafx.application.Application;

public class Launcher
{
    public static void main(String[] args)
    {
        LoginService loginService = new LoginService();

        User user = loginService.login("admin", "admin123");

        if (user != null)
        {
            System.out.println("Login via service OK:");
            System.out.println(user.getUsername() + " - " + user.getRole());
        }
        else
        {
            System.out.println("Login via service fejlede");
        }

        Application.launch(HelloApplication.class, args);
    }
}