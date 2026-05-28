package dk.agrisysting.agrisysting.Andet;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.model.User;

//Session bruges til at huske hvilken bruger der er logget ind
//Så kan vi senere tjekke brugerens rolle i andre controllers
public class Session
{
    private static User currentUser;

    public static void setCurrentUser(User user)
    {
        currentUser = user;
    }

    public static User getCurrentUser()
    {
        return currentUser;
    }

    public static void clear()
    {
        currentUser = null;
    }
}