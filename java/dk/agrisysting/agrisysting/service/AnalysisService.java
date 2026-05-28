package dk.agrisysting.agrisysting.service;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.model.Animal;
import dk.agrisysting.agrisysting.repository.AnimalRepository;

import java.util.ArrayList;

//Denne service bruges til simple analyser af grisedata
public class AnalysisService
{
    private AnimalRepository animalRepository;

    public AnalysisService()
    {
        animalRepository = new AnimalRepository();
    }

    public int getTotalAnimals()
    //Returnerer antal grise i alt
    {
        ArrayList<Animal> animals = animalRepository.hentAlleAnimals();

        return animals.size();
    }

    public int getActiveAnimals()
    //Returnerer antal aktive grise
    {
        ArrayList<Animal> animals = animalRepository.hentAlleAnimals();

        int count = 0;

        for (Animal animal : animals)
        {
            if (animal.isActive())
            {
                count++;
            }
        }

        return count;
    }

    public int getInactiveAnimals()
    //Returnerer antal inaktive grise
    {
        ArrayList<Animal> animals = animalRepository.hentAlleAnimals();

        int count = 0;

        for (Animal animal : animals)
        {
            if (!animal.isActive())
            {
                count++;
            }
        }

        return count;
    }

    public double getAverageFcr()
    //Udregner gennemsnitlig FCR
    {
        ArrayList<Animal> animals = animalRepository.hentAlleAnimals();

        if (animals.isEmpty())
        {
            return 0;
        }

        double total = 0;

        for (Animal animal : animals)
        {
            total = total + animal.getFcr();
        }

        return total / animals.size();
    }

    public double getAverageStartWeight()
    //Udregner gennemsnitlig startvægt
    {
        ArrayList<Animal> animals = animalRepository.hentAlleAnimals();

        if (animals.isEmpty())
        {
            return 0;
        }

        double total = 0;

        for (Animal animal : animals)
        {
            total = total + animal.getStartWeightKg();
        }

        return total / animals.size();
    }

    public double getAverageEndWeight()
    //Udregner gennemsnitlig slutvægt
    {
        ArrayList<Animal> animals = animalRepository.hentAlleAnimals();

        if (animals.isEmpty())
        {
            return 0;
        }

        double total = 0;

        for (Animal animal : animals)
        {
            total = total + animal.getEndWeightKg();
        }

        return total / animals.size();
    }
}