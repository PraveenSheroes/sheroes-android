package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;


/**
 * Created by ravi on 24/01/18.
 * Tooltip Messages
 */

public class ToolTipHelper {

    private Activity mContext;
    private String mMessage;
    private View mView;
    private PopupWindow mPopupWindow;
    private String mPreferenceId;
    private int mAddMargin;
    private boolean isArrowOnLeft = false;

    public ToolTipHelper(Activity context, String message, View view, String prefId, int addMargin) {
        this.mContext = context;
        this.mMessage = message;
        this.mView = view;
        this.mPreferenceId = prefId;
        this.mAddMargin = addMargin;
    }

    public void displayTooltip() {
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.tooltip_layout, null);
        mPopupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        if (isArrowOnLeft()) {
            ImageView arrow = (ImageView) popupView.findViewById(R.id.tooltip_nav);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT | Gravity.START;
            params.setMargins(10, 0, 0, 0);
            arrow.setLayoutParams(params);
        }

        TextView spacer = (TextView) popupView.findViewById(R.id.spacer);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, mAddMargin, 0, 0);
        spacer.setLayoutParams(params);

        TextView mMessageTextView = (TextView) popupView.findViewById(R.id.title);
        mMessageTextView.setText(mMessage);

        TextView closeButton = (TextView) popupView.findViewById(R.id.got_it);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtil.ensureFirstTime(mPreferenceId);
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.showAsDropDown(popupView, 0, 0);
    }


    private void addEvent(String eventName, String source) {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .name(eventName)
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_FOLLOWER_COUNT, source, properties);
    }

    public boolean isArrowOnLeft() {
        return isArrowOnLeft;
    }

    public void setArrowOnLeft(boolean arrowOnLeft) {
        isArrowOnLeft = arrowOnLeft;
    }
}
