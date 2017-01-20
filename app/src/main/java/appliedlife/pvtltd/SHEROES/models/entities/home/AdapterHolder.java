package appliedlife.pvtltd.SHEROES.models.entities.home;

/**
 * Created by Praveen.Singh on 13/01/2017.
 *
 * @author Praveen Singh
 * @since 13/01/2017.
 * Title: POJO to store  adapter data for listRowItemObject and position.
 */
public class AdapterHolder
{
    private int position;
    private Object listRowItemObject;
    private boolean isChecked;

    public AdapterHolder(int position, Object listRowItemObject)
    {
        this.position = position;
        this.listRowItemObject = listRowItemObject;
    }

    public AdapterHolder()
    {
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public Object getHolder()
    {
        return listRowItemObject;
    }

    public void setHolder(Object listRowItemObject)
    {
        this.listRowItemObject = listRowItemObject;
    }

    public boolean isChecked()
    {
        return isChecked;
    }

    public void setChecked(boolean checked)
    {
        isChecked = checked;
    }
}
