package appliedlife.pvtltd.SHEROES.views.fragments;

/**
 * Created by SHEROES-TECH on 01-03-2017.
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.util.Calendar;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

public class DatePickerExample extends DialogFragment {

    private static final int MAX_YEAR = 50;
    private OnDateSetListener listener;
    private final String mTAG = LogUtils.makeLogTag(DatePickerExample.class);

    public void setListener(OnDateSetListener listener) {
        this.listener = listener;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogPositivCustom);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.date_picker_dialog, null);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);



        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(0);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);
        setDividerColor(yearPicker);
        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDateSet(null, yearPicker.getValue(), yearPicker.getValue(), 0);
                    }
                });
        final AlertDialog dialog1= builder.create();
        dialog1.show();
        final Button positiveButton = dialog1.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);
        return dialog1;
    }
    private void setDividerColor (NumberPicker picker) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //pf.set(picker, getResources().getColor(R.color.my_orange));
                    //Log.v(TAG,"here");
                    pf.set(picker, getResources().getDrawable(R.drawable.blank_image));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        //}
    }

}
