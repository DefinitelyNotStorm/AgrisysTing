package dk.agrisysting.agrisysting.model;

//Denne klasse repræsenterer en bruger fra databasen
//Klassen matcher User tabellen

public class User
{
    private int userId;

    private String username;
    private String password;

    private String role;

    //Constructor bruges til at oprette et User object
    public User(int userId, String username, String password, String role)
    {
        this.userId = userId;

        this.username = username;
        this.password = password;

        this.role = role;
    }

    public int getUserId()
    {
        return userId;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getRole()
    {
        return role;
    }

    //Setters bruges hvis noget senere skal ændres

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setRole(String role)
    {
        this.role = role;
    }
}