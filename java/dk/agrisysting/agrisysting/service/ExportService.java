package dk.agrisysting.agrisysting.service;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.model.Animal;
import dk.agrisysting.agrisysting.repository.AnimalRepository;

import java.io.FileWriter;
import java.io.PrintWriter;

import java.util.ArrayList;

//Denne service bruges til at eksportere data til en CSV-fil
public class ExportService
{
    private AnimalRepository animalRepository;

    public ExportService()
    {
        animalRepository = new AnimalRepository();
    }

    public boolean exportAnimalsToCsv(String filePath)
    //Denne metode eksporterer alle grise til en CSV-fil
    {
        ArrayList<Animal> animals = animalRepository.hentAlleAnimals();

        try
                (
                        PrintWriter writer = new PrintWriter(new FileWriter(filePath))
                )
        {
            //Første linje i CSV filen er overskrifter
            writer.println("AnimalId;AnimalNumber;Responder;GroupName;LocationName;StartWeightKg;EndWeightKg;WeightGainKg;TotalFeedIntakeKg;FCR;StartDay;CompletedDaysInTest;IsActive");

            for (Animal animal : animals)
            {
                writer.println(
                        animal.getAnimalId() + ";" +
                                animal.getAnimalNumber() + ";" +
                                animal.getResponder() + ";" +
                                animal.getGroupName() + ";" +
                                animal.getLocationName() + ";" +
                                animal.getStartWeightKg() + ";" +
                                animal.getEndWeightKg() + ";" +
                                animal.getWeightGainKg() + ";" +
                                animal.getTotalFeedIntakeKg() + ";" +
                                animal.getFcr() + ";" +
                                animal.getStartDay() + ";" +
                                animal.getCompletedDaysInTest() + ";" +
                                animal.isActive()
                );
            }

            return true;
        }
        catch (Exception e)
        {
            System.out.println("Fejl ved eksport til CSV.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean generateImportTemplate(String filePath)
        //Denne metode laver en tom CSV skabelon
        //Skabelonen viser hvilke kolonner importfilen skal have
    {
        try
                (
                        PrintWriter writer = new PrintWriter(new FileWriter(filePath))
                )
        {
            writer.println("AnimalNumber;Responder;GroupName;LocationName;StartWeightKg;EndWeightKg;WeightGainKg;TotalFeedIntakeKg;FCR;StartDay;CompletedDaysInTest");

            return true;
        }
        catch (Exception e)
        {
            System.out.println("Fejl ved oprettelse af importskabelon.");
            e.printStackTrace();
        }

        return false;
    }
}