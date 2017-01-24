package appliedlife.pvtltd.SHEROES.basecomponents.baseresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceErrors implements Parcelable
{
    @SerializedName("error_list")
    @Expose
    List<ServiceError> errorList;

    public static Creator<ServiceErrors> getCREATOR()
    {
        return CREATOR;
    }


    public List<ServiceError> getErrorList()
    {
        return errorList;
    }

    public void setErrorList(List<ServiceError> errorList)
    {
        this.errorList = errorList;
    }

    public ServiceErrors()
    {
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeTypedList(errorList);
    }

    protected ServiceErrors(Parcel in)
    {
        this.errorList = in.createTypedArrayList(ServiceError.CREATOR);
    }

    public static final Creator<ServiceErrors> CREATOR = new Creator<ServiceErrors>()
    {
        @Override
        public ServiceErrors createFromParcel(Parcel source)
        {
            return new ServiceErrors(source);
        }

        @Override
        public ServiceErrors[] newArray(int size)
        {
            return new ServiceErrors[size];
        }
    };
}
