package dk.agrisysting.agrisysting.controller;
//Mappen filen ligger i

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

/*
Controlleren styrer animals-view.fxml
Den henter grise fra databasen & viser dem i en TableView
Den kan også filtrere grise, stoppe registrering og åbne opret gris siden
*/
public class AnimalController
{
    @FXML
    private TableView<Animal> animalTable;
    //TableView er selve tabellen hvor grisene bliver vist

    @FXML
    private TableColumn<Animal, String> animalNumberColumn;
    //Kolonne til dyrenummer

    @FXML
    private TableColumn<Animal, String> responderColumn;
    //Kolonne til responder

    @FXML
    private TableColumn<Animal, String> groupNameColumn;
    //Kolonne til gruppe

    @FXML
    private TableColumn<Animal, String> locationNameColumn;
    //Kolonne til lokation

    @FXML
    private TableColumn<Animal, Number> startWeightColumn;
    //Kolonne til startvægt

    @FXML
    private TableColumn<Animal, Number> endWeightColumn;
    //Kolonne til slutvægt

    @FXML
    private TableColumn<Animal, Number> feedIntakeColumn;
    //Kolonne til foderindtag

    @FXML
    private TableColumn<Animal, Number> fcrColumn;
    //Kolonne til FCR

    @FXML
    private TableColumn<Animal, String> activeColumn;
    //Kolonne som viser om grisen er aktiv eller inaktiv

    @FXML
    private TextField searchField;
    //Tekstfeltet hvor jeg kan søge/filtere i grisene

    @FXML
    private Label messageLabel;
    //Label bruges til at vise beskeder til brugeren

    private AnimalRepository animalRepository;
    //Repository bruges til at hente & ændre grise i databasen

    private ArrayList<Animal> alleAnimals;
    //Denne liste gemmer alle grise fra databasen

    public AnimalController()
    //Constructor kører når controlleren oprettes
    {
        animalRepository = new AnimalRepository();

        alleAnimals = new ArrayList<>();
    }

    @FXML
    private void initialize()
    /*
    Initialize kører automatisk når FXML siden åbnes
    Her sætter jeg først tabellens kolonner op
    Derefter henter jeg grisene fra databasen
    */
    {
        setupTableColumns();

        loadAnimals();
    }

    private void setupTableColumns()
    /*
    Her bestemmer jeg hvilke data fra Animal objectet der skal vises i tabellen
    Hver kolonne kobles til en getter fra Animal klassen
    */
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
    /*
    Denne metode henter alle grise fra databasen
    Derefter sorterer jeg grisene med bubble sort efter vægtøgning
    Til sidst bliver listen vist i tabellen
    */
    {
        alleAnimals = animalRepository.hentAlleAnimals();

        bubbleSortAnimalsByWeightGain();
        //Her sorterer jeg listen før den bliver vist i tabellen

        ObservableList<Animal> animalObservableList =
                FXCollections.observableArrayList(alleAnimals);

        animalTable.setItems(animalObservableList);

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Antal grise vist: " + alleAnimals.size() + " sorteret efter vægtøgning");
    }

    private void bubbleSortAnimalsByWeightGain()
    /*
    Denne metode sorterer grisene efter WeightGainKg
    Jeg bruger bubble sort her for at vise en simpel sorteringsalgoritme i Java

    Bubble sort fungerer ved at sammenligne to grise ved siden af hinanden
    Hvis den næste gris har større vægtøgning end den nuværende gris
    så bytter jeg dem rundt i listen

    Det gentages flere gange indtil grisene står sorteret
    med den største vægtøgning øverst
    */
    {
        for (int i = 0; i < alleAnimals.size() - 1; i++)
        //Det yderste loop bestemmer hvor mange gange listen skal gennemgås
        {
            for (int j = 0; j < alleAnimals.size() - i - 1; j++)
            //Det inderste loop sammenligner to grise ved siden af hinanden
            {
                Animal currentAnimal = alleAnimals.get(j);
                //Den gris jeg står på lige nu

                Animal nextAnimal = alleAnimals.get(j + 1);
                //Den næste gris i listen

                if (currentAnimal.getWeightGainKg() < nextAnimal.getWeightGainKg())
                /*
                Hvis næste gris har større vægtøgning end den nuværende gris
                så skal de bytte plads
                */
                {
                    alleAnimals.set(j, nextAnimal);

                    alleAnimals.set(j + 1, currentAnimal);
                }
            }
        }
    }

    @FXML
    private void handleSearch()
    /*
    Denne metode filtrerer grisene ud fra det jeg skriver i søgefeltet
    Der søges på dyrenummer, gruppe, lokation & responder
    */
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

        ObservableList<Animal> animalObservableList =
                FXCollections.observableArrayList(filteredAnimals);

        animalTable.setItems(animalObservableList);

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Filter viser: " + filteredAnimals.size() + " grise");
    }

    @FXML
    private void handleClearFilter()
    //Denne metode nulstiller søgefeltet og viser alle grise igen "skulle den gerne"
    {
        searchField.clear();

        ObservableList<Animal> animalObservableList =
                FXCollections.observableArrayList(alleAnimals);

        animalTable.setItems(animalObservableList);

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Filter nulstillet antal grise vist: " + alleAnimals.size());
    }

    @FXML
    private void handleStopRegistration()
    /*
    Denne metode stopper registrering af den gris jeg har valgt i tabellen
    Det betyder at IsActive bliver sat til 0 i databasen
    */
    {
        Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();
        //Her henter jeg den gris som er markeret i tabellen

        if (selectedAnimal == null)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Vælg en gris først");

            return;
        }

        if (!selectedAnimal.isActive())
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Denne gris er allerede inaktiv");

            return;
        }

        boolean success = animalRepository.stopRegistrering(selectedAnimal.getAnimalId());
        //Her kalder jeg repository metoden som opdaterer databasen

        if (success)
        {
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Registrering stoppet for gris: " + selectedAnimal.getAnimalNumber());

            loadAnimals();
            //Listen hentes igen så status ændres til Inaktiv
        }
        else
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke stoppe registrering");
        }
    }

    @FXML
    private void handleOpenCreateAnimal()
    //Denne metode åbner siden hvor jeg kan oprette en ny gris
    {
        try
        {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(HelloApplication.class.getResource("create-animal-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 500, 650);

            Stage stage = (Stage) animalTable.getScene().getWindow();

            stage.setTitle("Agrisys PPT Opret gris");
            stage.setScene(scene);
        }
        catch (Exception e)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke åbne opret gris");

            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToDashboard()
    //Denne metode sender mig tilbage til hovedmenuen
    {
        try
        {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 550);

            Stage stage = (Stage) animalTable.getScene().getWindow();

            stage.setTitle("Agrisys PPT Hovedmenu");
            stage.setScene(scene);
        }
        catch (Exception e)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Kunne ikke åbne hovedmenu");

            e.printStackTrace();
        }
    }
}