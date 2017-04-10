package appliedlife.pvtltd.SHEROES.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;

/**
 * Created by priyanka on 23/03/17.
 */

public class ProfileSpinnerAdapter extends ArrayAdapter<String> {

    String[] item_text;
    int[] item_icon;


    public ProfileSpinnerAdapter(Context ctx, int txtViewResourceId, String[] objects) {
        super(ctx, txtViewResourceId, objects);
        item_text=objects;
        this.item_icon=item_icon;


    }
    @Override public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt); }
    @Override public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return getCustomView(pos, cnvtView, prnt);
    }
    public View getCustomView(int position, View convertView, ViewGroup parent) {



        LayoutInflater layoutInflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View mySpinner = layoutInflater.inflate(R.layout.profile_current_status_spinner, parent, false);
        TextView main_text = (TextView) mySpinner.findViewById(R.id.text_set);

        main_text.setText(item_text[position]);


        return mySpinner;

    }
}