package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.EducationEntityBO;
import appliedlife.pvtltd.SHEROES.models.entities.login.ExprienceEntityBO;
import appliedlife.pvtltd.SHEROES.models.entities.login.ProjectEntityBO;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserBO;

/**
 * Created by priyanka on 28/03/17.
 */

public class UserProfileResponse extends BaseResponse {

    @SerializedName("user_details")
    @Expose
    private UserDetails userDetails;

    public UserDetails getUserDetails() {
        return userDetails;
    }
}
