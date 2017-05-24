package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Calendar;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

public class DatePickerExample extends DialogFragment {
    private final String TAG = LogUtils.makeLogTag(DatePickerExample.class);
    private static final int MAX_YEAR = 50;
    private OnDateSetListener listener;
    private int yearMonthCall;
    private NumberPicker yearPicker;
    private TextView tvMontnYear;
    private Calendar cal;

    public void setListener(OnDateSetListener listener, int callFor) {
        yearMonthCall = callFor;
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
        cal = Calendar.getInstance();
        View dialog = inflater.inflate(R.layout.date_picker_dialog, null);
        yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);
        tvMontnYear = (TextView) dialog.findViewById(R.id.tv_year_month);
        setDateAccordingToInput(yearMonthCall);
        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDateSet(null, yearPicker.getValue(), yearPicker.getValue(), yearMonthCall);
                    }
                });
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        final AlertDialog dialog1 = builder.create();
        dialog1.show();
        final Button positiveButton = dialog1.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);
        return dialog1;
    }

    private void initlizeYear() {
        tvMontnYear.setText(getString(R.string.ID_YEARS));
        int year = cal.get(Calendar.YEAR);
       /* yearPicker.setMinValue(0);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);*/
        setDividerColor(yearPicker);
        yearPicker.setMaxValue(year+50);
        yearPicker.setMinValue(year-50);
        yearPicker.setWrapSelectorWheel(false);
        yearPicker.setValue(year);
    }

    private void initlizeMonth() {
        tvMontnYear.setText(getString(R.string.ID_MONTHS));
        yearPicker.setMinValue(1);
        yearPicker.setMaxValue(12);
        yearPicker.setValue(cal.get(Calendar.MONTH) + 1);
        setDividerColor(yearPicker);
    }

    private void initailizeDay() {
        tvMontnYear.setText(getString(R.string.ID_DAY));
        yearPicker.setMinValue(1);
        yearPicker.setMaxValue(31);
        yearPicker.setValue(cal.get(Calendar.DAY_OF_MONTH) + 1);
        setDividerColor(yearPicker);
    }

    private void setDateAccordingToInput(int callForDate) {
        switch (callForDate) {
            case AppConstants.ONE_CONSTANT:
                initailizeDay();
                break;
            case AppConstants.TWO_CONSTANT:
                initlizeMonth();
                break;
            case AppConstants.THREE_CONSTANT:
                initlizeYear();
                break;
            case AppConstants.FOURTH_CONSTANT:
                initailizeDay();
                break;
            case 5:
                initlizeMonth();
                break;
            case 6:
                initlizeYear();
                break;

        }

    }

    private void setDividerColor(NumberPicker picker) {

        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //pf.set(picker, getResources().getColor(R.color.my_orange));
                    //Log.v(TAG,"here");
                    pf.set(picker, getResources().getDrawable(R.drawable.ic_reaction_strip_background));
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

            dialog.getWindow().setLayout(450, 450);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        }
    }

}
