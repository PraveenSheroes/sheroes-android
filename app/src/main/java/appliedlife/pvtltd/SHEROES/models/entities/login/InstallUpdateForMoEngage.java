package appliedlife.pvtltd.SHEROES.models.entities.login;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Praveen_Singh on 12-05-2017.
 */

public class InstallUpdateForMoEngage implements Parcelable {
    private int appVersion;

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.appVersion);
    }

    public InstallUpdateForMoEngage() {
    }

    protected InstallUpdateForMoEngage(Parcel in) {
        this.appVersion = in.readInt();
    }

    public static final Parcelable.Creator<InstallUpdateForMoEngage> CREATOR = new Parcelable.Creator<InstallUpdateForMoEngage>() {
        @Override
        public InstallUpdateForMoEngage createFromParcel(Parcel source) {
            return new InstallUpdateForMoEngage(source);
        }

        @Override
        public InstallUpdateForMoEngage[] newArray(int size) {
            return new InstallUpdateForMoEngage[size];
        }
    };
}
