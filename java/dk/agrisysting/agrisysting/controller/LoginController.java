package dk.agrisysting.agrisysting.controller;
//Mappen filen ligger i.

import dk.agrisysting.agrisysting.HelloApplication;
import dk.agrisysting.agrisysting.Andet.Session;
import dk.agrisysting.agrisysting.model.User;
import dk.agrisysting.agrisysting.service.LoginService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Controlleren styrer login-view.fxml.
//Den reagerer på knappen i brugerfladen.
public class LoginController
{
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private LoginService loginService;

    public LoginController()
    {
        loginService = new LoginService();
    }

    @FXML
    private void handleLogin()
    {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = loginService.login(username, password);

        if (user != null)
        {
            //Her gemmer vi brugeren i Session
            //Så dashboardet kan se om brugeren er Admin, Landmand eller Rådgiver
            Session.setCurrentUser(user);

            System.out.println("Login OK:");
            System.out.println(user.getUsername() + " - " + user.getRole());

            openDashboard();
        }
        else
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Forkert brugernavn eller adgangskode.");
        }
    }

    private void openDashboard()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 550);

            Stage stage = (Stage) usernameField.getScene().getWindow();

            stage.setTitle("Agrisys PPT - Hovedmenu");
            stage.setScene(scene);
        }
        catch (Exception e)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke åbne hovedmenu.");

            e.printStackTrace();
        }
    }
}