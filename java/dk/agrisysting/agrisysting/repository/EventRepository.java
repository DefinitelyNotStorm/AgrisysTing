package dk.agrisysting.agrisysting.repository;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.Andet.DatabaseConnection;
import dk.agrisysting.agrisysting.model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

//Repository betyder at denne klasse arbejder med databasen
//Denne klasse henter data fra Event tabellen
public class EventRepository
{
    public ArrayList<Event> hentAlleEvents()
    //Denne metode henter alle hændelser fra min Event tabel
    {
        ArrayList<Event> events = new ArrayList<>();
        //Her laver jeg en tom liste som senere fyldes med events fra databasen

        String sql = "SELECT * FROM `Event` ORDER BY EventId";
        //Event skrives med ` ` fordi Event kan være et særligt/reserveret ord i nogle databaser

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
                Event event = new Event
                        (
                                resultSet.getInt("EventId"),
                                resultSet.getInt("AnimalId"),
                                resultSet.getString("EventType"),
                                resultSet.getString("EventDescription"),
                                resultSet.getString("EventDate")
                        );

                events.add(event);
                //Her tilføjer jeg eventet til listen
            }
        }
        catch (SQLException e)
        {
            System.out.println("Fejl ved hentning af events fra databasen.");
            e.printStackTrace();
        }

        return events;
        //Returnerer listen med events
    }
}