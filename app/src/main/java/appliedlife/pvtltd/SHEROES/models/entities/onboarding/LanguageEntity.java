package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Praveen_Singh on 02-04-2017.
 */

public class LanguageEntity {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("proficiencylevel")
    @Expose
    private long proficiencyLevel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(long proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
}
