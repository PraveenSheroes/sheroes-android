package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.community.BadgeDetails;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.BadgeClosetAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.DATE_FORMAT;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;

/**
 * Created by Ravi on 24-07-18
 */

public class BadgeClosetViewHolder extends RecyclerView.ViewHolder {

    //region constant declaration
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final String UNAVAILABLE_TEXT = "NA";
    //endregion

    //region private member declaration
    private Context mContext;
    private BadgeClosetAdapter.OnItemClickListener onItemClickListener;
    //endregion

    // region Butterknife Bindings
    @Bind(R.id.badge_icon)
    ImageView mBadgeIcon;

    @Bind(R.id.earned_badge_count)
    TextView mEarnedBadgeCount;

    @Bind(R.id.badge_title)
    TextView badgeTitle;

    @Bind(R.id.bade_won_date)
    TextView badgeWonDate;
    //endregion

    public BadgeClosetViewHolder(View view, Context context, BadgeClosetAdapter.OnItemClickListener onItemClickListener) {
        super(view);
        ButterKnife.bind(this, view);
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void bindData(final BadgeDetails badgeDetails) {
        if (CommonUtil.isNotEmpty(badgeDetails.getImageUrl())) {
            Glide.with(mContext)
                    .load(badgeDetails.getImageUrl())
                    .into(mBadgeIcon);
        } else {
            mBadgeIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_empty_badge));
        }

        badgeTitle.setText(badgeDetails.getName());

        mEarnedBadgeCount.setText(String.valueOf(numericToThousand(badgeDetails.getBadgeCount())));

        if (badgeDetails.isActive()) {
            badgeWonDate.setText(mContext.getResources().getString(R.string.badge_closet_won_this_week_text));
            mBadgeIcon.setBackgroundResource(R.drawable.circular_background_yellow);
            mEarnedBadgeCount.setBackgroundResource(R.drawable.ic_active_badge_blank);
            mEarnedBadgeCount.setTextColor(ContextCompat.getColor(mContext, R.color.earned_badge_count));
        } else {
            mBadgeIcon.setBackgroundResource(R.drawable.circular_background_grey);
            mEarnedBadgeCount.setBackgroundResource(R.drawable.ic_inactive_badge);
            mEarnedBadgeCount.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            String startDate = badgeDetails.getSolrIgnoreStartDate();

            if (startDate != null) {
                Date startDateObj = DateUtil.parseDateFormat(startDate, DATE_FORMAT);
                //date when the badge won.
                String startDateText = dateFormat.format(startDateObj);
                badgeWonDate.setText(mContext.getResources().getString(R.string.badge_closet_won_date_text, startDateText));
            } else {
                badgeWonDate.setText(mContext.getResources().getString(R.string.badge_closet_won_date_text, UNAVAILABLE_TEXT));
            }
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onItemClickListener.onItemClick(badgeDetails);
            }
        });
    }

}