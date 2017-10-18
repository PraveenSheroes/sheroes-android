package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.post.Album;

/**
 * Created by ujjwal on 17/10/17.
 */

public interface IAlbumView {
    void showAlbum(Album mAlbum);

    void showProgressBar();

    void hideProgressBar();

    void showSuccessMessage();

    void trackSaveImageEvent(String fullPath);
}
