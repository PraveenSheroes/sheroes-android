package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.post.Photo;
import appliedlife.pvtltd.SHEROES.views.viewholders.PostPhotoViewHolder;


/**
 * Created by ujjwal on 06/10/16.
 */

public class PostPhotoAdapter extends RecyclerView.Adapter<PostPhotoViewHolder> {
    private final Context mContext;
    private List<Photo> mPhotoList = new ArrayList<>();
    private final View.OnClickListener mOnCloseClickListener;
    private int mSelectedPos = 0;

    //region Constructor
    public PostPhotoAdapter(Context context, View.OnClickListener onCloseClickListener) {
        this.mContext = context;
        this.mOnCloseClickListener = onCloseClickListener;
    }
    //endregion

    //region Post Photo methods
    @Override
    public PostPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_image_item, parent, false);
        return new PostPhotoViewHolder(view, mContext, mOnCloseClickListener);
    }

    @Override
    public void onBindViewHolder(PostPhotoViewHolder holder, int position) {
        Photo photo = mPhotoList.get(position);
        holder.itemView.setSelected(mSelectedPos == position);
        if (photo != null) {
            holder.bindData(photo);
        }
    }

    @Override
    public int getItemCount() {
        return mPhotoList == null ? 0 : mPhotoList.size();
    }

    public void setData(List<Photo> photos){
        this.mPhotoList = photos;
        notifyDataSetChanged();
    }

    public void addPhoto(Photo photo) {
        int size = mPhotoList.size();
        mPhotoList.add(photo);
        notifyItemInserted(size + 1);
    }

    public void removePhoto(int pos) {
        mPhotoList.remove(pos);
        notifyItemRemoved(pos);
    }

    // endregion
}
