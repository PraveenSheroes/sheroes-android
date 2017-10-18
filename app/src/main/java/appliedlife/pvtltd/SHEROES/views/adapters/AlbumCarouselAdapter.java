package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.post.Photo;
import appliedlife.pvtltd.SHEROES.views.viewholders.PhotoViewHolder;


/**
 * Created by ujjwal on 06/10/16.
 */

public class AlbumCarouselAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
    private final Context mContext;
    private List<Photo> photos;
    private final View.OnClickListener mOnClickListener;
    private int mSelectedPos = 0;

    //region Constructor
    public AlbumCarouselAdapter(Context context, List<Photo> photos, View.OnClickListener onClickListener) {
        this.mContext = context;
        this.photos = photos;
        this.mOnClickListener = onClickListener;
    }
    //endregion

    //region AlbumCarousel methods
    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_carosel_item, parent, false);
        return new PhotoViewHolder(view, mContext, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.itemView.setSelected(mSelectedPos == position);
        if (photo != null) {
            holder.bindData(photo);
        }
    }

    @Override
    public int getItemCount() {
        return photos == null ? 0 : photos.size();
    }

    public void setSelectedPosition(int position){
        if(mSelectedPos == position){
            return;
        }
        int oldPosition = mSelectedPos;
        mSelectedPos = position;
        notifyItemChanged(oldPosition);
        notifyItemChanged(position);
    }
    // endregion
}
