package dk.agrisysting.agrisysting.controller;
//Mappen filen ligger i.

import dk.agrisysting.agrisysting.HelloApplication;
import dk.agrisysting.agrisysting.service.AnalysisService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.stage.Stage;

//Controlleren styrer analysis-view.fxml
//Her viser jeg simple analyser & nøgletal ud fra grisedata i databasen
public class AnalysisController
{
    @FXML
    private Label totalAnimalsLabel;

    @FXML
    private Label activeAnimalsLabel;

    @FXML
    private Label inactiveAnimalsLabel;

    @FXML
    private Label averageFcrLabel;

    @FXML
    private Label averageStartWeightLabel;

    @FXML
    private Label averageEndWeightLabel;

    @FXML
    private Label messageLabel;
    //@FXML betyder at JavaFX kobler felterne sammen med analysis-view.fxml.
    //Label bruges til at vise tekst i brugergrænsefladen

    private AnalysisService analysisService;
    //Jeg bruger AnalysisService som mellemled mellem controller og repository
    //Controlleren snakker derfor ikke "direkte" med databasen

    public AnalysisController()
    {
        analysisService = new AnalysisService();
        //Her opretter jeg min service så controlleren kan hente analysedata
    }

    @FXML
    private void initialize()
    //initialize() kører automatisk når JavaFX loader siden
    {
        loadAnalysis();
        //Når siden åbnes henter jeg automatisk analyserne
    }

    @FXML
    private void loadAnalysis()
    //Denne metode henter analysedata og viser det på siden
    {
        int totalAnimals = analysisService.getTotalAnimals();
        int activeAnimals = analysisService.getActiveAnimals();
        int inactiveAnimals = analysisService.getInactiveAnimals();

        double averageFcr = analysisService.getAverageFcr();
        double averageStartWeight = analysisService.getAverageStartWeight();
        double averageEndWeight = analysisService.getAverageEndWeight();

        //Her henter jeg data fra AnalysisService

        totalAnimalsLabel.setText("Antal grise i alt: " + totalAnimals);
        activeAnimalsLabel.setText("Aktive grise: " + activeAnimals);
        inactiveAnimalsLabel.setText("Inaktive grise: " + inactiveAnimals);

        averageFcrLabel.setText("Gennemsnitlig FCR: " + String.format("%.2f", averageFcr));
        averageStartWeightLabel.setText("Gennemsnitlig startvægt: " + String.format("%.2f", averageStartWeight) + " kg");
        averageEndWeightLabel.setText("Gennemsnitlig slutvægt: " + String.format("%.2f", averageEndWeight) + " kg");

        //setText bliver brugt til at ændre teksten i labels
        //String.format("%.2f") gør at decimaler vises med 2 decimaler

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Analyse opdateret");
    }

    @FXML
    private void handleBackToDashboard()
    //Denne metode skulle sende brugeren tilbage til dashboardet
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
            //FXMLLoader loader dashboard-view.fxml ind

            Scene scene = new Scene(fxmlLoader.load(), 600, 550);
            //Scene er vel bare det indhold som vises i vinduet

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            //Stage er vel bare selve programvinduet

            stage.setTitle("Agrisys PPT Hovedmenu");
            stage.setScene(scene);
        }

        catch (Exception e)
        //Hvis noget går galt så skal det her ske, men det er uklart.
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke åbne hovedmenu");

            e.printStackTrace();
            //Printer fejlen i consollen så jeg kan debugge.
        }
    }
}

//@FXML = Kobler Java kode sammen med JavaFX FXML filer
//Label = UI element som viser tekst
//Scene = Indholdet i et vindue
//Stage = Selve programvinduet
//FXMLLoader = Bruger jeg til at åbne nye JavaFX sider
//Service = Laget mellem controller & database/repository
//String.format("%.2f") = Viser tal med 2 decimaler