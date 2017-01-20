package appliedlife.pvtltd.SHEROES.models.entities.home;


public class Collections extends SheroesListDataItem
{

    private long id;

    private String title;

    private String description;

    private String type;

    private Long[] regions;



    public long getId ()
    {
        return id;
    }

    public void setId (long id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public Long[] getRegions ()
    {
        return regions;
    }

    public void setRegions (Long[] regions)
    {
        this.regions = regions;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", title = "+title+", description = "+description+", type = "+type+", regions = "+regions+"]";
    }
}