package appliedlife.pvtltd.SHEROES.utils;

import com.squareup.pollexor.Thumbor;

/**
 * Created by ujjwal on 20/03/18.
 */
public class SheroesThumbor {
    private static Thumbor thumbor;

    public static Thumbor getInstance() {
        if (thumbor == null) {
            String thumborUrl = "https://t.sheroes.in";
            if (CommonUtil.isNotEmpty(CommonUtil.getPref(AppConstants.THUMBOR_KEY))) {
                thumbor = Thumbor.create(thumborUrl, CommonUtil.getPref(AppConstants.THUMBOR_KEY));
            } else {
                thumbor = Thumbor.create(thumborUrl);
            }
        }
        return thumbor;
    }
}
