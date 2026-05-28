package dk.agrisysting.agrisysting.model;

//Denne klasse repræsenterer et besøg fra databasen
//Klassen matcher Visit tabellen

public class Visit
{
    private int visitId;

    private int animalId;

    private String visitTime;

    private int durationSeconds;

    private int weightGram;

    private int feedIntakeGram;

    //Constructor bruges til at oprette et Visit object
    public Visit
    (
            int visitId,
            int animalId,
            String visitTime,
            int durationSeconds,
            int weightGram,
            int feedIntakeGram
    )
    {
        this.visitId = visitId;

        this.animalId = animalId;

        this.visitTime = visitTime;

        this.durationSeconds = durationSeconds;

        this.weightGram = weightGram;

        this.feedIntakeGram = feedIntakeGram;
    }

    public int getVisitId()
    {
        return visitId;
    }

    public int getAnimalId()
    {
        return animalId;
    }

    public String getVisitTime()
    {
        return visitTime;
    }

    public int getDurationSeconds()
    {
        return durationSeconds;
    }

    public int getWeightGram()
    {
        return weightGram;
    }

    public int getFeedIntakeGram()
    {
        return feedIntakeGram;
    }

    //Setters bruges hvis data senere skal ændres

    public void setVisitTime(String visitTime)
    {
        this.visitTime = visitTime;
    }

    public void setDurationSeconds(int durationSeconds)
    {
        this.durationSeconds = durationSeconds;
    }

    public void setWeightGram(int weightGram)
    {
        this.weightGram = weightGram;
    }

    public void setFeedIntakeGram(int feedIntakeGram)
    {
        this.feedIntakeGram = feedIntakeGram;
    }
}