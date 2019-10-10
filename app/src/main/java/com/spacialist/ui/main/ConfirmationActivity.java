package com.spacialist.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ProofOfPayment;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.ImageRequester;

public class ConfirmationActivity extends Fragment {

    private NetworkImageView businessLogo;
    private TextView businessName;
    private TextView businessAddress;
    private TextView serviceName;
    private TextView serviceDescription;
    private TextView staffName;
    private TextView serviceDuration;
    private TextView selectedDateTime;

    private TextView userFullName;
    private TextView userEmail;
    private TextView userContactNo;
    private TextView address;
    private TextView tnc;

    private Bundle bundle;
    private ImageRequester imageRequester;
    private MaterialButton homeButton;
    private PaymentConfirmation paymentConfirm;
    private ProofOfPayment proofOfPayment;
    private TextView bookingFee;
    private TextView totalPayableTextView;
    private TextView unitPrice;
    private TextView vatPercentage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_payment_confirmation, container, false);
        bundle = this.getArguments();

        new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                .setTitle("Book Service")
                .setMessage("Thank you!")
                .setTitle("Payment successful")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();

        if (bundle != null) {
            paymentConfirm = bundle.getParcelable(Constants.PAYMENT_DETAILS);
            proofOfPayment = bundle.getParcelable(Constants.PAYMENT_PROOF);
        } else {
            Log.i("Bundle state", "Bundle is NULL!");
        }
        initViews(view);
        setViewElements(bundle);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment bookFragment = new NavigationFragment();
                bookFragment.setArguments(bundle);
                ((NavigationHost) getActivity()).navigateTo(bookFragment, false); // Navigate to the next Fragment
            }
        });

        return view;
    }

    private void initViews(View view) {
        businessLogo = view.findViewById(R.id.business_image);
        businessName = view.findViewById(R.id.business_name);
        businessAddress = view.findViewById(R.id.business_address);
        serviceName = view.findViewById(R.id.service_name);
        serviceDescription = view.findViewById(R.id.service_description);
        staffName = view.findViewById(R.id.staff_name);
        serviceDuration = view.findViewById(R.id.service_duration);
        userFullName = view.findViewById(R.id.full_name);
        userEmail = view.findViewById(R.id.email);
        userContactNo = view.findViewById(R.id.phone_num);
        address = view.findViewById(R.id.address);
        selectedDateTime = view.findViewById(R.id.selectedDateTime);
        tnc = view.findViewById(R.id.termsAndConditions);
        totalPayableTextView = view.findViewById(R.id.total_fees);
        bookingFee = view.findViewById(R.id.booking_fee);

        homeButton = view.findViewById(R.id.homeButton);
        unitPrice = view.findViewById(R.id.net_price);
        vatPercentage = view.findViewById(R.id.vat_percent);
    }

    private void setViewElements(Bundle bundle) {
        imageRequester = ImageRequester.getInstance();
        imageRequester.setImageFromUrl(businessLogo, bundle.getString(Constants.SELECTED_BUSINESS_LOGO_URI));
        businessName.setText(bundle.getString(Constants.SELECTED_BUSINESS_NAME));
        businessAddress.setText(bundle.getString(Constants.SELECTED_BUSINESS_ADDRESS));
        selectedDateTime.setText(bundle.getString(Constants.SELECTED_DATE_TIME));
        userFullName.setText(bundle.getString(Constants.USER_FULL_NAME));
        userEmail.setText(bundle.getString(Constants.USER_EMAIL));
        userContactNo.setText(bundle.getString(Constants.USER_PHONE_NUM));
        address.setText(bundle.getString(Constants.USER_ADDRESS));
        serviceName.setText(bundle.getString(Constants.SELECTED_SERVICE_NAME));
        serviceDescription.setText(bundle.getString(Constants.SELECTED_SERVICE_DESCRIPTION));
        serviceDuration.setText(bundle.getString(Constants.SELECTED_SERVICE_DURATION));
        staffName.setText(bundle.getString(Constants.SELECTED_ASSIGNED_STAFF_NAME));
        bookingFee.setText(Constants.PESO_SIGN + Constants.BOOKING_FEE_TEN_PESOS);
        totalPayableTextView.setText(Constants.PESO_SIGN + bundle.getString(Constants.COMPUTED_TOTAL_FEES_IN_PHP));
        unitPrice.setText(bundle.getString(Constants.COMPUTED_UNIT_PRICE));
        vatPercentage.setText(bundle.getString(Constants.COMPUTED_VAT_PERCENTAGE));
    }
}
