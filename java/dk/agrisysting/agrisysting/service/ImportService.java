package dk.agrisysting.agrisysting.service;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.model.Animal;
import dk.agrisysting.agrisysting.repository.AnimalRepository;

import java.io.BufferedReader;
import java.io.FileReader;

//Denne service bruges til at importere data fra en CSV-fil
public class ImportService
{
    private AnimalRepository animalRepository;

    public ImportService()
    {
        animalRepository = new AnimalRepository();
    }

    public int importAnimalsFromCsv(String filePath)
    //Denne metode importerer grise fra en CSV-fil
    //Den returnerer hvor mange grise der blev importeret
    {
        int importedCount = 0;

        try
                (
                        BufferedReader reader = new BufferedReader(new FileReader(filePath))
                )
        {
            String line;

            boolean firstLine = true;

            while ((line = reader.readLine()) != null)
            {
                if (firstLine)
                //Første linje springes over, fordi det er overskrifter
                {
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(";");

                if (data.length < 11)
                //Hvis linjen ikke har nok data, springes den over
                {
                    continue;
                }

                String animalNumber = data[0];
                String responder = data[1];
                String groupName = data[2];
                String locationName = data[3];

                double startWeightKg = Double.parseDouble(data[4]);
                double endWeightKg = Double.parseDouble(data[5]);
                double weightGainKg = Double.parseDouble(data[6]);
                double totalFeedIntakeKg = Double.parseDouble(data[7]);
                double fcr = Double.parseDouble(data[8]);

                String startDay = data[9];

                int completedDaysInTest = Integer.parseInt(data[10]);

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
                    importedCount++;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Fejl ved import fra CSV.");
            e.printStackTrace();
        }

        return importedCount;
    }
}