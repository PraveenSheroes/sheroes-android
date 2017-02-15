package appliedlife.pvtltd.SHEROES.basecomponents;

import android.content.Intent;
import android.view.View;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;


public interface BaseHolderInterface<T extends BaseResponse> {
    void startActivityFromHolder(Intent intent);
    void handleOnClick(BaseResponse baseResponse, View view);
    void dataOperationOnClick(BaseResponse baseResponse);
    void setListData(T data,boolean flag);
    void userCommentLikeRequest(long entityId,int reactionValue);

    List<T> getListData();
}
