package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

/**
 * Created by priyanka on 25/03/17.
 */

public class DayPickerProfile extends DatePickerForProfile {

    private final String TAG = LogUtils.makeLogTag(DayPickerProfile.class);
    private static final int MAX_YEAR = 2099;
    private MyDayPickerListener listener;

    public void setListener(MyDayPickerListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogPositivCustom);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.education_date_picker, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_day);
        final NumberPicker dayPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        final TextView tvDone = (TextView) dialog.findViewById(R.id.tv_done);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setDisplayedValues(new String[]{"JAN", "FAB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"});

        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(30);

        yearPicker.setMinValue(1960);
        yearPicker.setMaxValue(2050);

        setDividerColor(monthPicker);
        setDividerColor(dayPicker);
        setDividerColor(yearPicker);
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  listener.onDateSet(monthPicker.getValue());
                getDialog().cancel();
            }
        });

        builder.setView(dialog);
        final AlertDialog dialog1 = builder.create();
        dialog1.show();
       /* final Button positiveButton = dialog1.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);

        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);*/
        return dialog1;
    }

    private void setDividerColor(NumberPicker picker) {

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
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        //}
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.getWindow().setLayout(700, ViewGroup.LayoutParams.WRAP_CONTENT);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        }
    }

    public interface MyDayPickerListener {
        void onErrorOccurence();

        void onDaySubmit(int tagsval);
    }
}
