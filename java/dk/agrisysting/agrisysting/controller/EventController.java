package dk.agrisysting.agrisysting.controller;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.HelloApplication;
import dk.agrisysting.agrisysting.model.Event;
import dk.agrisysting.agrisysting.service.EventService;

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

//Controlleren styrer event-view.fxml
//Den henter hændelser fra databasen og viser dem i en TableView
public class EventController
{
    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, Number> eventIdColumn;

    @FXML
    private TableColumn<Event, Number> animalIdColumn;

    @FXML
    private TableColumn<Event, String> eventTypeColumn;

    @FXML
    private TableColumn<Event, String> eventDescriptionColumn;

    @FXML
    private TableColumn<Event, String> eventDateColumn;

    @FXML
    private Label messageLabel;

    private EventService eventService;

    public EventController()
    {
        eventService = new EventService();
    }

    @FXML
    private void initialize()
    //Initialize kører automatisk når FXML siden åbnes
    {
        setupTableColumns();

        loadEvents();
    }

    private void setupTableColumns()
    //Her bestemmer vi hvilke data fra Event objectet der skal vises i tabellen
    {
        eventIdColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getEventId()));

        animalIdColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getAnimalId()));

        eventTypeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEventType()));

        eventDescriptionColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEventDescription()));

        eventDateColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEventDate()));
    }

    @FXML
    private void loadEvents()
    //Denne metode henter alle events fra databasen og viser dem i tabellen
    {
        ArrayList<Event> events = eventService.hentAlleEvents();

        ObservableList<Event> eventObservableList = FXCollections.observableArrayList(events);

        eventTable.setItems(eventObservableList);

        messageLabel.setText("Antal hændelser vist: " + events.size());
    }

    @FXML
    private void handleBackToDashboard()
    //Denne metode sender brugeren tilbage til hovedmenuen
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 450);

            Stage stage = (Stage) eventTable.getScene().getWindow();

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