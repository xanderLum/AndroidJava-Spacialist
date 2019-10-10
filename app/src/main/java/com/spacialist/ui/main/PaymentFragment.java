package com.spacialist.ui.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ProofOfPayment;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.ImageRequester;
import com.spacialist.data.dto.MessageJSON;
import com.spacialist.data.dto.NotificationDTO;
import com.spacialist.data.dto.PaymentDetailsDTO;
import com.spacialist.service.Utility;
import com.spacialist.ui.login.ViewModelFactory;

import org.json.JSONException;

import java.math.BigDecimal;

import static com.android.volley.VolleyLog.TAG;

public class PaymentFragment extends Fragment {

    private NetworkImageView businessLogo;
    private TextView businessName;
    private TextView businessAddress;
    private TextView serviceName;
    private TextView serviceDescription;
    private TextView staffName;
    private TextView bookingFee;
    private TextView serviceDuration;
    private TextView selectedDateTime;

    private TextView userFullName;
    private TextView userEmail;
    private TextView userContactNo;
    private TextView userAddress;
    private TextView totalPayableTextView;

    private TextView unitPrice;
    private TextView vatPercentage;
    private TextView tnc;

    private MaterialButton payButton;

    private Bundle bundle;
    private ImageRequester imageRequester;
    private String amountToPay;
    private String service;
    private int hrDuration;
    private int minDuration;
    private double totalPayable;
    private String totalPayableStrUSD;
    private String totalPayableStrPHP;
    private String vatPercentageStr;
    private String unitPriceStr;

