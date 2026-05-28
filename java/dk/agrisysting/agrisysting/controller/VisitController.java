package dk.agrisysting.agrisysting.controller;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.HelloApplication;
import dk.agrisysting.agrisysting.model.Visit;
import dk.agrisysting.agrisysting.service.VisitService;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.stage.Stage;

import java.util.ArrayList;

//Controlleren styrer visit-view.fxml
//Den henter visits fra databasen og viser dem i en TableView
public class VisitController
{
    @FXML
    private TableView<Visit> visitTable;

    @FXML
    private TableColumn<Visit, Number> visitIdColumn;

    @FXML
    private TableColumn<Visit, Number> animalIdColumn;

    @FXML
    private TableColumn<Visit, String> visitTimeColumn;

    @FXML
    private TableColumn<Visit, Number> durationColumn;

    @FXML
    private TableColumn<Visit, Number> weightColumn;

    @FXML
    private TableColumn<Visit, Number> feedIntakeColumn;

    @FXML
    private Label messageLabel;

    private VisitService visitService;

    public VisitController()
    {
        visitService = new VisitService();
    }

    @FXML
    private void initialize()
    //Initialize kører automatisk når FXML siden åbnes
    {
        setupTableColumns();

        loadVisits();
    }

    private void setupTableColumns()
    //Her bestemmer vi hvilke data fra Visit objectet der skal vises i tabellen
    {
        visitIdColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getVisitId()));

        animalIdColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getAnimalId()));

        visitTimeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getVisitTime()));

        durationColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getDurationSeconds()));

        weightColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getWeightGram()));

        feedIntakeColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getFeedIntakeGram()));
    }

    @FXML
    private void loadVisits()
    //Denne metode henter alle visits fra databasen og viser dem i tabellen
    {
        ArrayList<Visit> visits = visitService.hentAlleVisits();

        ObservableList<Visit> visitObservableList = FXCollections.observableArrayList(visits);

        visitTable.setItems(visitObservableList);

        messageLabel.setText("Antal visits vist: " + visits.size());
    }

    @FXML
    private void handleBackToDashboard()
    //Denne metode sender brugeren tilbage til hovedmenuen
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 450);

            Stage stage = (Stage) visitTable.getScene().getWindow();

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