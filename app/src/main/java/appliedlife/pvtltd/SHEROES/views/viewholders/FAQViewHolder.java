package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQS;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHEROES 005 on 30-May-17.
 */

public class FAQViewHolder extends BaseViewHolder<FAQS> {

    private FAQS faqs;

    BaseHolderInterface baseHolderInterface;

    @Bind(R.id.tv_faqs_question)
    TextView tvFAQSQuestion;

    @Bind(R.id.tv_faqs_answer)
    TextView tvFAQSAnswer;

    @Bind(R.id.id_faq_answer)
    LinearLayout mFaqAnswer;

    @Bind(R.id.tab_faq_question)
    LinearLayout liFaqQuestion;

    @Bind(R.id.id_spinner)
    ImageView mSpinner;

    public FAQViewHolder(View view, BaseHolderInterface viewInterface){
        super(view);
        ButterKnife.bind(this,  view);
        this.baseHolderInterface = viewInterface;
        SheroesApplication.getAppComponent(view.getContext()).inject(this);
    }

    @Override
    public void bindData(final FAQS faqs, final Context context, final int position) {
        this.faqs = faqs;
        faqs.setItemPosition(position);
        if(faqs.getAnswer()!=null){
            tvFAQSAnswer.setText(faqs.getAnswer());
        }
        if(faqs.getQuestion()!=null){
            tvFAQSQuestion.setText(faqs.getQuestion());
        }

        if(faqs.getItemSelected()){
            expand();
            faqs.setItemSelected(false);
        }else{
            collapse();
        }

        liFaqQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFaqAnswer.isShown()){
                    collapse();
                }else{
                    faqs.setItemSelected(true);
                    baseHolderInterface.handleOnClick(faqs, liFaqQuestion);
                }

            }
        });
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }

    private void expand() {
        mFaqAnswer.setVisibility(View.VISIBLE);
        Matrix matrix = new Matrix();
        mSpinner.setScaleType(ImageView.ScaleType.MATRIX);
        matrix.postRotate(180f, mSpinner.getDrawable().getBounds().width()/2, mSpinner.getDrawable().getBounds().height()/2);
        mSpinner.setImageMatrix(matrix);
    }

    private void collapse() {
        mFaqAnswer.setVisibility(View.GONE);
        Matrix matrix = new Matrix();
        mSpinner.setScaleType(ImageView.ScaleType.MATRIX);
        matrix.postRotate(0f, mSpinner.getDrawable().getBounds().width()/2, mSpinner.getDrawable().getBounds().height()/2);
        mSpinner.setImageMatrix(matrix);
    }
}
