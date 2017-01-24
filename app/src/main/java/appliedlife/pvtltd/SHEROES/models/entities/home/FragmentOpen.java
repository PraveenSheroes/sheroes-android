package appliedlife.pvtltd.SHEROES.models.entities.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Praveen_Singh on 13-01-2017.
 */

public class FragmentOpen implements Parcelable {
    boolean isOpen;
    boolean drawerOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isOpen ? (byte) 1 : (byte) 0);
        dest.writeByte(this.drawerOpen ? (byte) 1 : (byte) 0);
    }

    public FragmentOpen() {
    }

    protected FragmentOpen(Parcel in) {
        this.isOpen = in.readByte() != 0;
        this.drawerOpen = in.readByte() != 0;
    }

    public static final Parcelable.Creator<FragmentOpen> CREATOR = new Parcelable.Creator<FragmentOpen>() {
        @Override
        public FragmentOpen createFromParcel(Parcel source) {
            return new FragmentOpen(source);
        }

        @Override
        public FragmentOpen[] newArray(int size) {
            return new FragmentOpen[size];
        }
    };
}
