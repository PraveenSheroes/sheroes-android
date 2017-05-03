package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by priyanka on 07/04/17.
 */

public class ClientSideLocation implements Parcelable {


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

    public ClientSideLocation() {
    }

    protected ClientSideLocation(Parcel in) {
        this.description = in.readString();
    }

    public static final Parcelable.Creator<ClientSideLocation> CREATOR = new Parcelable.Creator<ClientSideLocation>() {
        @Override
        public ClientSideLocation createFromParcel(Parcel source) {
            return new ClientSideLocation(source);
        }

        @Override
        public ClientSideLocation[] newArray(int size) {
            return new ClientSideLocation[size];
        }
    };
}
