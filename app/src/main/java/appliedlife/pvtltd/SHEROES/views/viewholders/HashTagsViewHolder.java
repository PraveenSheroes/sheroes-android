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
    @Bind(R.id.tv_hashtag)TextView hashTagTxt;
    @Bind(R.id.ll_hashtag)LinearLayout hashTagLayout;
    private List<String> hashTagsList;
    private IHashTagCallBack iHashTagCallBack;

    public HashTagsViewHolder(View itemView, IHashTagCallBack iHashTagCallBack, List<String> hashTagsList) {
        super(itemView);
        this.iHashTagCallBack = iHashTagCallBack;
        this.hashTagsList = hashTagsList;

        ButterKnife.bind(this, itemView);

    }

    @OnClick(R.id.ll_hashtag)
    public void onHashTagClick(){
        String query = hashTagsList.get(getAdapterPosition() - 1).startsWith("#")
                ? hashTagsList.get(getAdapterPosition() - 1).substring(1) : hashTagsList.get(getAdapterPosition() - 1);
        iHashTagCallBack.onHashTagClicked(query);
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
