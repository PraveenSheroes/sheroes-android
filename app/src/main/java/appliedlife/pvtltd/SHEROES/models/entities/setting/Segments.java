
package appliedlife.pvtltd.SHEROES.models.entities.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Segments {

    @SerializedName("1")
    @Expose
    private appliedlife.pvtltd.SHEROES.models.entities.setting._1 _1;
    @SerializedName("2")
    @Expose
    private _2 _2;
    @SerializedName("3")
    @Expose
    private appliedlife.pvtltd.SHEROES.models.entities.setting._3 _3;

    public appliedlife.pvtltd.SHEROES.models.entities.setting._1 get1() {
        return _1;
    }

    public void set1(appliedlife.pvtltd.SHEROES.models.entities.setting._1 _1) {
        this._1 = _1;
    }

    public _2 get2() {
        return _2;
    }

    public void set2(_2 _2) {
        this._2 = _2;
    }

    public appliedlife.pvtltd.SHEROES.models.entities.setting._3 get3() {
        return _3;
    }

    public void set3(appliedlife.pvtltd.SHEROES.models.entities.setting._3 _3) {
        this._3 = _3;
    }

}
