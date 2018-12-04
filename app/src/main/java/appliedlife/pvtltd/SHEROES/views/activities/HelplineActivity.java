package appliedlife.pvtltd.SHEROES.views.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineChatDoc;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.HelplineFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HelplineActivity extends BaseActivity implements BaseHolderInterface {
    private static final String TAG = HelplineActivity.class.getName();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.title_toolbar)
    TextView titleToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);
        ButterKnife.bind(this);

        setupToolbarItemsColor();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.helplineActivityFrameLayout, new HelplineFragment(), TAG)
                .commit();
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }

    @Override
    public String getScreenName() {
        return null;
    }

    private void setupToolbarItemsColor() {
        setSupportActionBar(mToolbar);
        titleToolbar.setText(R.string.helpline_title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        Fragment helplineFragment = getSupportFragmentManager().findFragmentById(R.id.helplineActivityFrameLayout);
        if (AppUtils.isFragmentUIActive(helplineFragment)) {
            HelplineChatDoc helplineChatDoc = (HelplineChatDoc) baseResponse;
            ((HelplineFragment) helplineFragment).checkHelplineRating(helplineChatDoc);
        }
    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {
    }

    @Override
    public void setListData(BaseResponse data, boolean flag) {
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
    }

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {
    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {
    }
}
