package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleDetailPojo;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 09-02-2017.
 */

public class ArticleDetailSuggestedHolder extends BaseViewHolder<ArticleDetailPojo> {
    private final String TAG = LogUtils.makeLogTag(ArticleDetailSuggestedHolder.class);
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    @Bind(R.id.rv_article_detail_suggested_list)
    RecyclerView mRecyclerView;
    BaseHolderInterface viewInterface;
    private ArticleDetailPojo dataItem;


    public ArticleDetailSuggestedHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ArticleDetailPojo item, final Context context, int position) {
        this.dataItem = item;
      /*  List<ArticleDetailSuggestion> suggestedPost=new ArrayList<>();
        ArticleDetailSuggestion articleDetailSuggestion=new ArticleDetailSuggestion();
        articleDetailSuggestion.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14846520381484652038.png");
        articleDetailSuggestion.setPostName("First name");
        ArticleDetailSuggestion communitySuggestion2=new ArticleDetailSuggestion();
        communitySuggestion2.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14845475641484547564.png");
        communitySuggestion2.setPostName("Second name");
        ArticleDetailSuggestion communitySuggestion3=new ArticleDetailSuggestion();
        communitySuggestion3.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14847278181484727818.png");
        communitySuggestion3.setPostName("Third name");
        ArticleDetailSuggestion communitySuggestion4=new ArticleDetailSuggestion();
        communitySuggestion4.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14842893151484289315.png");
        suggestedPost.add(articleDetailSuggestion);
        suggestedPost.add(communitySuggestion2);
        suggestedPost.add(communitySuggestion3);
        suggestedPost.add(communitySuggestion4);*/

        mLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(context,(ArticleDetailActivity) context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
   //     mAdapter.setSheroesGenericListData(suggestedPost);
    }


    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            //case R.id.tv_community_join:
            //      break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}
