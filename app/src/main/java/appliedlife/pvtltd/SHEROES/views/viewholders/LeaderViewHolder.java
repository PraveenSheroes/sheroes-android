package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;

import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderBoardUserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 18/09/17.
 */

public class LeaderViewHolder extends BaseViewHolder<LeaderBoardUserSolrObj> {

   /* @Bind(R.id.profile_pic)
    ImageView mProfilePic;

    @Bind(R.id.crown)
    ImageView mCrown;

    @Bind(R.id.name)
    TextView mName;

    @Bind(R.id.likes_comments_count)
    TextView mDescription;

    @BindDimen(R.dimen.dp_size_40)
    int mUserPicSize;

    @BindDimen(R.dimen.dp_size_24)
    int mCrownHeight;

    @BindDimen(R.dimen.dp_size_18)
    int mCrownWidth;*/

    @Inject
    Preference<LoginResponse> userPreference;
    BaseHolderInterface viewInterface;
    public LeaderViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
    }

    @Override
    public void bindData(LeaderBoardUserSolrObj leaderBoardUserSolrObj, final Context context, int position) {


        if (leaderBoardUserSolrObj != null) {
            //leaderBoardUserSolrObj.getSubType()
         //   mDescription.setText(leaderObj.getDescription());
          //  if (CommonUtil.isNotEmpty(leaderObj.crownUrl)) {
          //      String trophyImageUrl = CommonUtil.getThumborUri(leaderObj.crownUrl, mCrownWidth, mCrownHeight);
          //      Glide.with(mCrown.getContext())
          //              .load(trophyImageUrl)
          //              .into(mCrown);
          //  }
         //   mName.setText(leaderObj.getNameOrTitle());
         /*   if (leaderObj.getThumbnailImageUrl() != null && CommonUtil.isNotEmpty(leaderObj.getThumbnailImageUrl())) {
                String userImage = CommonUtil.getThumborUri(leaderObj.getThumbnailImageUrl(), mUserPicSize, mUserPicSize);
                Glide.with(mProfilePic.getContext())
                        .load(userImage)
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(context)))
                        .into(mProfilePic);
            }*/
        }
    }
    @Override
    public void viewRecycled() {

    }
    @Override
    public void onClick(View view) {
    /*    if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onChampionProfileClicked();
        }*/
    }

}
