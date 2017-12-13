package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;


import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Address;
import appliedlife.pvtltd.SHEROES.presenters.AddressPresenterImpl;
import appliedlife.pvtltd.SHEROES.presenters.ArticlePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IAddressView;
import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 03/05/17.
 */

public class AddressActivity extends BaseActivity implements IAddressView {
    private static final String SCREEN_LABEL = "Address Activity";
    private static final String IS_ADDRESS_UPDATED = "Is Address Updated";


    @Inject
    AddressPresenterImpl mAddressPresenter;

    //region View variables
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.name)
    EditText mNameView;

    @Bind(R.id.phone)
    EditText mPhoneNumberView;

    @Bind(R.id.address)
    EditText mAddressView;

    @Bind(R.id.email_id)
    AutoCompleteTextView mEmailView;

    @Bind(R.id.pin_code)
    EditText mPinCodeView;

    @BindString(R.string.error_name_blank)
    String mErrorNameString;

    @BindString(R.string.error_email_blank)
    String mErrorEmailString;

    @BindString(R.string.error_invalid_mobile)
    String mInvalidMobileString;

    @BindString(R.string.error_address_blank)
    String mInvalidAddressString;

    @BindString(R.string.error_invalid_pin_code)
    String mInvalidPinCodeString;
    //endregion

    private Address originalAddress;
    private boolean wasAddressUpdated;

    //region activity lifecycle methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        mAddressPresenter.attachView(this);
        wasAddressUpdated = getIntent().getBooleanExtra(IS_ADDRESS_UPDATED, false);
        Parcelable parcelable = getIntent().getParcelableExtra(Address.ADDRESS_OBJ);
        if (parcelable != null) {
            originalAddress = Parcels.unwrap(parcelable);
            if (originalAddress != null && wasAddressUpdated) {
                prePopulateFields();
            }
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.vector_clear);
        getSupportActionBar().setTitle(R.string.title_send_address);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_send_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPress();
            return true;
        }

        if (id == R.id.post) {
            if (validateFields()) {
                Address address = new Address();
                getAddressFromField(address);
                address.challengeId = originalAddress.challengeId;
                mAddressPresenter.postAddress(address);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    //endregion

    //region private methods
    private void prePopulateFields() {
        mNameView.setText(originalAddress.fullName);
        mPhoneNumberView.setText(originalAddress.mobileNumber);
        mAddressView.setText(originalAddress.fullAddress);
        mEmailView.setText(originalAddress.emailAddress);
        mPinCodeView.setText(originalAddress.pinCode);
        mNameView.setSelection(mNameView.length());
    }

    private boolean validateFields() {
        String phoneNumber = mPhoneNumberView.getText().toString().trim();
        String name = mNameView.getText().toString().trim();
        String email = mEmailView.getText().toString().trim();
        String address = mAddressView.getText().toString().trim();
        String pinCode = mPinCodeView.getText().toString().trim();

        boolean phoneNumberValidation = (TextUtils.isDigitsOnly(phoneNumber)) && (phoneNumber.length() == 10 && phoneNumber.matches("^[^0][0-9]{9}$"));

        boolean nameValidation = !TextUtils.isEmpty(name);

        boolean emailValidation = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();

        boolean addressValidation = !TextUtils.isEmpty(address);

        boolean pinCodeValidation = (TextUtils.isDigitsOnly(pinCode));

        if (!nameValidation) {
            mNameView.requestFocus();
            mNameView.setError(mErrorNameString);
        } else {
            mNameView.setError(null);
        }

        if (!phoneNumberValidation) {
            mPhoneNumberView.requestFocus();
            mPhoneNumberView.setError(mInvalidMobileString);
        } else {
            mPhoneNumberView.setError(null);
        }

        if (!emailValidation) {
            mEmailView.requestFocus();
            mEmailView.setError(mErrorEmailString);
        } else {
            mEmailView.setError(null);
        }

        if (!addressValidation) {
            mAddressView.requestFocus();
            mAddressView.setError(mInvalidAddressString);
        } else {
            mAddressView.setError(null);
        }

        if (!pinCodeValidation) {
            mPinCodeView.requestFocus();
            mPinCodeView.setError(mInvalidPinCodeString);
        } else {
            mPhoneNumberView.setError(null);
        }

        return nameValidation && phoneNumberValidation && emailValidation && addressValidation && pinCodeValidation;
    }

    private void onBackPress() {
        if (isDirty()) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(AddressActivity.this);

            builder.setTitle("Discard Address?");
            builder.setMessage("Are you sure you want to discard your changes?");
            builder.setNegativeButton("NO", null);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    CommonUtil.hideKeyboard(AddressActivity.this);
                    AddressActivity.this.finish();
                }
            });

            builder.create();
            builder.show();
        } else {
            CommonUtil.hideKeyboard(AddressActivity.this);
            onBackPressed();
        }

    }

    private boolean isDirty() {
        if (originalAddress != null) {
            Address address = new Address();
            getAddressFromField(address);
            if (!address.fullName.equals(originalAddress.fullName)
                    || !address.fullAddress.equals(originalAddress.fullAddress)
                    || !address.pinCode.equals(originalAddress.pinCode)
                    || !address.emailAddress.equals(originalAddress.emailAddress)
                    || !address.mobileNumber.equals(originalAddress.mobileNumber)) {
                return true;
            }else {
                return false;
            }
        }
        if (CommonUtil.isNotEmpty(mNameView.getText().toString())
                || CommonUtil.isNotEmpty(mPhoneNumberView.getText().toString())
                || CommonUtil.isNotEmpty(mPinCodeView.getText().toString())
                || CommonUtil.isNotEmpty(mEmailView.getText().toString())
                || CommonUtil.isNotEmpty(mAddressView.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }


    private void getAddressFromField(Address address) {
        address.mobileNumber = mPhoneNumberView.getText().toString().trim();
        address.fullName = mNameView.getText().toString().trim();
        address.emailAddress = mEmailView.getText().toString().trim();
        address.fullAddress = mAddressView.getText().toString().trim();
        address.pinCode = mPinCodeView.getText().toString().trim();
    }

    //endregion

    //region static methods
    public static void navigateTo(Activity fromActivity, String sourceScreen, Address address, int requestCode, boolean isAddressUpdated, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, AddressActivity.class);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        Parcelable parcelable = Parcels.wrap(address);
        intent.putExtra(Address.ADDRESS_OBJ, parcelable);
        intent.putExtra(IS_ADDRESS_UPDATED, isAddressUpdated);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
        ActivityCompat.startActivity(fromActivity, intent, null);
    }

    @Override
    public void finishActivity() {
        CommonUtil.hideKeyboard(AddressActivity.this);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }
    //endregion
}