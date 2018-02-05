package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hendraanggrian.widget.SocialAdapter;

import org.jetbrains.annotations.NotNull;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.UserTaggingPerson;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import io.reactivex.annotations.NonNull;

/**
 * Created by Praveen on 01/02/18.
 */

public class SocialPersonAdapter extends SocialAdapter<UserTaggingPerson> {

    public SocialPersonAdapter(Context context) {
        super(context, R.layout.social_person, R.id.tv_name);
    }

    @NotNull
    @Override
    public String convertToString(UserTaggingPerson $receiver) {
        return $receiver.name;
    }

    @NonNull
    @Override
    public View getView(int position, @io.reactivex.annotations.Nullable View convertView, @NonNull ViewGroup parent) {
        SocialPersonAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.social_person, parent, false);
            holder = new SocialPersonAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (SocialPersonAdapter.ViewHolder) convertView.getTag();
        }
        final UserTaggingPerson item = getItem(position);
        if (item != null) {
            holder.textView.setText(item.name);
            holder.ivPicIcon.setCircularImage(true);
            holder.ivPicIcon.bindImage(item.authorImageUrl);
        }
        return convertView;
    }

    private static class ViewHolder {
        final TextView textView;
        final CircleImageView ivPicIcon;
        final LinearLayout liSocialUser;

        ViewHolder(@NonNull View view) {
            this.textView = view.findViewById(R.id.tv_name);
            this.ivPicIcon = view.findViewById(R.id.iv_user_pic);
            this.liSocialUser=view.findViewById(R.id.li_social_user);
        }
    }
}