    private ProgressDialog processDialog;

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 1;
    private String paymentAmount;

    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Constants.PAYPAL_CLIENT_ID);

    private String instanceTokenOfDevice;

    @Override
    public void onDestroy() {
        ((MainActivity) getActivity()).stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.payment_fragment, container, false);
        bundle = this.getArguments();

        amountToPay = bundle.getString(Constants.SELECTED_SERVICE_PRICE);
        service = bundle.getString(Constants.SELECTED_SERVICE_NAME);
        amountToPay = amountToPay.substring(1);
        System.out.println("AMOUNT Payable: " + amountToPay);
        hrDuration = Integer.parseInt(bundle.getString(Constants.COMPUTED_HR_DURATION));
        minDuration = Integer.parseInt(bundle.getString(Constants.COMPUTED_MIN_DURATION));
        unitPriceStr = Utility.calculateUnitPrice(amountToPay);
        vatPercentageStr = Utility.calculateVatPercentage(unitPriceStr);

        Log.i("VAT", "UNIT PRICE: " + unitPriceStr);
        Log.i("VAT", "VAT PERCENT: " + vatPercentageStr);

        bundle.putString(Constants.COMPUTED_UNIT_PRICE, unitPriceStr);
        bundle.putString(Constants.COMPUTED_VAT_PERCENTAGE, vatPercentageStr);

        // Set up the tool bar
        setUpToolbar(view);
        initViews(view);
        setViewElements(bundle);

        Intent intent = new Intent(getActivity(), PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        ((MainActivity) getActivity()).startService(intent);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalPayableStrPHP = String.valueOf(Double.parseDouble(amountToPay)
                        + Double.parseDouble(Constants.BOOKING_FEE_TEN_PESOS));
                totalPayable = Utility.convertPesoToUSD(Double.parseDouble(amountToPay)
                        + Double.parseDouble(Constants.BOOKING_FEE_TEN_PESOS));
                totalPayableStrUSD = String.valueOf(totalPayable);
                System.out.println("Converted to USD: $" + totalPayable);
                getPayment(totalPayable, service);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {
            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                final PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        final String paymentDetails = confirm.toJSONObject().toString(4);
                        final ProofOfPayment proofOfPayment = confirm.getProofOfPayment();
                        String proofOfPaymentStr = proofOfPayment.toJSONObject().toString(4);
                        Log.i("paymentDetails", paymentDetails);
                        Log.i("proofOfPayment", proofOfPaymentStr);

                        //===========SAVE PAYMENT DETAILS TO DB HERE==============
                        final PaymentDetailsDTO paymentDetailsRequest = new PaymentDetailsDTO();
                        paymentDetailsRequest.setUserId(bundle.getString(Constants.USER_ID));
                        paymentDetailsRequest.setBusId(bundle.getString(Constants.SELECTED_BUSINESS_ID));
                        paymentDetailsRequest.setPaymentId(proofOfPayment.getPaymentId());
                        paymentDetailsRequest.setPaymentDetails("Payment for Service: " + service);
                        paymentDetailsRequest.setPaymentTransactionId(proofOfPayment.getTransactionId());
                        paymentDetailsRequest.setAmount(totalPayableStrPHP);
                        paymentDetailsRequest.setSchedDate(
                                Utility.formatDateToYYYYMMDD(bundle.getString(Constants.SELECTED_DATE_TIME)));

                        paymentDetailsRequest.setAppointmentName(String.format(
                                Constants.APPOINTMENT_NAME,
                                bundle.getString(Constants.SELECTED_SERVICE_NAME),
                                bundle.getString(Constants.USER_FULL_NAME)));
                        paymentDetailsRequest.setAppointmentDesc(String.format(
                                Constants.APPOINTMENT_DESC,
                                bundle.getString(Constants.USER_FULL_NAME),
                                bundle.getString(Constants.SELECTED_SERVICE_NAME),
                                bundle.getString(Constants.SELECTED_ASSIGNED_STAFF_NAME)));
                        paymentDetailsRequest.setSchedDate(bundle.getString(Constants.SELECTED_DATE_STRING));
                        paymentDetailsRequest.setStaff_id(bundle.getString(Constants.SELECTED_ASSIGNED_STAFF_ID));
                        paymentDetailsRequest.setStaff_service_id(bundle.getString(Constants.SELECTED_ASSIGNED_STAFF_SERVICE_ID));

                        paymentDetailsRequest.setStartTime(bundle.getString(Constants.SELECTED_TIME_STRING));
                        paymentDetailsRequest.setEndTime(Utility.getDurationTimeStr(paymentDetailsRequest.getStartTime(), hrDuration, minDuration));
                        System.out.println("PaymentDetails Request: " + paymentDetailsRequest.toString());

                        PaymentViewModel paymentViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                                .get(PaymentViewModel.class);

                        final NotificationViewModel notificationViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                                .get(NotificationViewModel.class);
                        setInstanceTokenOfDevice();

                        paymentViewModel.saveTransaction(paymentDetailsRequest);

                        paymentViewModel.getMessageJSON().observe(this, new Observer<MessageJSON>() {
                            @Override
                            public void onChanged(@Nullable MessageJSON message) {
                                if (message == null) {
                                    return;
                                }
                                if (message.getError() != null) {
                                    showTransactionFailed(message.getError());
                                }
                                if (message.getAppointmentId() != null
                                        && Constants.TRANSACTION_SUCCESS.equalsIgnoreCase(message.getMessage())) {
                                    //save to notification
                                    Log.i("Transaction", "Transaction successful!");
                                    Log.i("Notification", "Saving notification...");
                                    NotificationDTO notificationDTO = new NotificationDTO();
                                    notificationDTO.setAppointmentId(message.getAppointmentId());
                                    notificationDTO.setUserId(paymentDetailsRequest.getUserId());
                                    notificationDTO.setBusId(paymentDetailsRequest.getBusId());

                                    notificationDTO.setTitle(Constants.APPOINTMENT_CONFIRMATION_TITLE);
                                    notificationDTO.setMessage(String
                                            .format(Constants.APPOINTMENT_CONFIRMATION_MESSAGE,
                                                    bundle.getString(Constants.SELECTED_BUSINESS_NAME),
                                                    bundle.getString(Constants.SELECTED_DATE_TIME),
                                                    bundle.getString(Constants.SELECTED_SERVICE_NAME)));
                                    notificationDTO.setImage_url(bundle.getString(Constants.SELECTED_BUSINESS_LOGO_URI));
                                    notificationDTO.setFirebaseAPI(Constants.FIREBASE_API_SERVER_KEY);
                                    notificationDTO.setFirebaseToken(instanceTokenOfDevice);
                                    notificationViewModel.saveNotification(notificationDTO);
                                }
                            }
                        });

                        paymentViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(@Nullable Boolean aBoolean) {
                                if (aBoolean) {
                                    processDialog = new ProgressDialog(getActivity());
                                    processDialog.setMessage("Please  Wait ...");
                                    processDialog.setCancelable(false);
                                    processDialog.show();
                                } else {
                                    processDialog.dismiss();
                                }
                            }
                        });


                        notificationViewModel.getMessageJSON().observe(this, new Observer<MessageJSON>() {
                            @Override
                            public void onChanged(@Nullable MessageJSON message) {
                                if (message == null) {
                                    return;
                                }
                                if (message.getError() != null) {
                                    showNotificationFailed(message.getError());
                                }
                                if (message.getMessage() != null) {
                                    //save to notification
                                    Log.i("Notification", "Proceeding to confirmation page.");
                                    updateToConfirmationUI(confirm, proofOfPayment);
                                }
                            }
                        });

                        notificationViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(@Nullable Boolean aBoolean) {
                                if (aBoolean) {
                                    processDialog = new ProgressDialog(getActivity());
                                    processDialog.setMessage("Please  Wait ...");
                                    processDialog.setCancelable(false);
                                    processDialog.show();
                                } else {
                                    processDialog.dismiss();
                                }
                            }
                        });

                        //=======================END==============================
                    } catch (JSONException e) {
                        Log.e("PaymentDetails", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("PaymentDetails", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("PaymentDetails", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    private void updateToConfirmationUI(PaymentConfirmation confirm, ProofOfPayment proofOfPayment) {
        //Starting a new activity for the payment details and also putting the payment details with intent
        ConfirmationActivity confirmationActivity = new ConfirmationActivity();

        bundle.putParcelable(Constants.PAYMENT_DETAILS, confirm);
        bundle.putParcelable(Constants.PAYMENT_PROOF, proofOfPayment);
        confirmationActivity.setArguments(bundle);
        ((NavigationHost) getActivity()).navigateTo(confirmationActivity, false);
    }

    private void showTransactionFailed(@StringRes Integer errorString) {
        Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void showNotificationFailed(@StringRes Integer errorString) {
        Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void getPayment(Double amount, String service) {
        //Getting the amount from editText

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD",
                "Payment for Service booking: " + service,
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(getActivity(), PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }

    private void initViews(View view) {
        businessLogo = view.findViewById(R.id.business_image);
        businessName = view.findViewById(R.id.business_name);
        businessAddress = view.findViewById(R.id.business_address);
        serviceName = view.findViewById(R.id.service_name);
        serviceDescription = view.findViewById(R.id.service_description);
        staffName = view.findViewById(R.id.staff_name);
        bookingFee = view.findViewById(R.id.booking_fee);
        serviceDuration = view.findViewById(R.id.service_duration);
        userFullName = view.findViewById(R.id.full_name);
        userEmail = view.findViewById(R.id.email);
        userContactNo = view.findViewById(R.id.phone_num);
        userAddress = view.findViewById(R.id.address);
        selectedDateTime = view.findViewById(R.id.selectedDateTime);
        totalPayableTextView = view.findViewById(R.id.total_fees);
        tnc = view.findViewById(R.id.termsAndConditions);
        unitPrice = view.findViewById(R.id.net_price);
        vatPercentage = view.findViewById(R.id.vat_percent);

        payButton = view.findViewById(R.id.pay_button);
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
        userAddress.setText(bundle.getString(Constants.USER_ADDRESS));
        serviceName.setText(bundle.getString(Constants.SELECTED_SERVICE_NAME));
        serviceDescription.setText(bundle.getString(Constants.SELECTED_SERVICE_DESCRIPTION));
        serviceDuration.setText(bundle.getString(Constants.SELECTED_SERVICE_DURATION));
        staffName.setText(bundle.getString(Constants.SELECTED_ASSIGNED_STAFF_NAME));
        bookingFee.setText(Constants.PESO_SIGN + Constants.BOOKING_FEE_TEN_PESOS);
        totalPayableTextView.setText(Constants.PESO_SIGN + bundle.getString(Constants.COMPUTED_TOTAL_FEES_IN_PHP));
        unitPrice.setText(unitPriceStr);
        vatPercentage.setText(vatPercentageStr);
    }

    private void setInstanceTokenOfDevice() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        instanceTokenOfDevice = token;
                    }
                });
        // [END retrieve_current_token]
    }
}
