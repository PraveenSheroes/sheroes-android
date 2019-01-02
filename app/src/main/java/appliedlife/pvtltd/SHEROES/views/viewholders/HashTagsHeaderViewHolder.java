package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import appliedlife.pvtltd.SHEROES.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HashTagsHeaderViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_hashtag_header)TextView hashTagHeaderTxt;

    public HashTagsHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getHashTagHeaderTxt() {
        return hashTagHeaderTxt;
    }

    public void setHashTagHeaderTxt(TextView hashTagHeaderTxt) {
        this.hashTagHeaderTxt = hashTagHeaderTxt;
    }
}
