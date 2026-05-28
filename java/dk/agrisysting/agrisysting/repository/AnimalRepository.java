package dk.agrisysting.agrisysting.repository;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.Andet.DatabaseConnection;
import dk.agrisysting.agrisysting.model.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

//Repository betyder at denne klasse arbejder med databasen
public class AnimalRepository
{
    public ArrayList<Animal> hentAlleAnimals()
    //Denne metode henter alle grise fra min Animal tabel
    {
        ArrayList<Animal> animals = new ArrayList<>();

        String sql = "SELECT * FROM Animal";

        try
                (
                        Connection connection = DatabaseConnection.hentForbindelse();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet resultSet = statement.executeQuery()
                )
        {
            while (resultSet.next())
            {
                Animal animal = new Animal
                        (
                                resultSet.getInt("AnimalId"),
                                resultSet.getString("AnimalNumber"),
                                resultSet.getString("Responder"),
                                resultSet.getString("GroupName"),
                                resultSet.getString("LocationName"),
                                resultSet.getDouble("StartWeightKg"),
                                resultSet.getDouble("EndWeightKg"),
                                resultSet.getDouble("WeightGainKg"),
                                resultSet.getDouble("TotalFeedIntakeKg"),
                                resultSet.getDouble("FCR"),
                                resultSet.getString("StartDay"),
                                resultSet.getInt("CompletedDaysInTest"),
                                resultSet.getBoolean("IsActive")
                        );

                animals.add(animal);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Fejl ved hentning af grise fra databasen.");
            e.printStackTrace();
        }

        return animals;
    }

    public boolean stopRegistrering(int animalId)
    //Denne metode stopper registreringen af en gris
    {
        String sql = "UPDATE Animal SET IsActive = 0 WHERE AnimalId = ?";

        try
                (
                        Connection connection = DatabaseConnection.hentForbindelse();
                        PreparedStatement statement = connection.prepareStatement(sql)
                )
        {
            statement.setInt(1, animalId);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        }
        catch (SQLException e)
        {
            System.out.println("Fejl ved stop af registrering.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean opretAnimal(Animal animal)
    //Denne metode opretter en ny gris i databasen
    {
        String sql =
                """
                INSERT INTO Animal
                (
                    AnimalNumber,
                    Responder,
                    GroupName,
                    LocationName,
                    StartWeightKg,
                    EndWeightKg,
                    WeightGainKg,
                    TotalFeedIntakeKg,
                    FCR,
                    StartDay,
                    CompletedDaysInTest,
                    IsActive
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try
                (
                        Connection connection = DatabaseConnection.hentForbindelse();
                        PreparedStatement statement = connection.prepareStatement(sql)
                )
        {
            statement.setString(1, animal.getAnimalNumber());
            statement.setString(2, animal.getResponder());
            statement.setString(3, animal.getGroupName());
            statement.setString(4, animal.getLocationName());

            statement.setDouble(5, animal.getStartWeightKg());
            statement.setDouble(6, animal.getEndWeightKg());
            statement.setDouble(7, animal.getWeightGainKg());
            statement.setDouble(8, animal.getTotalFeedIntakeKg());
            statement.setDouble(9, animal.getFcr());

            statement.setString(10, animal.getStartDay());
            statement.setInt(11, animal.getCompletedDaysInTest());
            statement.setBoolean(12, animal.isActive());

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        }
        catch (SQLException e)
        {
            System.out.println("Fejl ved oprettelse af gris.");
            e.printStackTrace();
        }

        return false;
    }
}