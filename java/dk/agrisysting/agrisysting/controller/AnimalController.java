package dk.agrisysting.agrisysting.controller;
//Mappen filen ligger i.

import dk.agrisysting.agrisysting.HelloApplication;
import dk.agrisysting.agrisysting.model.Animal;
import dk.agrisysting.agrisysting.repository.AnimalRepository;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.util.ArrayList;

//Controlleren styrer animals-view.fxml.
//Den henter grise fra databasen og viser dem i en TableView.
//Den kan også filtrere grise og stoppe registrering af en gris.
public class AnimalController
{
    @FXML
    private TableView<Animal> animalTable;

    @FXML
    private TableColumn<Animal, String> animalNumberColumn;

    @FXML
    private TableColumn<Animal, String> responderColumn;

    @FXML
    private TableColumn<Animal, String> groupNameColumn;

    @FXML
    private TableColumn<Animal, String> locationNameColumn;

    @FXML
    private TableColumn<Animal, Number> startWeightColumn;

    @FXML
    private TableColumn<Animal, Number> endWeightColumn;

    @FXML
    private TableColumn<Animal, Number> feedIntakeColumn;

    @FXML
    private TableColumn<Animal, Number> fcrColumn;

    @FXML
    private TableColumn<Animal, String> activeColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Label messageLabel;

    private AnimalRepository animalRepository;

    private ArrayList<Animal> alleAnimals;

    public AnimalController()
    {
        animalRepository = new AnimalRepository();

        alleAnimals = new ArrayList<>();
    }

    @FXML
    private void initialize()
    {
        setupTableColumns();

        loadAnimals();
    }

    private void setupTableColumns()
    {
        animalNumberColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getAnimalNumber()));

        responderColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getResponder()));

        groupNameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getGroupName()));

        locationNameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getLocationName()));

        startWeightColumn.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getStartWeightKg()));

        endWeightColumn.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getEndWeightKg()));

        feedIntakeColumn.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getTotalFeedIntakeKg()));

        fcrColumn.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getFcr()));

        activeColumn.setCellValueFactory(data ->
        {
            if (data.getValue().isActive())
            {
                return new SimpleStringProperty("Aktiv");
            }
            else
            {
                return new SimpleStringProperty("Inaktiv");
            }
        });
    }

    @FXML
    private void loadAnimals()
    {
        alleAnimals = animalRepository.hentAlleAnimals();

        ObservableList<Animal> animalObservableList = FXCollections.observableArrayList(alleAnimals);

        animalTable.setItems(animalObservableList);

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Antal grise vist: " + alleAnimals.size());
    }

    @FXML
    private void handleSearch()
    {
        String searchText = searchField.getText().toLowerCase();

        ArrayList<Animal> filteredAnimals = new ArrayList<>();

        for (Animal animal : alleAnimals)
        {
            String animalNumber = animal.getAnimalNumber().toLowerCase();
            String groupName = animal.getGroupName().toLowerCase();
            String locationName = animal.getLocationName().toLowerCase();
            String responder = animal.getResponder().toLowerCase();

            if (
                    animalNumber.contains(searchText) ||
                            groupName.contains(searchText) ||
                            locationName.contains(searchText) ||
                            responder.contains(searchText)
            )
            {
                filteredAnimals.add(animal);
            }
        }

        ObservableList<Animal> animalObservableList = FXCollections.observableArrayList(filteredAnimals);

        animalTable.setItems(animalObservableList);

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Filter viser: " + filteredAnimals.size() + " grise.");
    }

    @FXML
    private void handleClearFilter()
    {
        searchField.clear();

        ObservableList<Animal> animalObservableList = FXCollections.observableArrayList(alleAnimals);

        animalTable.setItems(animalObservableList);

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Filter nulstillet. Antal grise vist: " + alleAnimals.size());
    }

    @FXML
    private void handleStopRegistration()
    //Denne metode stopper registrering af den gris brugeren har valgt i tabellen
    {
        Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();
        //Her henter vi den gris som brugeren har markeret i tabellen

        if (selectedAnimal == null)
        //Hvis brugeren ikke har valgt en gris
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Vælg en gris først.");

            return;
        }

        if (!selectedAnimal.isActive())
        //Hvis grisen allerede er inaktiv
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Denne gris er allerede inaktiv.");

            return;
        }

        boolean success = animalRepository.stopRegistrering(selectedAnimal.getAnimalId());
        //Her kalder vi repository-metoden som opdaterer databasen

        if (success)
        {
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Registrering stoppet for gris: " + selectedAnimal.getAnimalNumber());

            loadAnimals();
            //Opdaterer listen, så status ændres til Inaktiv
        }
        else
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke stoppe registrering.");
        }
    }

    @FXML
    private void handleBackToDashboard()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 500);

            Stage stage = (Stage) animalTable.getScene().getWindow();

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

    @FXML
    private void handleOpenCreateAnimal()
    //Denne metode åbner siden hvor brugeren kan oprette en ny gris
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-animal-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 500, 650);

            Stage stage = (Stage) animalTable.getScene().getWindow();

            stage.setTitle("Agrisys PPT - Opret gris");
            stage.setScene(scene);
        }
        catch (Exception e)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke åbne opret gris.");

            e.printStackTrace();
        }
    }

}