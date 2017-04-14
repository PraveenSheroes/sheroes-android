package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

/**
 * Created by Dilip.Chaudhary on 13/4/17.
 */

public class WorkExperienceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "WorkExperienceAdapter";

    private String[] mData;
    private int mSelectedPosition = -1;

    private Context mContext;
    private ExperienceCallable mCallback;

    public WorkExperienceAdapter(Context context, String[] data) {
        mContext = context;
        mData = data;
        try {
            mCallback = (ExperienceCallable) context;
        } catch (ClassCastException ex) {
            LogUtils.error(TAG, "ProfessionalWorkExperienceActivity must implements ExperienceCallable", ex);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_experience_type_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        int textColor;
        viewHolder.titleTxt.setText(mData[holder.getAdapterPosition()]);
        if (position == mSelectedPosition) {
            textColor = ContextCompat.getColor(mContext, R.color.search_tab_text);
            viewHolder.tickCb.setChecked(true);
        } else {
            textColor = ContextCompat.getColor(mContext, R.color.searchbox_hint_text_color);
            viewHolder.tickCb.setChecked(false);
        }

        viewHolder.titleTxt.setTextColor(textColor);

        viewHolder.titleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.tickCb.performClick();
            }
        });
        viewHolder.tickCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.tickCb.isChecked()) {
                    mSelectedPosition = holder.getAdapterPosition();
                    mCallback.onItemSelect(mData[holder.getAdapterPosition()]);
                } else {
                    mSelectedPosition = -1;
                    mCallback.onItemUnSelect();
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.length;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        CheckBox tickCb;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            titleTxt = (TextView) itemLayoutView.findViewById(R.id.tv_job);
            tickCb = (CheckBox) itemLayoutView.findViewById(R.id.cb_job);
        }
    }

    public interface ExperienceCallable {
        void onItemSelect(String category);

        void onItemUnSelect();
    }
}
