package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMember;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
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


    public ICCMemberViewHolder(View view, BaseHolderInterface baseHolderInterface) {
        super(view);
        ButterKnife.bind(this, view);
        this.baseHolderInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(view.getContext()).inject(this);
    }

    @Override
    public void bindData(ICCMember iccMember, Context context, int position) {
        this.iccMember = iccMember;
        if (StringUtil.isNotNullOrEmptyString(iccMember.getPhotoUrl())) {
            Glide.with(context)
                    .load(iccMember.getPhotoUrl())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA).skipMemoryCache(true))
                    .into(ivICCProfileCircleIcon);
        }
        if (StringUtil.isNotNullOrEmptyString(iccMember.getName())) {
            tvICCName.setText(iccMember.getName());
        }
        String description = null;
        if (StringUtil.isNotNullOrEmptyString(iccMember.getTitle())) {
            description = iccMember.getTitle();

            if (StringUtil.isNotNullOrEmptyString(iccMember.getOrganization())) {
                description = description + " - " + iccMember.getOrganization();
            }
        } else if (StringUtil.isNotNullOrEmptyString(iccMember.getOrganization())) {
            description = iccMember.getOrganization();
        }

        if (StringUtil.isNotNullOrEmptyString(iccMember.getTitle()) || StringUtil.isNotNullOrEmptyString(iccMember.getOrganization())) {
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
