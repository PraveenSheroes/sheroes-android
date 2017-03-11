
package appliedlife.pvtltd.SHEROES.models.entities.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Setting_PrivilegesList_1 {

    @SerializedName("school_marks")
    @Expose
    private SchoolMarks schoolMarks;
    @SerializedName("graduation_marks")
    @Expose
    private GraduationMarks graduationMarks;

    public SchoolMarks getSchoolMarks() {
        return schoolMarks;
    }

    public void setSchoolMarks(SchoolMarks schoolMarks) {
        this.schoolMarks = schoolMarks;
    }

    public GraduationMarks getGraduationMarks() {
        return graduationMarks;
    }

    public void setGraduationMarks(GraduationMarks graduationMarks) {
        this.graduationMarks = graduationMarks;
    }

}
