package appliedlife.pvtltd.SHEROES.basecomponents;

import androidx.cardview.widget.CardView;
import android.view.View;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;


public interface BaseHolderInterface<T extends BaseResponse> {
    void handleOnClick(BaseResponse baseResponse, View view);

    void dataOperationOnClick(BaseResponse baseResponse);

    void setListData(T data, boolean flag);

    void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position);

    void navigateToProfileView(BaseResponse baseResponse, int mValue);

    void contestOnClick(Contest mContest, CardView mCardChallenge);
}
