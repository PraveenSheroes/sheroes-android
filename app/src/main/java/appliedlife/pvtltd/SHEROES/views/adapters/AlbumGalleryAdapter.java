package appliedlife.pvtltd.SHEROES.views.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.post.Photo;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.FileFragment;

/**
 * Created by ujjwal on 31/03/17.
 */

public class AlbumGalleryAdapter extends FragmentStatePagerAdapter {
    private final List<Photo> mPhoto;
    public CommonUtil.Callback mCallBack;

    public AlbumGalleryAdapter(FragmentActivity activity,
                               List<Photo> photos, CommonUtil.Callback callback) {
        super(activity.getSupportFragmentManager());
        this.mPhoto = photos;
        this.mCallBack = callback;
    }

    @Override
    public int getCount() {
        return mPhoto.size();
    }

    @Override
    public Fragment getItem(int position) {
        FileFragment fileFragment = FileFragment.newInstance(mPhoto.get(position).url, null);
        fileFragment.mCallBack = mCallBack;
        return fileFragment;
    }

}
