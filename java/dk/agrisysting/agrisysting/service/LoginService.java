package dk.agrisysting.agrisysting.service;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.model.User;
import dk.agrisysting.agrisysting.repository.UserRepository;

//Service klasse til login
//Service bruges til logik mellem controller og repository
public class LoginService
{
    private UserRepository userRepository;

    //Constructor.
    public LoginService()
    {
        userRepository = new UserRepository();
    }

    public User login(String username, String password)
    //Denne metode håndterer login
    {
        if (username == null || username.isEmpty())
        {
            return null;
        }

        if (password == null || password.isEmpty())
        {
            return null;
        }

        return userRepository.findUserByLogin(username, password);
    }
}