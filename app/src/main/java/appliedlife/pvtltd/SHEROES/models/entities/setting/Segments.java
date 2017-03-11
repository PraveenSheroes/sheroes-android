
package appliedlife.pvtltd.SHEROES.models.entities.setting;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Segments implements Parcelable {

    @SerializedName("1")
    @Expose
    private Setting_basic_details Setting_basic_details;
    @SerializedName("2")
    @Expose
    private Setting_Education Setting_Education;
    @SerializedName("3")
    @Expose
    private Setting_Work_Experience Setting_Work_Experience;

    protected Segments(Parcel in) {


    }

    public static final Creator<Segments> CREATOR = new Creator<Segments>() {
        @Override
        public Segments createFromParcel(Parcel in) {
            return new Segments(in);
        }

        @Override
        public Segments[] newArray(int size) {
            return new Segments[size];
        }
    };

    public Setting_basic_details get1() {
        return Setting_basic_details;
    }

    public void set1(Setting_basic_details Setting_basic_details) {
        this.Setting_basic_details = Setting_basic_details;
    }

    public Setting_Education get2() {
        return Setting_Education;
    }

    public void set2(Setting_Education Setting_Education) {
        this.Setting_Education = Setting_Education;
    }

    public Setting_Work_Experience get3() {
        return Setting_Work_Experience;
    }

    public void set3(Setting_Work_Experience Setting_Work_Experience) {
        this.Setting_Work_Experience = Setting_Work_Experience;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
