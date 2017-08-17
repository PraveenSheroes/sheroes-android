package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.LatLongWithLocation;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.MakeIndiaSafeDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.createCommunityPostRequestBuilder;

/**
 * Created by Praveen_Singh on 17-08-2017.
 */

public class MakeIndiaSafeHolder extends BaseViewHolder<MakeIndiaSafeDetail> {
    private final String TAG = LogUtils.makeLogTag(MakeIndiaSafeHolder.class);
    @Inject
    AppUtils mAppUtils;
    @Inject
    Preference<LoginResponse> mUserPreference;
    LatLongWithLocation mLatLongWithLocation;
    @Inject
    DateUtil mDateUtil;
    BaseHolderInterface viewInterface;
    private MakeIndiaSafeDetail dataItem;
    Context mContext;
    @Bind(R.id.cb_post_as_first_name)
    CheckBox mCbPostName;
    @Bind(R.id.cb_post_as_annonyms)
    CheckBox mCbPostAsAnnoyms;
    @Bind(R.id.cb_post_as_first_name_by_link)
    CheckBox mCbPostNameByLink;
    @Bind(R.id.cb_post_as_annonyms_by_link)
    CheckBox mCbPostAsAnnoymsByLink;
    @Bind(R.id.scroll_make_india_safe_events)
    LinearLayout scrollMakeIndiaSafeEvent;
    @Bind(R.id.scroll_make_india_safe_image_holder)
    LinearLayout scrollMakeIndiaSafeImageHolder;
    @Bind(R.id.iv_make_india_safe_pic)
    ImageView ivMakeIndiaSafePic;
    @Bind(R.id.tv_click_pic_and_tell_friends)
    TextView tvClickPicAndTellFrnd;
    @Bind(R.id.tv_share_with_friends)
    TextView tvShareWithFriends;
    @Bind(R.id.tv_message_for_safe)
    TextView tvTvMessageForSafe;
    @Bind(R.id.et_make_india_safe_description)
    EditText etTvMakeIndiaSafeDescription;
    private Long mCommunityId = 273l;
    private String encodedImageUrl;
    private String messageFirst = "Hey, I have been seeing events of women getting harassed in ";
    private String messageSecond = "I think we should do something about it. How should we go ahead? #MakeIndiaSafe I shared this via SHEROES app- a women only safe space to talk openly without getting judged. You should also install the app from play store here: tinyurl.com/sheroes-app-safe-india";
    private String local = " my locality ";
    private String mCreaterType;
    private File localImageSaveForChallenge;
    private boolean isCreatedPost;

    public MakeIndiaSafeHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(MakeIndiaSafeDetail item, Context context, int position) {
        this.dataItem = item;
        mContext = context;
        initialize();
    }

