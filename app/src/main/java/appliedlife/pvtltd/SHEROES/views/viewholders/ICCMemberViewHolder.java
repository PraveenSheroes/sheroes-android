package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMember;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHEROES 005 on 27-May-17.
 */

public class ICCMemberViewHolder extends BaseViewHolder<ICCMember> {

    private ICCMember iccMember;

    BaseHolderInterface baseHolderInterface;

    @Bind(R.id.tv_icc_member_photo)
    RoundedImageView ivICCProfileCircleIcon;

    @Bind(R.id.tv_icc_name)
    TextView tvICCName;

    @Bind(R.id.tv_icc_description)
    TextView tvICCDescription;


    public ICCMemberViewHolder(View view, BaseHolderInterface baseHolderInterface){
        super(view);
        ButterKnife.bind(this,  view);
        this.baseHolderInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(view.getContext()).inject(this);
    }

    @Override
    public void bindData(ICCMember iccMember, Context context, int position) {
        this.iccMember = iccMember;
        if(iccMember.getPhotoUrl()!=null){
            Glide.with(context)
                    .load(iccMember.getPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivICCProfileCircleIcon);
        }
        if(iccMember.getName()!=null){
            tvICCName.setText(iccMember.getName());
        }
        String description = null;
        if(iccMember.getTitle() != null){
            description  = iccMember.getTitle();

            if(iccMember.getOrganization()!=null){
                description = description + " - " + iccMember.getOrganization();
            }
        }else if(iccMember.getOrganization()!=null){
            description = iccMember.getOrganization();
        }

        if(iccMember.getTitle()!=null || iccMember.getOrganization()!=null){
            tvICCDescription.setText(description);
        }
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }
}
