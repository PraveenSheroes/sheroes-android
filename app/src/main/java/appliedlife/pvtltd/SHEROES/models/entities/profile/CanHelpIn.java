package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sheroes on 09/04/17.
 */

public class CanHelpIn implements Parcelable {
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

    public CanHelpIn() {
    }

    protected CanHelpIn(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
    }

    public static final Parcelable.Creator<CanHelpIn> CREATOR = new Parcelable.Creator<CanHelpIn>() {
        @Override
        public CanHelpIn createFromParcel(Parcel source) {
            return new CanHelpIn(source);
        }

        @Override
        public CanHelpIn[] newArray(int size) {
            return new CanHelpIn[size];
        }
    };
}
