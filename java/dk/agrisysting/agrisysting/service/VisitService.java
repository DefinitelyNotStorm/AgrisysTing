package dk.agrisysting.agrisysting.service;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.model.Visit;
import dk.agrisysting.agrisysting.repository.VisitRepository;

import java.util.ArrayList;

//Service fungerer som mellemled mellem controller og repository
//Controlleren snakker med service, og service snakker med repository
public class VisitService
{
    private VisitRepository visitRepository;

    public VisitService()
    {
        visitRepository = new VisitRepository();
    }

    public ArrayList<Visit> hentAlleVisits()
    //Denne metode henter alle visits gennem repository
    {
        return visitRepository.hentAlleVisits();
    }
}