package appliedlife.pvtltd.SHEROES.basecomponents;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.utils.LogUtils;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Base fragment for all child fragment.
 * all the common behaviour.
 */
public class BaseFragment extends Fragment implements View.OnClickListener {
    private final String TAG = LogUtils.makeLogTag(BaseFragment.class);
    public FragmentActivity mActivity;

    public static Bundle prepareBasicBundle(String headerText, long propertyId) {
        Bundle args = new Bundle();
        args.putString("top_line_text", headerText);
        return args;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    public void callFragment(int layout, Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void callFragmentWithBundle(int layout, Fragment fragment, Bundle args) {
        FragmentManager fragmentManager = getFragmentManager();
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void callFragmentWithString(int layout, Fragment fragment, String headerText,
                                       long propertyId) {
        callFragmentWithBundle(layout, fragment, prepareBasicBundle(headerText, propertyId));
    }


    public Boolean checkArguments(String string) {
        if (getArguments() != null && getArguments().containsKey(string)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public Integer unitPlusSetter(TextView textView, Integer unitNumber) {
        unitNumber += 1;
        setUnitCount(textView, unitNumber);
        return unitNumber;
    }

    public Integer unitMinusSetter(TextView textView, Integer unitNumber) {
        if (unitNumber > 1) {
            unitNumber -= 1;
            setUnitCount(textView, unitNumber);
        }
        return unitNumber;
    }

    public void setUnitCount(TextView textView, Integer unitNumber) {
        textView.setText(String.valueOf(unitNumber));
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            mActivity = getActivity();
        }
    }


}
