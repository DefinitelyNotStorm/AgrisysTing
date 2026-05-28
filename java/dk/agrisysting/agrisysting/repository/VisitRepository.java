package dk.agrisysting.agrisysting.repository;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.Andet.DatabaseConnection;
import dk.agrisysting.agrisysting.model.Visit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

//Repository betyder at denne klasse arbejder med databasen
//Denne klasse henter data fra Visit tabellen
public class VisitRepository
{
    public ArrayList<Visit> hentAlleVisits()
    //Denne metode henter alle besøg fra min Visit tabel
    {
        ArrayList<Visit> visits = new ArrayList<>();
        //Her laver jeg en tom liste som senere fyldes med visits fra databasen

        String sql = "SELECT * FROM Visit ORDER BY VisitId";
        //SQL query som henter alt fra Visit tabellen

        try
                (
                        Connection connection = DatabaseConnection.hentForbindelse();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet resultSet = statement.executeQuery()
                )
        //try with resources lukker automatisk connection, statement og resultSet bagefter
        {
            while (resultSet.next())
            //Så længe der findes en række mere i databasen kører loopet
            {
                Visit visit = new Visit
                        (
                                resultSet.getInt("VisitId"),
                                resultSet.getInt("AnimalId"),
                                resultSet.getString("VisitTime"),
                                resultSet.getInt("DurationSeconds"),
                                resultSet.getInt("WeightGram"),
                                resultSet.getInt("FeedIntakeGram")
                        );

                visits.add(visit);
                //Her tilføjer jeg visit til listen
            }
        }
        catch (SQLException e)
        {
            System.out.println("Fejl ved hentning af visits fra databasen.");
            e.printStackTrace();
        }

        return visits;
        //Returnerer listen med visits
    }
}