package dk.agrisysting.agrisysting.service;
//Mappen filen ligger i

import dk.agrisysting.agrisysting.model.Event;
import dk.agrisysting.agrisysting.repository.EventRepository;

import java.util.ArrayList;

//Service fungerer som mellemled mellem controller og repository
//Controlleren snakker med service, og service snakker med repository
public class EventService
{
    private EventRepository eventRepository;

    public EventService()
    {
        eventRepository = new EventRepository();
    }

    public ArrayList<Event> hentAlleEvents()
    //Denne metode henter alle events gennem repository
    {
        return eventRepository.hentAlleEvents();
    }
}