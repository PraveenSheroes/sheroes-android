package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HashTagsViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.tv_hashtag)TextView hashTagTxt;

    public HashTagsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    @OnClick(R.id.tv_hashtag)
    public void onHashTagClick(){
        int position = getAdapterPosition();


    }

    public TextView getHashTagTxt() {
        return hashTagTxt;
    }

    public void setHashTagTxt(TextView hashTagTxt) {
        this.hashTagTxt = hashTagTxt;
    }
}
