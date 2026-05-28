package dk.agrisysting.agrisysting.repository;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.Andet.DatabaseConnection;
import dk.agrisysting.agrisysting.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Repository klasse til User tabellen
//Denne klasse bruges til at hente brugere fra databasen
public class UserRepository
{
    public User findUserByLogin(String username, String password)
    //Denne metode prøver at finde en bruger med det indtastede username og password
    {
        String sql = "SELECT * FROM `User` WHERE Username = ? AND Password = ?";
        //Spørgsmålstegn bruges så vi kan sætte værdier ind sikkert med PreparedStatement

        try
                (
                        Connection connection = DatabaseConnection.hentForbindelse();
                        PreparedStatement statement = connection.prepareStatement(sql)
                )
        {
            statement.setString(1, username);
            statement.setString(2, password);
            //Her sætter jeg værdierne ind i SQL queryen
            //1 = første spørgsmålstegn
            //2 = andet spørgsmålstegn

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            //Hvis databasen finder en bruger laves et User object
            {
                return new User
                        (
                                resultSet.getInt("UserId"),
                                resultSet.getString("Username"),
                                resultSet.getString("Password"),
                                resultSet.getString("Role")
                        );
            }
        }
        catch (SQLException e)
        {
            System.out.println("Fejl ved login opslag i databasen.");
            e.printStackTrace();
        }

        return null;
        //Hvis ingen bruger blev fundet så retuneres null i guess
    }
}