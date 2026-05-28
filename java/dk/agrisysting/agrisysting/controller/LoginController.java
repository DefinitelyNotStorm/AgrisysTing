package dk.agrisysting.agrisysting.controller;
//Mappen filen ligger i

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

/*
Controlleren styrer login-view.fxml
Her kan brugeren logge ind med brugernavn og adgangskode
Hvis login virker, gemmer jeg brugeren i Session og åbner dashboardet
*/
public class LoginController
{
    @FXML
    private TextField usernameField;
    //Tekstfeltet hvor brugeren skriver sit brugernavn

    @FXML
    private PasswordField passwordField;
    //PasswordField bruges til adgangskoden så teksten bliver skjult

    @FXML
    private Label messageLabel;
    //Label bruges til at vise fejlbeskeder på login siden

    private LoginService loginService;
    //LoginService bruges som mellemled mellem controller og UserRepository

    public LoginController()
    //Constructor kører når controlleren oprettes
    {
        loginService = new LoginService();
        //Her opretter jeg min loginService så jeg kan tjekke login
    }

    @FXML
    private void handleLogin()
    /*
    Denne metode kører når brugeren trykker på Log ind knappen
    Den henter tekst fra felterne og sender det videre til LoginService
    */
    {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = loginService.login(username, password);
        //Her prøver jeg at finde en bruger med det indtastede login

        if (user != null)
        /*
        Hvis user ikke er null, betyder det at login lykkedes
        Så gemmer jeg brugeren i Session og åbner dashboardet
        */
        {
            Session.setCurrentUser(user);
            //Her gemmer jeg brugeren så resten af programmet ved hvem der er logget ind

            System.out.println("Login OK:");
            System.out.println(user.getUsername() + " " + user.getRole());
            //Jeg printer login i consollen så jeg kan se at det virker

            openDashboard();
        }
        else
        /*
        Hvis user er null, betyder det at login fejlede
        Enten fordi brugernavn eller adgangskode er forkert
        */
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Forkert brugernavn eller adgangskode");
        }
    }

    private void openDashboard()
    /*
    Denne metode åbner dashboard-view.fxml
    Jeg har den som en separat metode for at holde handleLogin mere overskuelig
    */
    {
        try
        {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
            //Her fortæller jeg JavaFX hvilken FXML fil der skal åbnes

            Scene scene = new Scene(fxmlLoader.load(), 600, 550);
            //Her loader jeg FXML filen og laver den om til en Scene

            Stage stage = (Stage) usernameField.getScene().getWindow();
            //Her finder jeg det nuværende vindue ud fra usernameField

            stage.setTitle("Agrisys PPT Hovedmenu");
            //Ændrer vinduets titel

            stage.setScene(scene);
            //Skifter scenen til dashboardet
        }
        catch (Exception e)
        /*
        Hvis dashboardet ikke kan åbnes, kommer programmet herned
        Det kan fx være hvis FXML filen mangler eller har et forkert navn
        */
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke åbne hovedmenu");

            e.printStackTrace();
            //Printer fejlen i consollen så jeg kan finde problemet
        }
    }
}

/*
@FXML = Kobler Java kode sammen med FXML filen
TextField = Tekstfelt hvor brugeren kan skrive
PasswordField = Tekstfelt til adgangskoder, hvor teksten skjules
Label = Tekst i brugerfladen
Session = Bruges til at huske hvem der er logget ind
Service = Mellemled mellem controller og repository/database
FXMLLoader = Bruges til at åbne en ny FXML side
Scene = Indholdet i vinduet
Stage = Selve vinduet
null = Ingen værdi, bruges her når login fejler
try/catch = Bruges når noget kan gå galt
*/