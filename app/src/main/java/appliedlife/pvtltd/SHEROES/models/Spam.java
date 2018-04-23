
package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Spam {

    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("score")
    @Expose
    private int score;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
