package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import org.parceler.Parcels;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.models.entities.post.Post;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Geet on 13-06-2016.
 */
public class ShareBottomSheetFragment extends BottomSheetDialogFragment {

    //region Private Variables
    private static final String SCREEN_LABEL = "ShareBottomSheetFragment";
    private static final String IMAGE_SHARE = "image share";
    private static final String VIDEO_SHARE = "video share";
    private static final String COMMA = "\"";
    private static final String PERCENTAGE_UNICODE = "%22";
    private static final String COPY_LINK = "Copy Link";
    private android.content.ClipboardManager myClipboard;
    private ClipData myClip;
    private String action;
    private String url;
    private String sourceScreen;
    private boolean isUserShareBitmap;
    private Bitmap userShareBitmap;
    //endregion

    // region View variables
    @Bind(R.id.layout_save_to_gallery)
    LinearLayout layoutSaveToGallery;

    //region Fragment LifeCycle Methods
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        myClipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View containerView = View.inflate(getContext(), R.layout.dialog_share, null);
        dialog.setContentView(containerView);
        ButterKnife.bind(this, containerView);
     /*   if (CommonUtil.isNotEmpty(action) && (action.equals(IMAGE_SHARE))) {
            layoutSaveToGallery.setVisibility(View.VISIBLE);
        } else {
            layoutSaveToGallery.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //endregion

    //region OnClicks


    //region Public Static methods
    public static ShareBottomSheetFragment showDialog(AppCompatActivity activity, String url, String sourceScreen, Post post) {
        ShareBottomSheetFragment shareBottomSheetFragment = new ShareBottomSheetFragment();
        Bundle args = new Bundle();
       // args.putString(Globals.IMAGE_SHARE_URL, url);
        Parcelable parcelablePost;
        parcelablePost = Parcels.wrap(post);
        args.putParcelable(Post.FEED_OBJ, parcelablePost);
        //args.putString(Globals.ACTION, IMAGE_SHARE);
        shareBottomSheetFragment.setArguments(args);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        shareBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return shareBottomSheetFragment;
    }
    //endregion

}
