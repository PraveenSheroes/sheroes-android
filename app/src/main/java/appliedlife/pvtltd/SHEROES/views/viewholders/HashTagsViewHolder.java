package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.IHashTagCallBack;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HashTagsViewHolder extends RecyclerView.ViewHolder{
    //region view variables
    @Bind(R.id.tv_hashtag)TextView hashTagTxt;
    @Bind(R.id.ll_hashtag)LinearLayout hashTagLayout;
    //endregion view variables

    //region member variables
    private List<String> mHashTagsList;
    private IHashTagCallBack mIHashTagCallBack;
    //endregion member variables

    //region constructor
    public HashTagsViewHolder(View itemView, IHashTagCallBack iHashTagCallBack, List<String> hashTagsList) {
        super(itemView);
        this.mIHashTagCallBack = iHashTagCallBack;
        this.mHashTagsList = hashTagsList;

        ButterKnife.bind(this, itemView);

    }
    //endregion constructor

    //region click methods
    @OnClick(R.id.ll_hashtag)
    public void onHashTagClick() {
        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
            String query = mHashTagsList.get(getAdapterPosition() - 1).startsWith("#")
                    ? mHashTagsList.get(getAdapterPosition() - 1).substring(1) : mHashTagsList.get(getAdapterPosition() - 1);
            mIHashTagCallBack.onHashTagClicked(query);
        }
    }
    //endregion click methods

    //region public methods
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
    //endregion public methods
}
