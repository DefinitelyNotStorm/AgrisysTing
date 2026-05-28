package dk.agrisysting.agrisysting.model;

//Denne klasse repræsenterer en import log fra databasen
//Klassen matcher ImportLog tabellen

public class ImportLog
{
    private int importLogId;

    private String fileName;
    private String importDate;

    private int importedByUserId;

    private String status;

    //Constructor bruges til at oprette et ImportLog object
    public ImportLog
    (
            int importLogId,
            String fileName,
            String importDate,
            int importedByUserId,
            String status
    )
    {
        this.importLogId = importLogId;

        this.fileName = fileName;
        this.importDate = importDate;

        this.importedByUserId = importedByUserId;

        this.status = status;
    }

    public int getImportLogId()
    {
        return importLogId;
    }

    public String getFileName()
    {
        return fileName;
    }

    public String getImportDate()
    {
        return importDate;
    }

    public int getImportedByUserId()
    {
        return importedByUserId;
    }

    public String getStatus()
    {
        return status;
    }

    //Setters bruges hvis data senere skal ændres

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public void setImportDate(String importDate)
    {
        this.importDate = importDate;
    }

    public void setImportedByUserId(int importedByUserId)
    {
        this.importedByUserId = importedByUserId;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}