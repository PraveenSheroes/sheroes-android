package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by priyanka on 07/04/17.
 */

public class AboutMe implements Parcelable {
    private String description;

    public String getDescription() {
        return description;

    }

    public void setDescription(String description) {

        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
    }

    public AboutMe() {
    }

    protected AboutMe(Parcel in) {
        this.description = in.readString();
    }

    public static final Parcelable.Creator<AboutMe> CREATOR = new Parcelable.Creator<AboutMe>() {
        @Override
        public AboutMe createFromParcel(Parcel source) {
            return new AboutMe(source);
        }

        @Override
        public AboutMe[] newArray(int size) {
            return new AboutMe[size];
        }
    };
}
