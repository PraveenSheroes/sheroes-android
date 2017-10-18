package appliedlife.pvtltd.SHEROES.imageops;

import com.squareup.pollexor.Thumbor;

import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;

/**
 * Created by ujjwal on 06/09/16.
 */
public class SheroesThumbor {
    private static Thumbor thumbor;

    public static Thumbor getInstance() {
        if (thumbor == null) {
            String thumborUrl = (Config.getConfig() != null && CommonUtil.isNotEmpty(Config.getConfig().thumborUrl)) ? Config.getConfig().thumborUrl : Config.THUMBOR_URL;
            String thumborKey = (Config.getConfig() != null && CommonUtil.isNotEmpty(Config.getConfig().thumborKey)) ? Config.getConfig().thumborKey : Config.THUMBOR_KEY;
            thumbor = Thumbor.create(thumborUrl, thumborKey);
        }
        return thumbor;
    }
}
