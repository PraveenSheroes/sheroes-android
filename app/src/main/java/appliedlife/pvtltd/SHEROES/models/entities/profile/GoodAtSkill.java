package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sheroes on 07/04/17.
 */

public class GoodAtSkill implements Parcelable {
    private Long id;
    private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public GoodAtSkill() {
    }

    protected GoodAtSkill(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
    }

    public static final Parcelable.Creator<GoodAtSkill> CREATOR = new Parcelable.Creator<GoodAtSkill>() {
        @Override
        public GoodAtSkill createFromParcel(Parcel source) {
            return new GoodAtSkill(source);
        }

        @Override
        public GoodAtSkill[] newArray(int size) {
            return new GoodAtSkill[size];
        }
    };
}
