
package appliedlife.pvtltd.SHEROES.models.entities.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class HomeSpinnerItem extends BaseResponse implements Parcelable {

    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("ischecked")
    @Expose
    private boolean isChecked;
    private int categoryIdItem;
    List<Integer> categoryId;
    private boolean isDone;
    private int itemPostion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getCategoryIdItem() {
        return categoryIdItem;
    }

    public void setCategoryIdItem(int categoryIdItem) {
        this.categoryIdItem = categoryIdItem;
    }

    public List<Integer> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(List<Integer> categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public int getItemPostion() {
        return itemPostion;
    }

    public void setItemPostion(int itemPostion) {
        this.itemPostion = itemPostion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeInt(this.categoryIdItem);
        dest.writeList(this.categoryId);
        dest.writeByte(this.isDone ? (byte) 1 : (byte) 0);
        dest.writeInt(this.itemPostion);
    }

    public HomeSpinnerItem() {
    }

    protected HomeSpinnerItem(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.description = in.readString();
        this.isChecked = in.readByte() != 0;
        this.categoryIdItem = in.readInt();
        this.categoryId = new ArrayList<Integer>();
        in.readList(this.categoryId, Integer.class.getClassLoader());
        this.isDone = in.readByte() != 0;
        this.itemPostion = in.readInt();
    }

    public static final Creator<HomeSpinnerItem> CREATOR = new Creator<HomeSpinnerItem>() {
        @Override
        public HomeSpinnerItem createFromParcel(Parcel source) {
            return new HomeSpinnerItem(source);
        }

        @Override
        public HomeSpinnerItem[] newArray(int size) {
            return new HomeSpinnerItem[size];
        }
    };
}
