package dk.agrisysting.agrisysting.Andet;
//Mappen filen ligger i.
//Jeg har lagt den i Andet fordi den ikke direkte er en controller model service eller repository.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//Import hjælper mig med at hente klasser fra Java SQL biblioteket
//Connection bruger jeg til selve forbindelsen til databasen
//DriverManager bruges til at oprette forbindelsen til MySQL
//SQLException bruges til at fange fejl når noget går galt med databasen

public class DatabaseConnection
//Denne klasse er public, så andre klasser i projektet kan bruge den
//Klassen har skaber forbindelse til min MySQL database
{
    private static final String HOST = "localhost";
    private static final int PORT = 3307;
    private static final String DATABASE = "agrisys";
    //localhost betyder bare at databasen køre localt på min pc
    // 3307 er min PORT, da min MySQL ikke kunne bruge standard porten 3306 af en eller anden grund.
    //DATABASE = "agrysis" er navnet på databasen jeg forbinder tyil

    private static final String USER = "root";
    private static final String PASSWORD = "qqw39zxk";
    //Det er bare mit brugernavn og password til min database
    //PRIVATE så andre klasser ikke har adgang til det
    //static betyder at de tilhører klassen & ikke et objekt
    //final betyder at værdien ikke skal ændres så længe programmet kører

    private static final String CONNECTION_STRING =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    //Dette er selve forbindelses teksten til databasen Java bruger denne string til at vide hvilken database den skal forbinde til
    //jdbc:mysql siger bare jeg bruger MySQL mener jeg
    //HOST PORT & DATABASE bliver sat sammen til en samlet connection string

    public static Connection hentForbindelse() throws SQLException
    //Denne metode bruges når andre klasser skal have forbindelse til databasen
    //public betyder at andre klasser må kalde metoden
    //static betyder at jeg ikke behøver lave et DatabaseConnection object først
    //Connection betyder at metoden returnerer en databaseforbindelse

    {
        return DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
        //DriverManager skal oprette oprette forbindelse til databasen
    }

    public static void testForbindelse()
    //Denne metode tester om forbindelsen til databasen virker og void betyder bare metoden ikke returnerer noget
    {
        try (Connection a = hentForbindelse())
        //try betyder at jeg prøver noget som kan gå galt
        //Connection a er bare en "test forbindelse" til databasen
        //try with resources skal lukke forbindelsen automatisk bagefter
        {
            System.out.println("Forbindelse OK til databasen" + DATABASE);
            //Hvis forbindelsen virker printer jeg en besked i konsollen så jeg kan se det virker
        }
        catch (SQLException e)
        //catch kører hvis der nu skulle ske en fejl
        //SQLException kan fx være forkert password, forkert port eller database der ikke kører så kan jeg se det.
        {
            System.out.println("Forbindelse FEJLEDE");

            e.printStackTrace();
            //printStackTrace viser den tekniske fejl i konsollen til mig
            //Det bruger jeg til debugging, så jeg kan finde ud af hvad der gik galt hvis noget går galt
        }
    }
}

//private = Kun denne klasse kan direkte bruge variablen
//static = Tilhører klassen og ikke et object
//final = Værdien må ikke ændres
//String = er bare tekst
//int = er bare heltal
//Connection = Databaseforbindelse
//SQLException = Fejl der handler om databasearbejde