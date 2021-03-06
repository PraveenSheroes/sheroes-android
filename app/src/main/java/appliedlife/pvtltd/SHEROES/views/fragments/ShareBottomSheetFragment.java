package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences2.Preference;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ujjwal on 27-10-2018.
 */
public class ShareBottomSheetFragment extends BottomSheetDialogFragment {

    //region Private Variables
    private static final String SCREEN_LABEL = "ShareBottomSheetFragment";
    private static final String SHARE_DEEPLINK = "share deep link";
    private static final String PROPERTY = "Property";
    private static final String EVENT_NAME = "Event name";
    private static final String SHARE_IMAGE = "share image";
    private static final String SHARE_TEXT = "share text";
    private static final String SHARE_COPYLINK = "copy link";
    private static final String IS_IMAGE_SHARE = "Is Image Share";
    private static final String IS_CHALLENGE = "Is Challenge";
    private static final String SHOW_TITLE = "Show Title";
    private static final String IS_APP_LINK = "Is App Link";
    private static final String SHARE_DIALOG_TITLE = "Share Dialog Title";

    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    private android.content.ClipboardManager myClipboard;
    private ClipData myClip;

    private String mShareImageUrl;
    private String mShareText;
    private String mShareDeepLinkUrl;
    private String mShareCopyLink;
    private String mSourceScreen;
    private boolean mIsImageShare;
    private boolean mIsChallenge;
    private boolean mShowTitle;
    private boolean mIsAppLink;
    private String mShareDialogTitle;
    private Event mEventName;
    private HashMap<String, Object> mProperties;

    //endregion

    // region View variables
    @Bind(R.id.layout_save_to_gallery)
    LinearLayout layoutSaveToGallery;

    @Bind(R.id.title)
    TextView title;

    private Contest mContest;
    private boolean isUserShareBitmap;
    private Bitmap userShareBitmap;
    // endregion