    private void initialize() {
        mCreaterType = mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS);
        mCbPostAsAnnoyms.setChecked(true);
        mCbPostAsAnnoymsByLink.setChecked(true);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getFirstName())) {
            mCbPostName.setText(mUserPreference.get().getUserSummary().getFirstName());
            mCbPostNameByLink.setText(mUserPreference.get().getUserSummary().getFirstName());
        }
        mLatLongWithLocation = dataItem.getLatLongWithLocation();
        if (dataItem.isPicClick()) {
            scrollMakeIndiaSafeEvent.setVisibility(View.GONE);
            scrollMakeIndiaSafeImageHolder.setVisibility(View.VISIBLE);
            if (null != dataItem.getBitmap() && null != dataItem.getLocalImageSaveForChallenge()) {
                setImageOnHolder(dataItem.getBitmap(), dataItem.getLocalImageSaveForChallenge());
            }
        }else
        {
            scrollMakeIndiaSafeEvent.setVisibility(View.VISIBLE);
            scrollMakeIndiaSafeImageHolder.setVisibility(View.GONE);
            if (null != mLatLongWithLocation) {
                if (StringUtil.isNotNullOrEmptyString(mLatLongWithLocation.getLocality())) {
                    String locality = mContext.getString(R.string.ID_NO) + AppConstants.COMMA+ AppConstants.SPACE  + mLatLongWithLocation.getLocality() + AppConstants.SPACE + mContext.getString(R.string.ID_LOCALITY_NAME);
                    tvTvMessageForSafe.setText(locality);
                } else {
                    String locality = mContext.getString(R.string.ID_NO) + AppConstants.COMMA + AppConstants.SPACE + local + AppConstants.SPACE + mContext.getString(R.string.ID_LOCALITY_NAME);
                    tvTvMessageForSafe.setText(locality);
                }
            }
        }
    }

    @OnClick(R.id.tv_message_for_safe)
     public void onMessafeForSafe() {
         if (null != mLatLongWithLocation) {
             StringBuilder desc = new StringBuilder();
             if (StringUtil.isNotNullOrEmptyString(mLatLongWithLocation.getLocality())) {
                 desc.append("My locality,").append(AppConstants.SPACE).append(mLatLongWithLocation.getLocality()).append(AppConstants.SPACE).append(mLatLongWithLocation.getCityName()).append(AppConstants.SPACE).append("is a pretty safe place for women. #MakeIndiaSafe");
             } else {
                 desc.append("My locality,").append("is a pretty safe place for women. #MakeIndiaSafe");
             }
             mLatLongWithLocation.setDescription(desc.toString());
             CommunityPostCreateRequest communityPostCreateRequest = createCommunityPostRequestBuilder(mCommunityId, mCreaterType, mLatLongWithLocation.getDescription(), null, null, null);
             communityPostCreateRequest.setLattitude(mLatLongWithLocation.getLatitude());
             communityPostCreateRequest.setLongitude(mLatLongWithLocation.getLongitude());
           //  mCreateCommunityPresenter.postCommunityList(communityPostCreateRequest);
             dataItem.setCommunityPostCreateRequest(communityPostCreateRequest);
             dataItem.setLinkClicked(true);
             viewInterface.handleOnClick(dataItem, tvShareWithFriends);
         }
     }
 /*
     @OnClick(R.id.tv_tell_your_friends)
     public void onTellYourFriend() {
         StringBuilder shareData = new StringBuilder();
         if (StringUtil.isNotNullOrEmptyString(mLatLongWithLocation.getLocality())) {
             shareData.append(messageFirst).append(AppConstants.SPACE).append(mLatLongWithLocation.getLocality()).append(AppConstants.SPACE).append(messageSecond);
         } else {
             shareData.append(messageFirst).append(local).append(messageSecond);
         }
         Intent intent = new Intent(Intent.ACTION_SEND);
         intent.setType(AppConstants.SHARE_MENU_TYPE);
         intent.putExtra(Intent.EXTRA_TEXT, shareData.toString());
         startActivity(Intent.createChooser(intent, AppConstants.SHARE));
     }
   */

    @OnClick(R.id.cb_post_as_first_name)
    public void OnPostAsNameClick() {
        mCbPostAsAnnoyms.setChecked(false);
        mCreaterType = AppConstants.USER;
    }

    @OnClick(R.id.cb_post_as_annonyms)
    public void OnPostAnnonymsClick() {
        mCbPostName.setChecked(false);
        mCreaterType = mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS);
    }

    @OnClick(R.id.cb_post_as_first_name_by_link)
    public void OnPostAsNameByLinkClick() {
        mCbPostAsAnnoymsByLink.setChecked(false);
        mCreaterType = AppConstants.USER;
    }

    @OnClick(R.id.cb_post_as_annonyms_by_link)
    public void OnPostAnnonymsByLinkClick() {
        mCbPostNameByLink.setChecked(false);
        mCreaterType = mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS);
    }

    @OnClick(R.id.tv_share_with_friends)
    public void onShareClick() {
        if (null != mLatLongWithLocation) {
          //  tvShareWithFriends.setBackgroundResource(R.drawable.rounded_rectangle_active_signup);
            List<String> imag = new ArrayList<>();
            Long mIdForEditPost = null;
            mLatLongWithLocation.setDescription(etTvMakeIndiaSafeDescription.getText().toString());
            if (StringUtil.isNotNullOrEmptyString(encodedImageUrl)) {
                imag.add(encodedImageUrl);
            }
            CommunityPostCreateRequest communityPostCreateRequest = createCommunityPostRequestBuilder(mCommunityId, mCreaterType, etTvMakeIndiaSafeDescription.getText().toString(), imag, mIdForEditPost, null);
            communityPostCreateRequest.setLattitude(mLatLongWithLocation.getLatitude());
            communityPostCreateRequest.setLongitude(mLatLongWithLocation.getLongitude());
            dataItem.setCommunityPostCreateRequest(communityPostCreateRequest);
            dataItem.setLinkClicked(false);
            viewInterface.handleOnClick(dataItem, tvShareWithFriends);
        }
    }


    public void setImageOnHolder(Bitmap photo, File localImageSaveForChallenge) {
        this.localImageSaveForChallenge = localImageSaveForChallenge;
        ivMakeIndiaSafePic.setImageBitmap(photo);
        StringBuilder shareData = new StringBuilder();
        if (StringUtil.isNotNullOrEmptyString(mLatLongWithLocation.getLocality())) {
            shareData.append(messageFirst).append(AppConstants.SPACE).append(mLatLongWithLocation.getLocality()).append(AppConstants.SPACE).append(messageSecond);
        } else {
            shareData.append(messageFirst).append(local).append(messageSecond);
        }
        etTvMakeIndiaSafeDescription.setText(shareData.toString());
        etTvMakeIndiaSafeDescription.setSelection(shareData.toString().length());
        etTvMakeIndiaSafeDescription.setCursorVisible(true);
        etTvMakeIndiaSafeDescription.setFocusableInTouchMode(true);
        etTvMakeIndiaSafeDescription.requestFocus();
        byte[] buffer = new byte[4096];
        if (null != photo) {
            buffer = getBytesFromBitmap(photo);
            if (null != buffer) {
                encodedImageUrl = Base64.encodeToString(buffer, Base64.DEFAULT);
            }
        }
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    @OnClick(R.id.tv_click_pic_and_tell_friends)
    public void onClickPicTellFriend() {
        viewInterface.handleOnClick(dataItem, tvClickPicAndTellFrnd);
    }

    @Override
    public void onClick(View view) {


    }

    @Override
    public void viewRecycled() {

    }
}




