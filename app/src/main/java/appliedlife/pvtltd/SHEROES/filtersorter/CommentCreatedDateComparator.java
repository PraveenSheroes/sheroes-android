package appliedlife.pvtltd.SHEROES.filtersorter;

import java.io.Serializable;
import java.util.Comparator;

import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

/**
 * Created by Praveen.Singh on 14/06/2016.
 *
 * @author Praveen Singh
 * @version 1.0
 * @since 14/06/2016.
 * Title: Bus search list sort by departure date.
 */
public class CommentCreatedDateComparator implements Comparator<CommentReactionDoc>, Serializable {
    private final String TAG = LogUtils.makeLogTag(CommentCreatedDateComparator.class);
    private int mDescendingOrderForDeparture = -1;

    @Override
    public int compare(CommentReactionDoc commentReactionDocFisrt, CommentReactionDoc commentReactionDocSecond) {
        int compareValue = 0;
        if (null != commentReactionDocFisrt.getCreatedOn() && null != commentReactionDocSecond.getCreatedOn()) {
            compareValue = commentReactionDocFisrt.getCreatedOn().compareTo(commentReactionDocSecond.getCreatedOn());

        }
        return compareValue;
    }
}