    //region Fragment LifeCycle Methods
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        myClipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (getArguments() != null) {
            Parcelable parcelable;
            if (getArguments().getParcelable(AppConstants.CHALLENGE_GRATIFICATION_OBJ) != null) {
                parcelable = getArguments().getParcelable(AppConstants.CHALLENGE_GRATIFICATION_OBJ);
                mContest = Parcels.unwrap(parcelable);
                byte[] byteArray = getArguments().getByteArray("image");
                userShareBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                isUserShareBitmap = true;
            }
            mShareDeepLinkUrl = getArguments().getString(SHARE_DEEPLINK);
            mShareImageUrl = getArguments().getString(SHARE_IMAGE);
            mShareText = getArguments().getString(SHARE_TEXT);
            mShareCopyLink = getArguments().getString(SHARE_COPYLINK);
            mShareDialogTitle = getArguments().getString(SHARE_DIALOG_TITLE);
            mSourceScreen = getArguments().getString(BaseActivity.SOURCE_SCREEN);
            mIsImageShare = getArguments().getBoolean(IS_IMAGE_SHARE, false);
            mIsChallenge = getArguments().getBoolean(IS_CHALLENGE, false);
            mShowTitle = getArguments().getBoolean(SHOW_TITLE, false);
            mIsAppLink = getArguments().getBoolean(IS_APP_LINK, false);
            if(getArguments().getSerializable(PROPERTY)!=null){
                mProperties = (HashMap<String, Object>) getArguments().getSerializable(PROPERTY);
            }else {
                mProperties = null;
            }
            if(getArguments().getSerializable(EVENT_NAME)!=null){
                mEventName = (Event) getArguments().getSerializable(EVENT_NAME);
            }else {
                mEventName = null;
            }
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View containerView = View.inflate(getContext(), R.layout.dialog_share, null);
        dialog.setContentView(containerView);
        ButterKnife.bind(this, containerView);
        if (mShowTitle) {
            title.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.GONE);
        }
        if(CommonUtil.isNotEmpty(mShareDialogTitle)){
            title.setVisibility(View.VISIBLE);
            title.setText(mShareDialogTitle);
        }else {
            title.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //endregion

    //region Public Static methods
    public static void showDialog(AppCompatActivity activity, Contest contest, Bitmap bitmap, String sourceScreen) {
        ShareBottomSheetFragment shareBottomSheetFragment = new ShareBottomSheetFragment();
        Bundle args = new Bundle();
        args.putParcelable(AppConstants.CHALLENGE_GRATIFICATION_OBJ, Parcels.wrap(contest));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        args.putByteArray("image", byteArray);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        shareBottomSheetFragment.setArguments(args);
        shareBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
    }
    //endregion

    //region Public Static methods
    public static ShareBottomSheetFragment showDialog(AppCompatActivity activity, String shareText, String shareImage, String shareDeepLinkUrl, String sourceScreen, boolean isImage, String shareCopyLink, boolean isChallenge, Event event,  HashMap<String, Object> properties) {
        ShareBottomSheetFragment shareBottomSheetFragment = new ShareBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(SHARE_TEXT, shareText);
        args.putString(SHARE_DEEPLINK, shareDeepLinkUrl);
        args.putString(SHARE_IMAGE, shareImage);
        args.putString(SHARE_COPYLINK, shareCopyLink);
        args.putBoolean(IS_IMAGE_SHARE, isImage);
        args.putBoolean(IS_CHALLENGE, isChallenge);
        args.putSerializable(PROPERTY, properties);
        args.putSerializable(EVENT_NAME, event);
        shareBottomSheetFragment.setArguments(args);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        shareBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return shareBottomSheetFragment;
    }


    public static ShareBottomSheetFragment showDialog(AppCompatActivity activity, String shareText, String shareImage, String shareDeepLinkUrl, String sourceScreen, boolean isImage, String shareCopyLink, boolean isChallenge, boolean showTitle, boolean isAppLink) {
        ShareBottomSheetFragment shareBottomSheetFragment = new ShareBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(SHARE_TEXT, shareText);
        args.putString(SHARE_DEEPLINK, shareDeepLinkUrl);
        args.putString(SHARE_IMAGE, shareImage);
        args.putString(SHARE_COPYLINK, shareCopyLink);
        args.putBoolean(IS_IMAGE_SHARE, isImage);
        args.putBoolean(IS_CHALLENGE, isChallenge);
        args.putBoolean(SHOW_TITLE, showTitle);
        args.putBoolean(IS_APP_LINK, isAppLink);
        shareBottomSheetFragment.setArguments(args);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        shareBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return shareBottomSheetFragment;
    }
    public static ShareBottomSheetFragment showDialog(AppCompatActivity activity, String shareText, String shareImage, String shareDeepLinkUrl, String sourceScreen, boolean isImage, String shareCopyLink, boolean isChallenge, boolean showTitle, boolean isAppLink,Event event) {
        ShareBottomSheetFragment shareBottomSheetFragment = new ShareBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(SHARE_TEXT, shareText);
        args.putString(SHARE_DEEPLINK, shareDeepLinkUrl);
        args.putString(SHARE_IMAGE, shareImage);
        args.putString(SHARE_COPYLINK, shareCopyLink);
        args.putBoolean(IS_IMAGE_SHARE, isImage);
        args.putBoolean(IS_CHALLENGE, isChallenge);
        args.putBoolean(SHOW_TITLE, showTitle);
        args.putBoolean(IS_APP_LINK, isAppLink);
        args.putSerializable(EVENT_NAME, event);
        shareBottomSheetFragment.setArguments(args);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        shareBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return shareBottomSheetFragment;
    }
    public static ShareBottomSheetFragment showDialog(AppCompatActivity activity, String shareText, String shareImage, String shareDeepLinkUrl, String sourceScreen, boolean isImage, String shareCopyLink, boolean isChallenge, boolean showTitle, boolean isAppLink, String shareDialogTitle) {
        ShareBottomSheetFragment shareBottomSheetFragment = new ShareBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(SHARE_TEXT, shareText);
        args.putString(SHARE_DEEPLINK, shareDeepLinkUrl);
        args.putString(SHARE_IMAGE, shareImage);
        args.putString(SHARE_COPYLINK, shareCopyLink);
        args.putBoolean(IS_IMAGE_SHARE, isImage);
        args.putBoolean(IS_CHALLENGE, isChallenge);
        args.putBoolean(SHOW_TITLE, showTitle);
        args.putBoolean(IS_APP_LINK, isAppLink);
        args.putString(SHARE_DIALOG_TITLE, shareDialogTitle);
        shareBottomSheetFragment.setArguments(args);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        shareBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return shareBottomSheetFragment;
    }

    public static ShareBottomSheetFragment showDialog(AppCompatActivity activity, String shareText, String shareImage, String shareDeepLinkUrl, String sourceScreen, boolean isImage, String shareCopyLink, boolean isChallenge, boolean showTitle, boolean isAppLink, Event event,  HashMap<String, Object> properties) {
        ShareBottomSheetFragment shareBottomSheetFragment = new ShareBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(SHARE_TEXT, shareText);
        args.putString(SHARE_DEEPLINK, shareDeepLinkUrl);
        args.putString(SHARE_IMAGE, shareImage);
        args.putString(SHARE_COPYLINK, shareCopyLink);
        args.putBoolean(IS_IMAGE_SHARE, isImage);
        args.putBoolean(IS_CHALLENGE, isChallenge);
        args.putBoolean(SHOW_TITLE, showTitle);
        args.putBoolean(IS_APP_LINK, isAppLink);
        args.putSerializable(PROPERTY, properties);
        args.putSerializable(EVENT_NAME, event);
        shareBottomSheetFragment.setArguments(args);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        shareBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return shareBottomSheetFragment;
    }

    //endregion

    //region OnClicks
    @OnClick({R.id.layout_whatsapp, R.id.image_whatsapp, R.id.text_whatsapp})
    public void onWhatsAppClick() {
        if (isUserShareBitmap) {
            Config config = Config.getConfig();
            String whatsAppShareText = mContest.shortUrl;
           // CommonUtil.shareBitmapWhatsApp(getContext(), userShareBitmap, mSourceScreen, mContest.thumbImage, whatsAppShareText, Event.CHALLENGE_GRATIFICATION_WHATS);
            return;
        }
        if (mIsImageShare) {
            CommonUtil.shareImageWhatsApp(getContext(), mShareText, mShareImageUrl, mSourceScreen, true, mEventName, mProperties);
        } else {
            if (mShowTitle && mIsAppLink) {
                String shareWhatsappAppLink = "";
                if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get("APP_CONFIGURATION") != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get("APP_CONFIGURATION").get("APP_INVITE_MESSAGES_WHATSAPP"))) {
                    shareWhatsappAppLink = mUserPreferenceMasterData.get().getData().get("APP_CONFIGURATION").get("APP_INVITE_MESSAGES_WHATSAPP").get(0).getLabel();
                }
                mShareText = shareWhatsappAppLink + mShareDeepLinkUrl;
            }
            CommonUtil.shareLinkToWhatsApp(getContext(), mShareText);
            EventProperty.Builder builder = new EventProperty.Builder().sharedTo("Whatsapp");
            /*final HashMap<String, Object> properties = mToolTip.build();
            properties.put(EventProperty.SOURCE.getString(), mSourceScreen);
            properties.put(EventProperty.URL.getString(), mShareText);*/
            if(null==mProperties)
            {
                mProperties=new HashMap<>();
            }
            mProperties.put(EventProperty.SHARED_TO.getString(), "Whatsapp");
            AnalyticsManager.trackEvent(mEventName, mSourceScreen, mProperties);
        }
    }

    @OnClick({R.id.layout_copy_link, R.id.image_copy_link, R.id.text_copy_link})
    public void onCopyLinkClick() {
        if (!CommonUtil.isNotEmpty(mShareCopyLink)) {
            return;
        }
        myClip = ClipData.newPlainText("copy_link", mShareCopyLink);
        myClipboard.setPrimaryClip(myClip);
        if(StringUtil.isNotNullOrEmptyString(mSourceScreen)&&mSourceScreen.equalsIgnoreCase(AppConstants.CHALLENGE_GRATIFICATION_SCREEN))
        {
            HashMap<String, Object> properties = new EventProperty.Builder().sharedTo("Copy Link").build();
            properties.put(EventProperty.URL.getString(), mShareImageUrl);
            AnalyticsManager.trackEvent(Event.CHALLENGE_SHARED, mSourceScreen, properties);
        }else if(StringUtil.isNotNullOrEmptyString(mShareImageUrl)) {
            HashMap<String, Object> properties =new EventProperty.Builder().id(mShareImageUrl).build();
            AnalyticsManager.trackEvent(Event.IMAGE_COPY_LINK, mSourceScreen, properties);
        }else
        {
            HashMap<String, Object> properties = new EventProperty.Builder().sharedTo("Copy Link").build();
            AnalyticsManager.trackEvent(Event.FRIEND_INVITED, mSourceScreen, properties);
        }
        dismiss();
        Toast.makeText(getContext(), "Link Copied!", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.layout_facebook, R.id.image_facebook, R.id.text_facebook})
    public void onFacebookClick() {
        if (isUserShareBitmap) {
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(userShareBitmap)
                    .build();
            SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();
            ShareDialog shareDialog = new ShareDialog(getActivity());
            shareDialog.show(sharePhotoContent, ShareDialog.Mode.AUTOMATIC);
            if(StringUtil.isNotNullOrEmptyString(mSourceScreen)&&mSourceScreen.equalsIgnoreCase(AppConstants.CHALLENGE_GRATIFICATION_SCREEN))
            {
                HashMap<String, Object> properties = new EventProperty.Builder().sharedTo("Facebook").build();
                properties.put(EventProperty.URL.getString(), mShareImageUrl);
                AnalyticsManager.trackEvent(Event.CHALLENGE_SHARED, mSourceScreen, properties);
            }
            dismiss();
            return;
        }
        if (mIsImageShare && !mIsChallenge) {
            CompressImageUtil.createBitmap(SheroesApplication.mContext, mShareImageUrl, 816, 816)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<Bitmap>() {
                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Bitmap bmp) {
                            SharePhoto photo = new SharePhoto.Builder()
                                    .setBitmap(bmp)
                                    .build();
                            SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                                    .addPhoto(photo)
                                    .build();
                            ShareDialog shareDialog = new ShareDialog(getActivity());
                            shareDialog.show(sharePhotoContent, ShareDialog.Mode.AUTOMATIC);
                            if(StringUtil.isNotNullOrEmptyString(mSourceScreen)&&mSourceScreen.equalsIgnoreCase(AppConstants.CHALLENGE_GRATIFICATION_SCREEN))
                            {
                                HashMap<String, Object> properties = new EventProperty.Builder().sharedTo("Facebook").build();
                                properties.put(EventProperty.URL.getString(), mShareImageUrl);
                                AnalyticsManager.trackEvent(Event.CHALLENGE_SHARED, mSourceScreen, properties);
                            }else {
                                EventProperty.Builder builder = new EventProperty.Builder().sharedTo("Facebook");
                                /*final HashMap<String, Object> properties = mToolTip.build();
                                properties.put(EventProperty.URL.getString(), mShareImageUrl);*/
                                mProperties.put(EventProperty.SHARED_TO.getString(), "Facebook");
                                AnalyticsManager.trackEvent(mEventName, mSourceScreen, mProperties);
                            }
                        }
                    });
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType(AppConstants.SHARE_MENU_TYPE);
            String shareText = mShareDeepLinkUrl;
            if (mShowTitle && mIsAppLink) {
                String shareFacebookAppLink = "";
                if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get("APP_CONFIGURATION") != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get("APP_CONFIGURATION").get("APP_INVITE_MESSAGES_FB"))) {
                    shareFacebookAppLink = mUserPreferenceMasterData.get().getData().get("APP_CONFIGURATION").get("APP_INVITE_MESSAGES_FB").get(0).getLabel();
                }
                shareText = shareFacebookAppLink + mShareDeepLinkUrl;
            }
            EventProperty.Builder builder = new EventProperty.Builder().sharedTo("Facebook");
            final HashMap<String, Object> properties = builder.build();
            properties.put(EventProperty.SOURCE.getString(), mSourceScreen);
            properties.put(EventProperty.URL.getString(), shareText);
            AnalyticsManager.trackEvent(mEventName, mSourceScreen, properties);

            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            // See if official Facebook app is found
            boolean facebookAppFound = false;
            List<ResolveInfo> matches = getActivity().getPackageManager().queryIntentActivities(intent, 0);
            for (ResolveInfo info : matches) {
                if (info.activityInfo.packageName.toLowerCase().startsWith(AppConstants.FACEBOOK_SHARE)) {
                    intent.setPackage(info.activityInfo.packageName);
                    facebookAppFound = true;
                    break;
                }
            }
            // As fallback, launch sharer.php in a browser
            if (!facebookAppFound) {
                String sharerUrl = AppConstants.FACEBOOK_SHARE_VIA_BROSWER + shareText;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            }
            startActivity(intent);
        }
    }
    //endregion
}
