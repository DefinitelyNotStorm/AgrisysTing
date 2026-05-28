package dk.agrisysting.agrisysting.model;

//Denne klasse repræsenterer en hændelse fra databasen
//Klassen matcher Event tabellen
public class Event
{
    private int eventId;

    private int animalId;

    private String eventType;
    private String eventDescription;
    private String eventDate;

    //Constructor bruges til at oprette et Event object
    public Event
    (
            int eventId,
            int animalId,
            String eventType,
            String eventDescription,
            String eventDate
    )
    {
        this.eventId = eventId;

        this.animalId = animalId;

        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
    }

    public int getEventId()
    {
        return eventId;
    }

    public int getAnimalId()
    {
        return animalId;
    }

    public String getEventType()
    {
        return eventType;
    }

    public String getEventDescription()
    {
        return eventDescription;
    }

    public String getEventDate()
    {
        return eventDate;
    }

    //Setters bruges hvis data senere skal ændres

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public void setEventDescription(String eventDescription)
    {
        this.eventDescription = eventDescription;
    }

    public void setEventDate(String eventDate)
    {
        this.eventDate = eventDate;
    }
}