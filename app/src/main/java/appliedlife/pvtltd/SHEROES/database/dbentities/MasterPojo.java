package appliedlife.pvtltd.SHEROES.database.dbentities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Praveen_Singh on 06-02-2017.
 */

public class MasterPojo {

    @SerializedName("listOfMaster")
    @Expose
    private List<MasterData> listOfMaster = null;

    public List<MasterData> getListOfMaster() {
        return listOfMaster;
    }

    public void setListOfMaster(List<MasterData> listOfMaster) {
        this.listOfMaster = listOfMaster;
    }

}