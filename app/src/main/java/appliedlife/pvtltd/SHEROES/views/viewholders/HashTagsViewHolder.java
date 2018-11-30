package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.views.fragments.HashTagFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HashTagsViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.tv_hashtag)TextView hashTagTxt;
    @Bind(R.id.ll_hashtag)LinearLayout hashTagLayout;
    private HashTagFragment hashTagFragment;

    public HashTagsViewHolder(View itemView, HashTagFragment hashTagFragment) {
        super(itemView);
        this.hashTagFragment = hashTagFragment;
        ButterKnife.bind(this, itemView);

    }

    @OnClick(R.id.ll_hashtag)
    public void onHashTagClick(){
        int position = getAdapterPosition();

        hashTagFragment.onHashTagClicked();

    }

    public TextView getHashTagTxt() {
        return hashTagTxt;
    }

    public void setHashTagTxt(TextView hashTagTxt) {
        this.hashTagTxt = hashTagTxt;
    }

    public LinearLayout getHashTagLayout() {
        return hashTagLayout;
    }

    public void setHashTagLayout(LinearLayout hashTagLayout) {
        this.hashTagLayout = hashTagLayout;
    }
}
