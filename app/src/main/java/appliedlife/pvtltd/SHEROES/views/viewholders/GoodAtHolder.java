package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GoodAt;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sheroes on 26/03/17.
 */

public class GoodAtHolder extends BaseViewHolder<GoodAt> {
    private final String TAG = LogUtils.makeLogTag(OnBoardingHolder.class);
    BaseHolderInterface viewInterface;
    @Bind(R.id.li_good_at)
    LinearLayout liTags;
    @Bind(R.id.tv_good_at_header)
    TextView tvTagHeader;
    private GoodAt dataItem;
    private Context mContext;
    int mCurrentIndex = 0;
    TextView mTvTagData;
    int first,second,third,fourth;
    String tagText;
    private List<String> tagDataValue=new ArrayList<>();
    private List<Integer> tagDataPosition=new ArrayList<>();
    public GoodAtHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
    }



    @Override
    public void bindData(GoodAt obj, Context context, int position) {
        dataItem = obj;
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
                first=second=third=fourth=0;
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
            if(first==1&&second==1)
            {
                passedRow += 1;
                return passedRow;
            }else if(second==2)
            {
                passedRow += 1;
                return passedRow;
            }else if(second==1&&third==1)
            {
                passedRow += 1;
                return passedRow;
            }else if(fourth==1&&second==1)
            {
                passedRow += 1;
                return passedRow;
            }else if(fourth>=1&&lengthString>30)
            {
                passedRow += 1;
                return passedRow;
            }
            if (lengthString > 30) {
                if (column < 3) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    first++;
                    inflateTagData(liRow, stringList);
                    passedRow += 1;
                    mCurrentIndex++;
                }

            } else if (lengthString <= 30 && lengthString >15) {

                if (column < 2) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    second++;
                    inflateTagData(liRow, stringList);
                    mCurrentIndex++;
                    passedRow = cloumnViewTwo(liRow, passedRow, column - 1, stringList);
                }

            } else if (lengthString >= 10 && lengthString <=15) {

                if (column < 1) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    third++;
                    inflateTagData(liRow, stringList);
                    mCurrentIndex++;
                    passedRow = cloumnViewTwo(liRow, passedRow, column - 1, stringList);
                }

            } else if (lengthString >= 5 && lengthString < 10) {
                if (column<1) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    fourth++;
                    inflateTagData(liRow, stringList);
                    mCurrentIndex++;
                    passedRow = cloumnViewTwo(liRow, passedRow, column - 1, stringList);
                }
            }

        }
        return passedRow;
    }

    private void inflateTagData(LinearLayout liRow, List<String> stringList) {
        LayoutInflater columnInflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout liTagLable = (LinearLayout) columnInflate.inflate(R.layout.good_at_item, null);
        final TextView mTvTagData = (TextView) liTagLable.findViewById(R.id.tv_good_at_data);
        mTvTagData.setText(stringList.get(mCurrentIndex));
        tagDataValue.add(stringList.get(mCurrentIndex));
        tagDataPosition.add(mCurrentIndex);
        tagText=stringList.get(mCurrentIndex);
        // mTvTagData.mTvTag
        mTvTagData.setBackgroundResource(R.drawable.select_tag_shap);

        mTvTagData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvTagData.setBackgroundResource(R.drawable.selected_tag_shap);

                viewInterface.handleOnClick(dataItem,mTvTagData);


            }
        });
        liRow.addView(liTagLable);
    }

    @Override
    public void onClick(View v) {

    }


  /*  @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_tag_data:
                Toast.makeText(mContext,"Clicked->"+getAdapterPosition(),Toast.LENGTH_SHORT).show();
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
        }
    }*/

}
