package appliedlife.pvtltd.SHEROES.models.entities.login;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Praveen_Singh on 12-05-2017.
 */

public class InstallUpdateForMoEngage implements Parcelable {
    private int appVersion;
    private boolean isFirstOpen;
    private boolean isWelcome;
    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public InstallUpdateForMoEngage() {
    }

    public boolean isFirstOpen() {
        return isFirstOpen;
    }

    public void setFirstOpen(boolean firstOpen) {
        isFirstOpen = firstOpen;
    }

    public boolean isWelcome() {
        return isWelcome;
    }

    public void setWelcome(boolean welcome) {
        isWelcome = welcome;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.appVersion);
        dest.writeByte(this.isFirstOpen ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isWelcome ? (byte) 1 : (byte) 0);
    }

    protected InstallUpdateForMoEngage(Parcel in) {
        this.appVersion = in.readInt();
        this.isFirstOpen = in.readByte() != 0;
        this.isWelcome = in.readByte() != 0;
    }

    public static final Creator<InstallUpdateForMoEngage> CREATOR = new Creator<InstallUpdateForMoEngage>() {
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
