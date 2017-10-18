package appliedlife.pvtltd.SHEROES.presenters;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IAlbumView;

/**
 * Created by ujjwal on 17/10/17.
 */

public class AlbumPresenter {
    private IAlbumView mAlbumView;


    @Inject
    public AlbumPresenter() {

    }

    public void setView(IAlbumView view) {
        this.mAlbumView = view;
    }
}
