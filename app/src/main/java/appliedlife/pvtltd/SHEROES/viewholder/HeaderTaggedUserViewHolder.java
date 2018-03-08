package appliedlife.pvtltd.SHEROES.viewholder;

import android.content.Context;
import android.view.View;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.UserTagCallback;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 06/03/18.
 */

public class HeaderTaggedUserViewHolder extends BaseViewHolder<Suggestible> {
    private Suggestible suggestible;
    private UserTagCallback userTagCallback;


    public HeaderTaggedUserViewHolder(View itemView, UserTagCallback userTagCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.userTagCallback = userTagCallback;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);

    }

    @Override
    public void bindData(Suggestible suggestible, Context context, int position) {
        this.suggestible = suggestible;
    }


    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }
}
