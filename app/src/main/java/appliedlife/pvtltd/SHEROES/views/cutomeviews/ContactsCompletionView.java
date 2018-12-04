package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;

import com.tokenautocomplete.TokenCompleteTextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleTagName;

/**
 * Created by Praveen on 14/06/18.
 */

public class ContactsCompletionView extends TokenCompleteTextView<ArticleTagName> {

    InputConnection testAccessibleInputConnection;

    public ContactsCompletionView(Context context) {
        super(context);
    }

    public ContactsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactsCompletionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View getViewForObject(ArticleTagName articleTagName) {
        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TextView token = (TextView) l.inflate(R.layout.contact_token, (ViewGroup) getParent(), false);
        token.setText(articleTagName.getTagName());
        return token;
    }

    @Override
    protected ArticleTagName defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not
       /* int index = completionText.indexOf('@');
        ArticleTagName articleTagName = new ArticleTagName();
        if (index == -1) {
            articleTagName.setTagName(completionText);
            return articleTagName;
        } else {
            articleTagName.setTagName(completionText.substring(0, index));
            return articleTagName;
        }*/
       return null;
    }

    @Override
    public InputConnection onCreateInputConnection(@NonNull EditorInfo outAttrs) {
        testAccessibleInputConnection = super.onCreateInputConnection(outAttrs);
        return testAccessibleInputConnection;
    }
}
