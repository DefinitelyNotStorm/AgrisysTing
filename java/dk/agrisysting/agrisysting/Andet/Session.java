package dk.agrisysting.agrisysting.Andet;
//Jeg har lagt Session i Andet fordi den gerne skulle bruges i hele programmet

import dk.agrisysting.agrisysting.model.User;
//Import af User modellen
//Jeg bruger User objectet til at gemme hvem der er logget ind, fordi det virkede vigitgt da jeg lavede det


//Denne klasse bruges til at holde styr på den nuværende bruger.
public class Session
{
    private static User currentUser;
    //Her gemmer jeg den bruger som er logget ind lige nu.
    //Der skulle netop kun findes en forbruger af gangen

    public static void setCurrentUser(User user)
    //Denne metode bruges når en bruger logger ind
    //Metoden modtager et User object og gemmer det i currentUser
    {
        currentUser = user;
        //Det her gemmer brugeren i sessionen
    }

    //Denne metode bruges når jeg vil hente den bruger som er logget ind til Fx i DashboardController hvor jeg tjekker brugerens rolle
    public static User getCurrentUser()
    {
        return currentUser;
        //Returnerer den aktuelle bruger
    }

    public static void clear()
    //Denne metode bruges når brugeren logger ud så bliver sessionen nulstillet
    {
        currentUser = null;
        //null betyder at der ikke længere er en aktiv bruger
    }
}

//private = Kun denne klasse må direkte bruge variablen
//static = Tilhører klassen og ikke et object
//User =Min modelklasse som repræsenterer en bruger
//null = Ingen værdi/tom værdi.