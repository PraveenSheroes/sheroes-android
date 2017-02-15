package appliedlife.pvtltd.SHEROES.basecomponents.baseresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class BaseResponse
{
    @SerializedName("errors")
    @Expose
    protected ServiceErrors serviceErrors;
    @SerializedName("status")
    @Expose
    protected ResponseStatus responseStatus;

    public ServiceErrors getServiceErrors()
    {
        return serviceErrors;
    }

    public void setServiceErrors(ServiceErrors serviceErrors)
    {
        this.serviceErrors = serviceErrors;
    }

    public ResponseStatus getResponseStatus()
    {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus)
    {
        this.responseStatus = responseStatus;
    }
}