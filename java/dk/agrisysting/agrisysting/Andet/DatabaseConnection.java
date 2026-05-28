package dk.agrisysting.agrisysting.Andet;
//Mappen filen ligger i

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//Import hjælper med at hente klasser fra Java SQL biblioteket
//Connection bruges til selve database forbindelsen
//DriverManager bruges til at oprette forbindelsen
//SQLException bruges til fejl hvis noget går galt

public class DatabaseConnection
//Public så andre filer i projektet kan bruge denne klasse.
{
    private static final String HOST = "localhost";
    private static final int PORT = 3307;
    private static final String DATABASE = "agrisys";
    //Her er information om hvor databasen ligger.
    //3306 er standard porten til MySQL, men jeg har brugt 3307 da jeg virkelig lavede nogle upsere med databasen.

    private static final String USER = "root";
    private static final String PASSWORD = "qqw39zxk";


    //Dette er hele forbindelses teksten/Stringen
    private static final String CONNECTION_STRING =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;

    public static Connection hentForbindelse() throws SQLException
    //Denne metode returnerer en database forbindelse.
    {
        return DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
    }

    public static void testForbindelse()
    //Void betyder bare at metoden ikke returnerer noget
    {
        try (Connection a = hentForbindelse())
        //Java prøver at oprette forbindelse til databasen
        {
            System.out.println("Forbindelse OK til databasen: " + DATABASE);
        }

        catch (SQLException e)
        //Hvis noget går galt kommer vi herned
        {
            System.out.println("Forbindelse FEJLEDE");

            e.printStackTrace();
            //PrintStackTrace giver en detaljeret fejl i consollen
        }
    }
}