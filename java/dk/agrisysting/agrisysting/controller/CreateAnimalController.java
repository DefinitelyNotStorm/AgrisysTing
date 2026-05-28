package dk.agrisysting.agrisysting.controller;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.HelloApplication;
import dk.agrisysting.agrisysting.model.Animal;
import dk.agrisysting.agrisysting.repository.AnimalRepository;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

//Controlleren styrer create-animal-view.fxml
//Her kan brugeren oprette en ny gris
public class CreateAnimalController
{
    @FXML
    private TextField animalNumberField;

    @FXML
    private TextField responderField;

    @FXML
    private TextField groupNameField;

    @FXML
    private TextField locationNameField;

    @FXML
    private TextField startWeightField;

    @FXML
    private TextField endWeightField;

    @FXML
    private TextField weightGainField;

    @FXML
    private TextField feedIntakeField;

    @FXML
    private TextField fcrField;

    @FXML
    private TextField startDayField;

    @FXML
    private TextField completedDaysField;

    @FXML
    private Label messageLabel;

    private AnimalRepository animalRepository;

    public CreateAnimalController()
    {
        animalRepository = new AnimalRepository();
    }

    @FXML
    private void handleCreateAnimal()
    //Denne metode opretter en ny gris ud fra tekstfelterne
    {
        try
        {
            String animalNumber = animalNumberField.getText();
            String responder = responderField.getText();
            String groupName = groupNameField.getText();
            String locationName = locationNameField.getText();

            double startWeightKg = Double.parseDouble(startWeightField.getText());
            double endWeightKg = Double.parseDouble(endWeightField.getText());
            double weightGainKg = Double.parseDouble(weightGainField.getText());
            double totalFeedIntakeKg = Double.parseDouble(feedIntakeField.getText());
            double fcr = Double.parseDouble(fcrField.getText());

            String startDay = startDayField.getText();

            int completedDaysInTest = Integer.parseInt(completedDaysField.getText());

            Animal animal = new Animal
                    (
                            0,
                            animalNumber,
                            responder,
                            groupName,
                            locationName,
                            startWeightKg,
                            endWeightKg,
                            weightGainKg,
                            totalFeedIntakeKg,
                            fcr,
                            startDay,
                            completedDaysInTest,
                            true
                    );

            boolean success = animalRepository.opretAnimal(animal);

            if (success)
            {
                messageLabel.setStyle("-fx-text-fill: green;");
                messageLabel.setText("Grisen blev oprettet.");
            }
            else
            {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText("Grisen kunne ikke oprettes.");
            }
        }
        catch (NumberFormatException e)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Tjek at talfelterne er skrevet korrekt.");

            e.printStackTrace();
        }
        catch (Exception e)
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Der skete en fejl ved oprettelse.");

            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToAnimals()
    //Denne metode sender brugeren tilbage til griseoversigten
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("animals-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 950, 600);

            Stage stage = (Stage) animalNumberField.getScene().getWindow();

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
}