package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 04-05-2017.
 */

public class ExperienceBOdetail extends BaseResponse {
    @SerializedName("experiences")
    @Expose
    private HashMap<Long,ExprienceEntity>exprienceEntityHashMap=new HashMap<>();

    public HashMap<Long, ExprienceEntity> getExprienceEntityHashMap() {
        return exprienceEntityHashMap;
    }

    public void setExprienceEntityHashMap(HashMap<Long, ExprienceEntity> exprienceEntityHashMap) {
        this.exprienceEntityHashMap = exprienceEntityHashMap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeSerializable(this.exprienceEntityHashMap);
    }

    public ExperienceBOdetail() {
    }

    protected ExperienceBOdetail(Parcel in) {
        super(in);
        this.exprienceEntityHashMap = (HashMap<Long, ExprienceEntity>) in.readSerializable();
    }

    public static final Creator<ExperienceBOdetail> CREATOR = new Creator<ExperienceBOdetail>() {
        @Override
        public ExperienceBOdetail createFromParcel(Parcel source) {
            return new ExperienceBOdetail(source);
        }

        @Override
        public ExperienceBOdetail[] newArray(int size) {
            return new ExperienceBOdetail[size];
        }
    };
}
