package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import butterknife.ButterKnife;

/**
 * Created by Ujjwal on 10/10/2017.
 */
public class EmptyRecyclerView extends RecyclerView {
    private View emptyView;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {


        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    EmptyRecyclerView.this.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    EmptyRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }

        }
    };

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        emptyObserver.onChanged();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    /**
     * Sets Empty View with optional <code>emptyTextStringRes</code> and <code>emptySubTextStringRes</code> params
     * @param view View which is to be set as the empty view. Can contain optional <code>TextView</code>s with <code>R.id.empty_text</code> and <code>R.id.empty_subtext</code>
     * @param emptyTextStringRes Sets text on the <code>TextView</code> with id <code>R.id.empty_text</code> inside <code>view</code>. Pass <code>0</code> to ignore this param
     * @param emptySubTextStringRes Sets text on the <code>TextView</code> with id <code>R.id.empty_subtext</code> inside <code>view</code>. Pass <code>0</code> to ignore this param
     */
    public void setEmptyView(View view, @StringRes int emptyTextStringRes, @StringRes int emptySubTextStringRes) {
        setEmptyView(view);

        TextView emptyTextView = ButterKnife.findById(view, R.id.empty_text);
        if (emptyTextView != null) {
            emptyTextView.setText(emptyTextStringRes);
        }

        TextView emptySubTextView = ButterKnife.findById(view, R.id.empty_subtext);
        if (emptySubTextView != null) {
            emptySubTextView.setText(emptySubTextStringRes);
        }
    }

    public void setEmptyViewWithImage(View view, @StringRes int emptyTextStringRes, @DrawableRes int emptyDrawableRes, @StringRes int emptySubTextStringRes) {
        setEmptyView(view);

        TextView emptyTextView = ButterKnife.findById(view, R.id.empty_text);
        if (emptyTextView != null) {
            if (emptyTextStringRes != 0) {
                emptyTextView.setText(emptyTextStringRes);
            }

        }

        TextView emptySubTextView = ButterKnife.findById(view, R.id.empty_subtext);
        if (emptySubTextView != null) {
            emptySubTextView.setText(emptySubTextStringRes);
        }

        ImageView emptyImageView = ButterKnife.findById(view, R.id.empty_image);
        if(emptyImageView != null) {
            if (emptyDrawableRes != 0) {
                emptyImageView.setVisibility(VISIBLE);
                emptyImageView.setImageResource(emptyDrawableRes);
            } else {
                emptyImageView.setVisibility(GONE);
            }
        }
    }

    public void setEmptyViewWithImage(View view, String emptyTextString, @DrawableRes int emptyDrawableRes, String emptySubTextString) {
        setEmptyView(view);

        TextView emptyTextView = ButterKnife.findById(view, R.id.empty_text);
        if (emptyTextView != null) {
            if (CommonUtil.isNotEmpty(emptyTextString)) {
                emptyTextView.setText(emptyTextString);
            }

        }

        TextView emptySubTextView = ButterKnife.findById(view, R.id.empty_subtext);
        if (emptySubTextView != null) {
            emptySubTextView.setText(emptySubTextString);
        }

        ImageView emptyImageView = ButterKnife.findById(view, R.id.empty_image);
        if(emptyImageView != null) {
            if (emptyDrawableRes != 0) {
                emptyImageView.setVisibility(VISIBLE);
                emptyImageView.setImageResource(emptyDrawableRes);
            } else {
                emptyImageView.setVisibility(GONE);
            }
        }
    }
}