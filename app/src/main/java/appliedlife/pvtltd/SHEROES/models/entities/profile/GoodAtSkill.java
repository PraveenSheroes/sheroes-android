package appliedlife.pvtltd.SHEROES.models.entities.profile;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by sheroes on 07/04/17.
 */
@Parcel(analyze = {GoodAtSkill.class})
public class GoodAtSkill{
    private Long id;
    private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
