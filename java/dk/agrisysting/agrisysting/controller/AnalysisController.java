package dk.agrisysting.agrisysting.controller;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.HelloApplication;
import dk.agrisysting.agrisysting.service.AnalysisService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.stage.Stage;

//Controlleren styrer analysis-view.fxml
//Her vises simple analyser ud fra grisedata i databasen
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

    private AnalysisService analysisService;

    public AnalysisController()
    {
        analysisService = new AnalysisService();
    }

    @FXML
    private void initialize()
    //Initialize kører automatisk når siden åbnes
    {
        loadAnalysis();
    }

    @FXML
    private void loadAnalysis()
    //Denne metode henter analyse-tal og viser dem på siden
    {
        int totalAnimals = analysisService.getTotalAnimals();
        int activeAnimals = analysisService.getActiveAnimals();
        int inactiveAnimals = analysisService.getInactiveAnimals();

        double averageFcr = analysisService.getAverageFcr();
        double averageStartWeight = analysisService.getAverageStartWeight();
        double averageEndWeight = analysisService.getAverageEndWeight();

        totalAnimalsLabel.setText("Antal grise i alt: " + totalAnimals);
        activeAnimalsLabel.setText("Aktive grise: " + activeAnimals);
        inactiveAnimalsLabel.setText("Inaktive grise: " + inactiveAnimals);

        averageFcrLabel.setText("Gennemsnitlig FCR: " + String.format("%.2f", averageFcr));
        averageStartWeightLabel.setText("Gennemsnitlig startvægt: " + String.format("%.2f", averageStartWeight) + " kg");
        averageEndWeightLabel.setText("Gennemsnitlig slutvægt: " + String.format("%.2f", averageEndWeight) + " kg");

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Analyse opdateret.");
    }

    @FXML
    private void handleBackToDashboard()
    //Denne metode sender brugeren tilbage til hovedmenuen
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 550);

            Stage stage = (Stage) messageLabel.getScene().getWindow();

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