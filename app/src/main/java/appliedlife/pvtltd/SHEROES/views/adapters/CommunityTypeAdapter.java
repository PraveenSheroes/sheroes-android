package appliedlife.pvtltd.SHEROES.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityType;

/**
 * Created by Ajit Kumar on 17-01-2017.
 */

public class CommunityTypeAdapter extends BaseAdapter {

    Context context;
    List<CommunityType> rowItem;
    String itemsnm;
    View listView;
    boolean checkState[];
    int pos=-1,flag=0;
String communityType;
    ViewHolder holder;
    TextView done;
    private CommunityTypeAdapterCallback callback;

    public CommunityTypeAdapter(Context context, List<CommunityType> rowItem, TextView done) {

        this.context = context;
        this.rowItem = rowItem;
        checkState = new boolean[rowItem.size()];
        this.done=done;

    }

    @Override
    public int getCount() {

        return rowItem.size();
    }

    @Override
    public Object getItem(int position) {

        return rowItem.get(position);

    }

    @Override
    public long getItemId(int position) {

        return rowItem.indexOf(getItem(position));

    }

    public class ViewHolder {
        TextView tvItemName;
        CheckBox check;
        TextView done;
    }

    @Override
    public View getView(final int position, final View view, ViewGroup parent) {

        holder = new ViewHolder();
        final CommunityType itm = rowItem.get(position);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        listView = new View(context);
        listView = layoutInflater.inflate(R.layout.community_type_list,
                parent, false);
        if (view == null) {

            if(position ==pos) {
                listView.setBackgroundColor((Color.parseColor("#f5f5f5")));
                // tvItemName.setTextColor((Color.parseColor("#3949ab")));

            }
            else {
                listView.setBackgroundColor(Color.WHITE);
               // holder.tvItemName.setTextColor((Color.parseColor("#000000")));


            }


            holder.tvItemName = (TextView) listView.findViewById(R.id.textView1);


            holder.check = (CheckBox) listView.findViewById(R.id.checkBox1);
            listView.setTag(holder);

        } else {
            if(position ==pos) {
                listView.setBackgroundColor((Color.parseColor("#f5f5f5")));
                // tvItemName.setTextColor((Color.parseColor("#3949ab")));

            }
            else {
                listView.setBackgroundColor(Color.WHITE);


            }
            listView = (View) view;
            holder = (ViewHolder) listView.getTag();

        }
        itemsnm=itm.getItems();
        holder.tvItemName.setText(itemsnm);
        final TextView finalTvItemName = holder.tvItemName;

        boolean isSelected = itm.getSelected();

        if(position ==pos) {
            listView.setBackgroundColor((Color.parseColor("#f5f5f5")));
           // tvItemName.setTextColor((Color.parseColor("#3949ab")));

        }
        else {
            listView.setBackgroundColor(Color.WHITE);
           // holder.tvItemName.setTextColor((Color.parseColor("#000000")));


        }
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // CommunityTypeFragment.doneClick();
                callback.communityType(communityType);
            }
        });
        holder.tvItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pos =position;
                for(int i=0;i<checkState.length;i++)
                {
                    if(i==position)
                    {

                        flag=1;
                        checkState[i]=true;

                    }
                    else
                    {
                        checkState[i]=false;
                        //v.setBackgroundColor(Color.WHITE);




                    }
                }
                notifyDataSetChanged();
            }
        });

        holder.check.setChecked(checkState[position]);
        holder.tvItemName.setSelected(checkState[position]);
        if(holder.tvItemName.isSelected())
        {
            communityType=rowItem.get(position).getItems().toString();
            holder.tvItemName.setTextColor((Color.parseColor("#3949ab")));

        }
        else
            holder.tvItemName.setTextColor((Color.parseColor("#000000")));

        if(holder.check.isChecked())
        {
            holder.check.setBackgroundResource(R.drawable.ic_chek_box);

        }
        else {
            holder.check.setBackgroundColor(Color.WHITE);

        }

        holder.check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                for(int i=0;i<checkState.length;i++)
                {
                    if(i==position)
                    {
                        checkState[i]=true;
                    }
                    else
                    {
                        checkState[i]=false;
                    }
                }
                notifyDataSetChanged();

            }
        });
        return listView;
    }
    public void setCallback(CommunityTypeAdapterCallback callback){

        this.callback = callback;
    }
    public interface CommunityTypeAdapterCallback {

        public void communityType(String communitytype);
    }
}