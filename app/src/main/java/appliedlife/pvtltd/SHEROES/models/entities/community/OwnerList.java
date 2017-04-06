package appliedlife.pvtltd.SHEROES.models.entities.community;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class OwnerList extends BaseResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("background")
    @Expose
    private String background;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     *
     * @return
     * The background
     */
    public String getBackground() {
        return background;
    }

    /**
     *
     * @param background
     * The background
     */
    public void setBackground(String background) {
        this.background = background;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.background);
    }

    public OwnerList() {
    }

    protected OwnerList(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.background = in.readString();
    }

    public static final Parcelable.Creator<OwnerList> CREATOR = new Parcelable.Creator<OwnerList>() {
        @Override
        public OwnerList createFromParcel(Parcel source) {
            return new OwnerList(source);
        }

        @Override
        public OwnerList[] newArray(int size) {
            return new OwnerList[size];
        }
    };
}