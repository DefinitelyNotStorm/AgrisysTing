package dk.agrisysting.agrisysting.model;

//Denne klasse repræsenterer en gris fra min database
//Klassen tilhører Animal tabellen i databasen

public class Animal
{
    private int animalId;
    private String animalNumber;
    private String responder;
    private String groupName;
    private String locationName;

    private double startWeightKg;
    private double endWeightKg;
    private double weightGainKg;
    private double totalFeedIntakeKg;
    private double fcr;

    private String startDay;

    private int completedDaysInTest;

    private boolean isActive;

    //Constructor bruges til at oprette et Animal object med data
    public Animal
    (
            int animalId,
            String animalNumber,
            String responder,
            String groupName,
            String locationName,
            double startWeightKg,
            double endWeightKg,
            double weightGainKg,
            double totalFeedIntakeKg,
            double fcr,
            String startDay,
            int completedDaysInTest,
            boolean isActive
    )
    {
        this.animalId = animalId;
        this.animalNumber = animalNumber;
        this.responder = responder;
        this.groupName = groupName;
        this.locationName = locationName;

        this.startWeightKg = startWeightKg;
        this.endWeightKg = endWeightKg;
        this.weightGainKg = weightGainKg;
        this.totalFeedIntakeKg = totalFeedIntakeKg;
        this.fcr = fcr;

        this.startDay = startDay;

        this.completedDaysInTest = completedDaysInTest;

        this.isActive = isActive;
    }

    public int getAnimalId()
    {
        return animalId;
    }

    public String getAnimalNumber()
    {
        return animalNumber;
    }

    public String getResponder()
    {
        return responder;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public String getLocationName()
    {
        return locationName;
    }

    public double getStartWeightKg()
    {
        return startWeightKg;
    }

    public double getEndWeightKg()
    {
        return endWeightKg;
    }

    public double getWeightGainKg()
    {
        return weightGainKg;
    }

    public double getTotalFeedIntakeKg()
    {
        return totalFeedIntakeKg;
    }

    public double getFcr()
    {
        return fcr;
    }

    public String getStartDay()
    {
        return startDay;
    }

    public int getCompletedDaysInTest()
    {
        return completedDaysInTest;
    }

    public boolean isActive()
    {
        return isActive;
    }

    //Setters bruges hvis data senere skal ændres

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    public void setFcr(double fcr)
    {
        this.fcr = fcr;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }
}