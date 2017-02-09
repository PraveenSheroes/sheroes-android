package appliedlife.pvtltd.SHEROES.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityType;

/**
 * Created by Ajit Kumar on 17-01-2017.
 */

public class CommunityTypeAdapter extends BaseAdapter {

    Context mContext;
    List<CommunityType> mRowItem;
    String mItemsnm;
    View mListView;
    boolean mCheckState[];
    int mPos=-1,mFlag=0;
String mCommunityType;
    ViewHolder mHolder;
    TextView mDone;
    private CommunityTypeAdapterCallback mCallback;

    public CommunityTypeAdapter(Context mContext, List<CommunityType> mRowItem, TextView done) {

        this.mContext = mContext;
        this.mRowItem = mRowItem;
        mCheckState = new boolean[mRowItem.size()];
        this.mDone=done;

    }

    @Override
    public int getCount() {

        return mRowItem.size();
    }

    @Override
    public Object getItem(int position) {

        return mRowItem.get(position);

    }

    @Override
    public long getItemId(int position) {

        return mRowItem.indexOf(getItem(position));

    }

    public class ViewHolder {
        TextView tvItemName;
        CheckBox check;
        TextView done;
        TextView customer_review;
        TextView associate_employee_engagement;
        TextView product_promotion;
        LinearLayout lnr1;
    }

    @Override
    public View getView(final int position, final View view, ViewGroup parent) {
        final TextView tv_emloyee_branding,tv_customer_review,tv_associate_emloyee_engagement,tv_product_promotion;

        mHolder = new ViewHolder();
        final CommunityType itm = mRowItem.get(position);
        LayoutInflater layoutInflater = (LayoutInflater) mContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        mListView = new View(mContext);
        mListView = layoutInflater.inflate(R.layout.community_type_list,
                parent, false);
        if (view == null) {

            if(position ==mPos) {
                mListView.setBackgroundColor((Color.parseColor("#f5f5f5")));
                // tvItemName.setTextColor((Color.parseColor("#3949ab")));

            }
            else {
                mListView.setBackgroundColor(Color.WHITE);
               // holder.tvItemName.setTextColor((Color.parseColor("#000000")));


            }


            mHolder.tvItemName = (TextView) mListView.findViewById(R.id.textView1);

            mHolder.lnr1=(LinearLayout) mListView.findViewById(R.id.lnr1);
            tv_customer_review=(TextView) mHolder.lnr1.findViewById(R.id.tv_customer_review);
            tv_associate_emloyee_engagement=(TextView) mHolder.lnr1.findViewById(R.id.tv_associate_emloyee_engagement);
            tv_product_promotion=(TextView) mHolder.lnr1.findViewById(R.id.tv_product_promotion);
            tv_emloyee_branding=(TextView) mHolder.lnr1.findViewById(R.id.tv_employeer_branding);
            tv_emloyee_branding.setBackgroundResource(R.drawable.select_purpose_btn_shap);

            tv_emloyee_branding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_emloyee_branding.setBackgroundResource(R.drawable.invite_color_btn_shap);
                    tv_emloyee_branding.setTextColor((Color.parseColor("#FFFFFF")));
                }
            });
            tv_customer_review.setBackgroundResource(R.drawable.select_purpose_btn_shap);

            tv_customer_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_customer_review.setBackgroundResource(R.drawable.invite_color_btn_shap);
                    tv_customer_review.setTextColor((Color.parseColor("#FFFFFF")));
                }
            });
            tv_associate_emloyee_engagement.setBackgroundResource(R.drawable.select_purpose_btn_shap);

            tv_associate_emloyee_engagement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_associate_emloyee_engagement.setBackgroundResource(R.drawable.invite_color_btn_shap);
                    tv_associate_emloyee_engagement.setTextColor((Color.parseColor("#FFFFFF")));
                }
            });
            tv_product_promotion.setBackgroundResource(R.drawable.select_purpose_btn_shap);

            tv_product_promotion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_product_promotion.setBackgroundResource(R.drawable.invite_color_btn_shap);
                    tv_product_promotion.setTextColor((Color.parseColor("#FFFFFF")));
                }
            });
            mHolder.check = (CheckBox) mListView.findViewById(R.id.checkBox1);
            mListView.setTag(mHolder);

        } else {
            if(position ==mPos) {
                mListView.setBackgroundColor((Color.parseColor("#f5f5f5")));
                // tvItemName.setTextColor((Color.parseColor("#3949ab")));

            }
            else {
                mListView.setBackgroundColor(Color.WHITE);


            }
            mListView = (View) view;
            mHolder = (ViewHolder) mListView.getTag();

        }
        mItemsnm=itm.getItems();
        mHolder.tvItemName.setText(mItemsnm);
        final TextView finalTvItemName = mHolder.tvItemName;

        boolean isSelected = itm.getSelected();

        if(position ==mPos) {
            mListView.setBackgroundColor((Color.parseColor("#f5f5f5")));
           // tvItemName.setTextColor((Color.parseColor("#3949ab")));

        }
        else {
            mListView.setBackgroundColor(Color.WHITE);
           // holder.tvItemName.setTextColor((Color.parseColor("#000000")));


        }
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // CommunityTypeFragment.doneClick();
                mCallback.communityType(mCommunityType);
            }
        });
        mHolder.tvItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPos =position;
                for(int i=0;i<mCheckState.length;i++)
                {
                    if(i==position)
                    {

                        mFlag=1;
                        mCheckState[i]=true;

                    }
                    else
                    {
                        mCheckState[i]=false;
                        //v.setBackgroundColor(Color.WHITE);




                    }
                }
                notifyDataSetChanged();
            }
        });

        mHolder.check.setChecked(mCheckState[position]);
        mHolder.tvItemName.setSelected(mCheckState[position]);
        if(mHolder.tvItemName.isSelected())
        {
            mCommunityType=mRowItem.get(position).getItems().toString();
            mHolder.tvItemName.setTextColor((Color.parseColor("#3949ab")));

        }
        else
            mHolder.tvItemName.setTextColor((Color.parseColor("#000000")));

        if(mHolder.check.isChecked())
        {

            mHolder.check.setBackgroundResource(R.drawable.ic_chek_box);
            if(mHolder.tvItemName.getText().toString().equals("Company/Brand"))
                mHolder.lnr1.setVisibility(View.VISIBLE);

        }
        else {
            mHolder.check.setBackgroundColor(Color.WHITE);
            mHolder.lnr1.setVisibility(View.GONE);

        }

        mHolder.check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                for(int i=0;i<mCheckState.length;i++)
                {
                    if(i==position)
                    {
                        mCheckState[i]=true;
                    }
                    else
                    {
                        mCheckState[i]=false;
                    }
                }
                notifyDataSetChanged();

            }
        });
        return mListView;
    }
    public void setCallback(CommunityTypeAdapterCallback callback){

        this.mCallback = callback;
    }
    public interface CommunityTypeAdapterCallback {

        public void communityType(String communitytype);
    }
}