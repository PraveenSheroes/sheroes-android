package appliedlife.pvtltd.SHEROES.basecomponents.baserequest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceInfo implements Parcelable
{


    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("user_agent")
    @Expose
    private String userAgent;
    @SerializedName("app_id")
    @Expose
    private String appId;
    @SerializedName("app_version")
    @Expose
    private String appVersion;
    @SerializedName("account_email_id")
    @Expose
    private String accountEmailId;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("device_name")
    @Expose
    private String deviceName;
    @SerializedName("network_Type")
    @Expose
    private String networkType;
    @SerializedName("android_version")
    @Expose
    private String androidVersion;


    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }

    public DeviceInfo()
    {
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getUserAgent()
    {
        return userAgent;
    }

    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getAppVersion()
    {
        return appVersion;
    }

    public void setAppVersion(String appVersion)
    {
        this.appVersion = appVersion;
    }

    public String getAccountEmailId()
    {
        return accountEmailId;
    }

    public void setAccountEmailId(String accountEmailId)
    {
        this.accountEmailId = accountEmailId;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getLang()
    {
        return lang;
    }

    public void setLang(String lang)
    {
        this.lang = lang;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }

    public String getNetworkType()
    {
        return networkType;
    }

    public void setNetworkType(String networkType)
    {
        this.networkType = networkType;
    }

    public String getAndroidVersion()
    {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion)
    {
        this.androidVersion = androidVersion;
    }

    public static Creator<DeviceInfo> getCREATOR()
    {
        return CREATOR;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.deviceId);
        dest.writeString(this.deviceType);
        dest.writeString(this.description);
        dest.writeString(this.userAgent);
        dest.writeString(this.appId);
        dest.writeString(this.appVersion);
        dest.writeString(this.accountEmailId);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.lang);
        dest.writeString(this.deviceName);
        dest.writeString(this.networkType);
        dest.writeString(this.androidVersion);
    }

    protected DeviceInfo(Parcel in)
    {
        this.deviceId = in.readString();
        this.deviceType = in.readString();
        this.description = in.readString();
        this.userAgent = in.readString();
        this.appId = in.readString();
        this.appVersion = in.readString();
        this.accountEmailId = in.readString();
        this.phoneNumber = in.readString();
        this.lang = in.readString();
        this.deviceName = in.readString();
        this.networkType = in.readString();
        this.androidVersion = in.readString();
    }

    public static final Creator<DeviceInfo> CREATOR = new Creator<DeviceInfo>()
    {
        @Override
        public DeviceInfo createFromParcel(Parcel source)
        {
            return new DeviceInfo(source);
        }

        @Override
        public DeviceInfo[] newArray(int size)
        {
            return new DeviceInfo[size];
        }
    };
}
