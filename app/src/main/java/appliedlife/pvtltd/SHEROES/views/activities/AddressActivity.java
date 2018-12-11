package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import org.parceler.Parcels;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.post.Address;
import appliedlife.pvtltd.SHEROES.presenters.AddressPresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IAddressView;
import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 03/05/17.
 */

public class AddressActivity extends BaseActivity implements IAddressView {

    //region constants
    private static final String SCREEN_LABEL = "Address Activity";
    private static final String IS_ADDRESS_UPDATED = "Is Address Updated";
    //endregion contstants

    //region injected variables
    @Inject
    AddressPresenterImpl mAddressPresenter;
    //endregion injected variables

    //region View variables
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.name_container)
    TextInputLayout mNameViewContainer;

    @Bind(R.id.phone_container)
    TextInputLayout mPhoneNumberViewContainer;

    @Bind(R.id.address_container)
    TextInputLayout mAddressViewContainer;

    @Bind(R.id.pin_code_container)
    TextInputLayout mPinCodeViewContainer;

    @Bind(R.id.email_id_container)
    TextInputLayout mEmailViewContainer;

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

    //region member variable
    private Address mOriginalAddress;
    //endregion member variable

    //region activity lifecycle methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        mAddressPresenter.attachView(this);
        boolean wasAddressUpdated = getIntent().getBooleanExtra(IS_ADDRESS_UPDATED, false);
        Parcelable parcelable = getIntent().getParcelableExtra(Address.ADDRESS_OBJ);
        if (parcelable != null) {
            mOriginalAddress = Parcels.unwrap(parcelable);
            if (mOriginalAddress != null && wasAddressUpdated) {
                prePopulateFields();
            }
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.vector_clear);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_send_address);
        }
    }
    //endregion activity lifecycle methods

    //region override method
    @Override
    protected SheroesPresenter getPresenter() {
        return mAddressPresenter;
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
                address.challengeId = mOriginalAddress.challengeId;
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
    //endregion override method

    //region private methods
    private void prePopulateFields() {
        mNameView.setText(mOriginalAddress.fullName);
        mPhoneNumberView.setText(mOriginalAddress.mobileNumber);
        mAddressView.setText(mOriginalAddress.fullAddress);
        mEmailView.setText(mOriginalAddress.emailAddress);
        mPinCodeView.setText(mOriginalAddress.pinCode);
        mNameView.setSelection(mNameView.length());
    }

    private boolean validateFields() {
        String phoneNumber = mPhoneNumberView.getText().toString().trim();
        String name = mNameView.getText().toString().trim();
        String email = mEmailView.getText().toString().trim();
        String address = mAddressView.getText().toString().trim();
        String pinCode = mPinCodeView.getText().toString().trim();

        boolean nameValidation = !TextUtils.isEmpty(name);

        boolean emailValidation = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();

        boolean addressValidation = !TextUtils.isEmpty(address);

        boolean pinCodeValidation = CommonUtil.isNotEmpty(pinCode) && TextUtils.isDigitsOnly(pinCode);

        boolean phoneNumberValidation = (CommonUtil.isNotEmpty(phoneNumber) && phoneNumber.length() == 10 && phoneNumber.matches("^[^0][0-9]{9}$"));

        if (!nameValidation) {
            mNameViewContainer.setErrorEnabled(true);
            mNameViewContainer.requestFocus();
            mNameViewContainer.setError(mErrorNameString);
        } else {
            mNameViewContainer.setErrorEnabled(false);
            mNameViewContainer.setError(null);
        }

        if (!phoneNumberValidation) {
            mPhoneNumberViewContainer.setErrorEnabled(true);
            mPhoneNumberViewContainer.requestFocus();
            mPhoneNumberViewContainer.setError(mInvalidMobileString);
        } else {
            mPhoneNumberViewContainer.setErrorEnabled(false);
            mPhoneNumberViewContainer.setError(null);
        }

        if (!emailValidation) {
            mEmailViewContainer.setErrorEnabled(true);
            mEmailViewContainer.requestFocus();
            mEmailViewContainer.setError(mErrorEmailString);
        } else {
            mEmailViewContainer.setErrorEnabled(false);
            mEmailViewContainer.setError(null);
        }

        if (!addressValidation) {
            mAddressViewContainer.setErrorEnabled(true);
            mAddressViewContainer.requestFocus();
            mAddressViewContainer.setError(mInvalidAddressString);
        } else {
            mAddressViewContainer.setErrorEnabled(false);
            mAddressViewContainer.setError(null);
        }

        if (!pinCodeValidation) {
            mPinCodeViewContainer.setErrorEnabled(true);
            mPinCodeViewContainer.requestFocus();
            mPinCodeViewContainer.setError(mInvalidPinCodeString);
        } else {
            mPinCodeViewContainer.setErrorEnabled(false);
            mPinCodeViewContainer.setError(null);
        }

        return nameValidation && phoneNumberValidation && emailValidation && addressValidation && pinCodeValidation;
    }

    private void onBackPress() {
        if (isDirty()) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(AddressActivity.this);

            builder.setTitle(R.string.discard_address);
            builder.setMessage(R.string.discard_changes);
            builder.setNegativeButton(R.string.no, null);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
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
        if (mOriginalAddress != null) {
            Address address = new Address();
            getAddressFromField(address);
            return !address.fullName.equals(mOriginalAddress.fullName)
                    || !address.fullAddress.equals(mOriginalAddress.fullAddress)
                    || !address.pinCode.equals(mOriginalAddress.pinCode)
                    || !address.emailAddress.equals(mOriginalAddress.emailAddress)
                    || !address.mobileNumber.equals(mOriginalAddress.mobileNumber);
        }
        return CommonUtil.isNotEmpty(mNameView.getText().toString())
                || CommonUtil.isNotEmpty(mPhoneNumberView.getText().toString())
                || CommonUtil.isNotEmpty(mPinCodeView.getText().toString())
                || CommonUtil.isNotEmpty(mEmailView.getText().toString())
                || CommonUtil.isNotEmpty(mAddressView.getText().toString());
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
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        onShowErrorDialog(s, feedParticipationEnum);
    }

    @Override
    public void showEmptyScreen(String s) {

    }
    //endregion
}
