package appliedlife.pvtltd.SHEROES.basecomponents.baseresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceError implements Parcelable
{


    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;

    public static Creator<ServiceError> getCREATOR()
    {
        return CREATOR;
    }


    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public ServiceError()
    {
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.code);
        dest.writeString(this.message);
    }

    protected ServiceError(Parcel in)
    {
        this.code = in.readString();
        this.message = in.readString();
    }

    public static final Creator<ServiceError> CREATOR = new Creator<ServiceError>()
    {
        @Override
        public ServiceError createFromParcel(Parcel source)
        {
            return new ServiceError(source);
        }

        @Override
        public ServiceError[] newArray(int size)
        {
            return new ServiceError[size];
        }
    };
}
