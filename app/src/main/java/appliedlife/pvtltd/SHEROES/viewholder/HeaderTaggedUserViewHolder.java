package appliedlife.pvtltd.SHEROES.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.UserTagCallback;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.TaggedUserPojo;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 06/03/18.
 */

public class HeaderTaggedUserViewHolder extends BaseViewHolder<Suggestible> {
    private Suggestible suggestible;
    private UserTagCallback userTagCallback;

    @Bind(R.id.tv_header)
    TextView mTvHeader;
    public HeaderTaggedUserViewHolder(View itemView, UserTagCallback userTagCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.userTagCallback = userTagCallback;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);

    }

    @Override
    public void bindData(Suggestible suggestible, Context context, int position) {
        this.suggestible = suggestible;
        String name = ((TaggedUserPojo) suggestible).getName();
        if (StringUtil.isNotNullOrEmptyString(name)) {
            mTvHeader.setText(name);
        }
    }


    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }
}
