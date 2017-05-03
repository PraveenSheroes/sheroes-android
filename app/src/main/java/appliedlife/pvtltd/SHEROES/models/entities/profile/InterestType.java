package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sheroes on 07/04/17.
 */

public class InterestType implements Parcelable {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
    }

    public InterestType() {
    }

    protected InterestType(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
    }

    public static final Parcelable.Creator<InterestType> CREATOR = new Parcelable.Creator<InterestType>() {
        @Override
        public InterestType createFromParcel(Parcel source) {
            return new InterestType(source);
        }

        @Override
        public InterestType[] newArray(int size) {
            return new InterestType[size];
        }
    };
}
