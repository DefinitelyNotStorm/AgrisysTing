package dk.agrisysting.agrisysting.controller;
//Mappen filen ligger i.

import dk.agrisysting.agrisysting.HelloApplication;
import dk.agrisysting.agrisysting.Andet.Session;
import dk.agrisysting.agrisysting.model.User;
import dk.agrisysting.agrisysting.service.ExportService;
import dk.agrisysting.agrisysting.service.ImportService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

//Controlleren styrer dashboard-view.fxml.
//Dashboardet fungerer som programmets hovedmenu.
public class DashboardController
{
    @FXML
    private Label messageLabel;

    @FXML
    private Label userLabel;

    @FXML
    private Button animalsButton;

    @FXML
    private Button visitsButton;

    @FXML
    private Button eventsButton;

    @FXML
    private Button importButton;

    @FXML
    private Button templateButton;

    @FXML
    private Button exportButton;

    private ExportService exportService;
    private ImportService importService;

    public DashboardController()
    {
        exportService = new ExportService();
        importService = new ImportService();
    }

    @FXML
    private void initialize()
    //Initialize kører automatisk når dashboardet åbnes
    {
        setupRoleAccess();
    }

    private void setupRoleAccess()
    //Denne metode bestemmer hvilke knapper brugeren må se
    {
        User user = Session.getCurrentUser();

        if (user == null)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Ingen bruger er logget ind.");
            return;
        }

        String role = user.getRole();

        userLabel.setText("Logget ind som: " + user.getUsername() + " (" + role + ")");

        if (role.equalsIgnoreCase("Admin"))
        {
            //Admin må importere og eksportere
            animalsButton.setVisible(false);
            visitsButton.setVisible(false);
            eventsButton.setVisible(false);

            importButton.setVisible(true);
            templateButton.setVisible(true);
            exportButton.setVisible(true);
        }
        else if (role.equalsIgnoreCase("Landmand"))
        {
            //Landmand må arbejde med grise og se data
            animalsButton.setVisible(true);
            visitsButton.setVisible(true);
            eventsButton.setVisible(true);

            importButton.setVisible(false);
            templateButton.setVisible(false);
            exportButton.setVisible(false);
        }
        else if (role.equalsIgnoreCase("Rådgiver") || role.equalsIgnoreCase("Raadgiver"))
        {
            //Rådgiver må se data, men ikke importere/eksportere
            animalsButton.setVisible(true);
            visitsButton.setVisible(true);
            eventsButton.setVisible(true);

            importButton.setVisible(false);
            templateButton.setVisible(false);
            exportButton.setVisible(false);
        }
        else
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Ukendt rolle: " + role);
        }
    }

    @FXML
    private void handleAnimals()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("animals-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 950, 600);

            Stage stage = (Stage) messageLabel.getScene().getWindow();

            stage.setTitle("Agrisys PPT - Griseoversigt");
            stage.setScene(scene);
        }
        catch (Exception e)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke åbne griseoversigt.");

            e.printStackTrace();
        }
    }

    @FXML
    private void handleVisits()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("visit-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 950, 600);

            Stage stage = (Stage) messageLabel.getScene().getWindow();

            stage.setTitle("Agrisys PPT - Visits / Målinger");
            stage.setScene(scene);
        }
        catch (Exception e)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke åbne visits.");

            e.printStackTrace();
        }
    }

    @FXML
    private void handleEvents()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("event-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 950, 600);

            Stage stage = (Stage) messageLabel.getScene().getWindow();

            stage.setTitle("Agrisys PPT - Hændelser");
            stage.setScene(scene);
        }
        catch (Exception e)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke åbne hændelser.");

            e.printStackTrace();
        }
    }

    @FXML
    private void handleImport()
    {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Vælg CSV-fil til import");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV filer", "*.csv")
        );

        Stage stage = (Stage) messageLabel.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile == null)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Ingen fil valgt.");
            return;
        }

        int importedCount = importService.importAnimalsFromCsv(selectedFile.getAbsolutePath());

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Import færdig. Antal importerede grise: " + importedCount);
    }

    @FXML
    private void handleGenerateTemplate()
    //Denne metode laver en tom importskabelon
    {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Gem importskabelon");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV filer", "*.csv")
        );

        fileChooser.setInitialFileName("agrisys_import_skabelon.csv");

        Stage stage = (Stage) messageLabel.getScene().getWindow();

        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile == null)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Skabelon blev ikke gemt.");
            return;
        }

        boolean success = exportService.generateImportTemplate(selectedFile.getAbsolutePath());

        if (success)
        {
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Importskabelon gemt: " + selectedFile.getName());
        }
        else
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke gemme importskabelon.");
        }
    }

    @FXML
    private void handleAnalysis()
    //Åbner analyse siden
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("analysis-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 700, 500);

            Stage stage = (Stage) messageLabel.getScene().getWindow();

            stage.setTitle("Agrisys PPT - Analyse");
            stage.setScene(scene);
        }
        catch (Exception e)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke åbne analyse.");

            e.printStackTrace();
        }
    }

    @FXML
    private void handleExport()
    {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Gem CSV-fil");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV filer", "*.csv")
        );

        fileChooser.setInitialFileName("agrisys_animals_export.csv");

        Stage stage = (Stage) messageLabel.getScene().getWindow();

        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile == null)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Eksport annulleret.");
            return;
        }

        boolean success = exportService.exportAnimalsToCsv(selectedFile.getAbsolutePath());

        if (success)
        {
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Eksport færdig: " + selectedFile.getName());
        }
        else
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Eksport fejlede.");
        }
    }

    @FXML
    private void handleLogout()
    {
        try
        {
            Session.clear();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 500, 400);

            Stage stage = (Stage) messageLabel.getScene().getWindow();

            stage.setTitle("Agrisys PPT - Login");
            stage.setScene(scene);
        }
        catch (Exception e)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke logge ud.");

            e.printStackTrace();
        }
    }
}