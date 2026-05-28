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

/*
Controlleren styrer visit-view.fxml
Her viser jeg visits og målinger fra databasen i en TableView
Et visit er når en gris har været ved PPT enheden og der er registreret data
*/
public class VisitController
{
    @FXML
    private TableView<Visit> visitTable;
    //TableView er selve tabellen hvor visits bliver vist

    @FXML
    private TableColumn<Visit, Number> visitIdColumn;
    //Kolonne til VisitId

    @FXML
    private TableColumn<Visit, Number> animalIdColumn;
    //Kolonne til AnimalId så jeg kan se hvilken gris besøget hører til

    @FXML
    private TableColumn<Visit, String> visitTimeColumn;
    //Kolonne til tidspunktet for besøget

    @FXML
    private TableColumn<Visit, Number> durationColumn;
    //Kolonne til hvor lang tid besøget varede

    @FXML
    private TableColumn<Visit, Number> weightColumn;
    //Kolonne til grisens vægt i gram

    @FXML
    private TableColumn<Visit, Number> feedIntakeColumn;
    //Kolonne til hvor meget foder grisen har spist

    @FXML
    private Label messageLabel;
    //Label bruges til at vise beskeder til brugeren

    private VisitService visitService;
    //VisitService bruges som mellemled mellem controller og repository

    public VisitController()
    //Constructor kører når controlleren oprettes
    {
        visitService = new VisitService();
        //Her opretter jeg min VisitService så jeg kan hente visits
    }

    @FXML
    private void initialize()
    /*
    Initialize kører automatisk når FXML siden åbnes
    Her sætter jeg tabellens kolonner op
    Derefter henter jeg alle visits fra databasen
    */
    {
        setupTableColumns();

        loadVisits();
    }

    private void setupTableColumns()
    /*
    Her bestemmer jeg hvilke data fra Visit objectet der skal vises i tabellen
    Hver kolonne kobles til en getter fra Visit klassen
    */
    {
        visitIdColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getVisitId()));
        //Henter VisitId fra Visit objectet

        animalIdColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getAnimalId()));
        //Henter AnimalId fra Visit objectet

        visitTimeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getVisitTime()));
        //Henter tidspunktet for besøget

        durationColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getDurationSeconds()));
        //Henter varigheden af besøget i sekunder

        weightColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getWeightGram()));
        //Henter grisens vægt i gram

        feedIntakeColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getFeedIntakeGram()));
        //Henter foderindtag i gram
    }

    @FXML
    private void loadVisits()
    /*
    Denne metode henter alle visits gennem VisitService
    Listen laves om til en ObservableList
    Derefter bliver listen sat ind i tabellen
    */
    {
        ArrayList<Visit> visits = visitService.hentAlleVisits();
        //Her henter jeg alle visits fra databasen gennem service laget

        ObservableList<Visit> visitObservableList =
                FXCollections.observableArrayList(visits);
        //TableView arbejder bedst med ObservableList

        visitTable.setItems(visitObservableList);
        //Her viser jeg listen i tabellen

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Antal visits visit" + visits.size());
    }

    @FXML
    private void handleBackToDashboard()
    //Denne metode sender mig tilbage til hovedmenuen
    {
        try
        {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
            //Her fortæller jeg JavaFX hvilken FXML fil der skal åbnes

            Scene scene = new Scene(fxmlLoader.load(), 600, 550);
            //Her loader jeg FXML filen og laver den om til en Scene

            Stage stage = (Stage) visitTable.getScene().getWindow();
            //Her finder jeg det nuværende vindue ud fra tabellen

            stage.setTitle("Agrisys PPT Hovedmenu");
            //Ændrer vinduets titel

            stage.setScene(scene);
            //Skifter scenen til dashboardet
        }
        catch (Exception e)
        /*
        Hvis dashboardet ikke kan åbnes, kommer programmet herned
        Det kan fx være hvis FXML filen mangler eller har fejl
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
TableView = Tabellen i JavaFX
TableColumn = En kolonne i tabellen
ObservableList = En liste som JavaFX kan vise i UI
FXCollections = Hjælper med at lave en ObservableList
setCellValueFactory = Bestemmer hvad en kolonne skal vise
Service = Mellemled mellem controller og repository/database
FXMLLoader = Bruges til at åbne en ny FXML side
Scene = Indholdet i vinduet
Stage = Selve vinduet
try/catch = Bruges når noget kan gå galt
*/