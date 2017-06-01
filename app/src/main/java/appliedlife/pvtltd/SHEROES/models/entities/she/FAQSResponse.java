package appliedlife.pvtltd.SHEROES.models.entities.she;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SHEROES 005 on 30-May-17.
 */

public class FAQSResponse {
    @SerializedName("faqs")
    @Expose
    public List<FAQS> listOfFAQs;

    public List<FAQS> getListOfFAQs() {
        return listOfFAQs;
    }

    public void setListOfFAQs(List<FAQS> listOfFAQs) {
        this.listOfFAQs = listOfFAQs;
    }
}
