package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHEROES-TECH on 22-02-2017.
 */

public class OnBoardingHolder extends BaseViewHolder<OnBoardingData> {
    private final String TAG = LogUtils.makeLogTag(OnBoardingHolder.class);
    BaseHolderInterface viewInterface;
    @Bind(R.id.li_tags)
    LinearLayout liTags;
    @Bind(R.id.tv_tag_header)
    TextView tvTagHeader;
    private OnBoardingData dataItem;
    private Context mContext;
    int mCurrentIndex = 0;
    TextView mTvTagData;
    public OnBoardingHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
    }

    @Override
    public void bindData(OnBoardingData onBoardingData, Context context, int position) {
        dataItem = onBoardingData;
        mContext = context;
        renderOnBoardingView();
    }

    @Override
    public void viewRecycled() {

    }

    public void renderOnBoardingView() {
        int mSeatHeight = 100;//(int) mContext.getResources().getDimension(R.dimen.dp_size_48);
        int mSeatWidth = (int) mContext.getResources().getDimension(R.dimen.dp_size_48);
        if (StringUtil.isNotEmptyCollection(dataItem.getBoardingDataList()) && StringUtil.isNotNullOrEmptyString(dataItem.getName())) {
            tvTagHeader.setText(dataItem.getName());
            int row = 0;
            for (int index = 0; index <= row; index++) {
                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout liRow = (LinearLayout) layoutInflater.inflate(R.layout.tags_onboarding_ui_layout, null);
                int column = 3;
                row = cloumnViewTwo(liRow, row, column, dataItem.getBoardingDataList());
                liTags.addView(liRow);
            }
        }
    }

    private int cloumnViewTwo(LinearLayout liRow, int passedRow, int column, List<String> stringList) {

        if (mCurrentIndex < stringList.size()) {
            int lengthString = stringList.get(mCurrentIndex).length();
            if (lengthString > 30) {
                if (column < 3) {
                    passedRow += 1;
                    return passedRow;
                } else {
                   /* LayoutInflater columnInflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    LinearLayout liTagLable = (LinearLayout) columnInflate.inflate(R.layout.tag_item_ui_for_onboarding, null);
                    TextView tvTagData = (TextView) liTagLable.findViewById(R.id.tv_tag_data);
                    tvTagData.setText(stringList.get(mCurrentIndex));
                    liRow.addView(liTagLable);*/
                    inflateTagData(liRow, stringList);
                    passedRow += 1;
                    mCurrentIndex++;
                }
            } else if (lengthString <= 30 && lengthString >=20) {
                if (column < 2) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    inflateTagData(liRow, stringList);
                    mCurrentIndex++;
                    passedRow = cloumnViewTwo(liRow, passedRow, column - 1, stringList);
                }
            } else if (lengthString >= 10 && lengthString <20) {
                if (column < 1) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    inflateTagData(liRow, stringList);
                    mCurrentIndex++;
                    passedRow = cloumnViewTwo(liRow, passedRow, column - 1, stringList);
                }
            } else if (lengthString <= 5 && lengthString < 10) {
                inflateTagData(liRow, stringList);
                passedRow += 1;
                mCurrentIndex++;
            }

        }
        return passedRow;
    }

    private void inflateTagData(LinearLayout liRow, List<String> stringList) {
        LayoutInflater columnInflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout liTagLable = (LinearLayout) columnInflate.inflate(R.layout.tag_item_ui_for_onboarding, null);
        mTvTagData = (TextView) liTagLable.findViewById(R.id.tv_tag_data);
        mTvTagData.setText(stringList.get(mCurrentIndex));
        mTvTagData.setOnClickListener(this);
        liRow.addView(liTagLable);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_tag_data:
                Toast.makeText(mContext,"Clicked->"+getAdapterPosition(),Toast.LENGTH_SHORT).show();
                    viewInterface.handleOnClick(dataItem, mTvTagData);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
        }
    }

}
