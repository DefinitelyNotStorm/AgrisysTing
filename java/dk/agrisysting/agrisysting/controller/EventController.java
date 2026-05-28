package dk.agrisysting.agrisysting.controller;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.HelloApplication;
import dk.agrisysting.agrisysting.model.Event;
import dk.agrisysting.agrisysting.service.EventService;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
//SimpleIntegerProperty og SimpleStringProperty bruges til TableView
//Det gør at JavaFX kan læse værdierne fra mine Event objekter

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//ObservableList er en liste som JavaFX kan vise i en tabel
//FXCollections bruges til at lave en almindelig ArrayList om til en ObservableList

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//FXML bruges til at koble controlleren sammen med FXML filen
//FXMLLoader bruges når jeg skal åbne en anden FXML side

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
//Scene er det indhold der bliver vist i vinduet
//Label er tekst i brugerfladen
//TableColumn er en kolonne i tabellen
//TableView er selve tabellen

import javafx.stage.Stage;
//Stage er selve programvinduet

import java.util.ArrayList;
//ArrayList bruges til listen med events som kommer fra databasen

/*
Controlleren styrer event-view.fxml
Her viser jeg hændelser fra databasen i en TableView
En hændelse kan fx være sygdom, observation eller stop af registrering
*/
public class EventController
{
    @FXML
    private TableView<Event> eventTable;
    //TableView viser en liste af Event objekter

    @FXML
    private TableColumn<Event, Number> eventIdColumn;
    //Kolonne til EventId

    @FXML
    private TableColumn<Event, Number> animalIdColumn;
    //Kolonne til AnimalId, så jeg kan se hvilken gris hændelsen hører til

    @FXML
    private TableColumn<Event, String> eventTypeColumn;
    //Kolonne til typen af hændelse

    @FXML
    private TableColumn<Event, String> eventDescriptionColumn;
    //Kolonne til beskrivelsen af hændelsen

    @FXML
    private TableColumn<Event, String> eventDateColumn;
    //Kolonne til datoen for hændelsen

    @FXML
    private Label messageLabel;
    //Label bruges til at vise beskeder nederst på siden

    private EventService eventService;
    //Service laget bruges som mellemled mellem controller og repository

    public EventController()
    //Constructoren kører når controlleren bliver oprettet
    {
        eventService = new EventService();
        //Her opretter jeg min EventService, så jeg kan hente events
    }

    @FXML
    private void initialize()
    /*
    initialize kører automatisk når FXML siden åbnes
    Først sætter jeg kolonnerne op
    Derefter henter jeg events fra databasen
    */


    {
        setupTableColumns();

        loadEvents();
    }

    private void setupTableColumns()
    /*
    Her bestemmer jeg hvilke værdier fra Event objektet der skal vises i tabellen
    Uden denne metode ved JavaFX ikke hvad hver kolonne skal vise
    */
    {
        eventIdColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getEventId()));
        //Henter EventId fra Event objektet og viser det i Event ID kolonnen

        animalIdColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getAnimalId()));
        //Henter AnimalId, så jeg kan se hvilken gris eventet hører til

        eventTypeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEventType()));
        //Henter typen på hændelsen, fx sygdom eller observation

        eventDescriptionColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEventDescription()));
        //Henter beskrivelsen af hændelsen

        eventDateColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEventDate()));
        //Henter datoen for hændelsen
    }

    @FXML
    private void loadEvents()
    /*
    Denne metode henter alle events gennem EventService
    Derefter laver jeg listen om til en ObservableList
    Til sidst bliver listen sat ind i tabellen
    */
    {
        ArrayList<Event> events = eventService.hentAlleEvents();
        //Her henter jeg alle events fra databasen gennem service laget

        ObservableList<Event> eventObservableList = FXCollections.observableArrayList(events);
        //TableView kan bedst arbejde med ObservableList

        eventTable.setItems(eventObservableList);
        //Her sætter jeg listen ind i tabellen

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Antal hændelser vist " + events.size());
        //Viser hvor mange hændelser der er hentet
    }

    @FXML
    private void handleBackToDashboard()
    /*
    Denne metode sender brugeren tilbage til hovedmenuen
    Den bruges når man trykker på tilbage knappen
    */
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
            //Her fortæller jeg JavaFX hvilken FXML fil der skal åbnes

            Scene scene = new Scene(fxmlLoader.load(), 600, 450);
            //Her loader jeg FXML filen og laver den om til en Scene

            Stage stage = (Stage) eventTable.getScene().getWindow();
            //Her finder jeg det nuværende vindue ud fra tabellen

            stage.setTitle("Agrisys PPT Hovedmenu");
            //Ændrer titlen på vinduet

            stage.setScene(scene);
            //Skifter indholdet i vinduet til dashboardet
        }
        catch (Exception e)
        /*
        Hvis noget går galt med at åbne dashboardet lander programmet her
        Det kan fx være hvis FXML filen mangler eller navnet er skrevet forkert
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
ObservableList = En liste som JavaFX kan vise og opdatere i UI
FXCollections = Hjælper med at lave en ObservableList
setCellValueFactory = Bestemmer hvad en kolonne skal vise
FXMLLoader = Bruges til at åbne en ny FXML side
Scene = Indholdet i vinduet
Stage = Selve vinduet
Service = Mellemled mellem controller og repository/database
try/catch = Bruges når noget kan gå galt
*/