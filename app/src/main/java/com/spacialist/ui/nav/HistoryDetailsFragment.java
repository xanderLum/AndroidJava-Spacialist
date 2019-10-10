package com.spacialist.ui.nav;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.spacialist.R;
import com.spacialist.data.Constants;

public class HistoryDetailsFragment extends Fragment {
    private Bundle extras;
    private Toolbar toolbar;
    public TextView spaBusinessName;
    public TextView labelAppointmentStatus;
    public TextView address;
    public TextView serviceName;

    public TextView amount;
    public TextView dtiNo;
    public TextView startTime;
    public TextView endTime;
    public TextView appointmentDate;
    public TextView appointmentId;
    public TextView bookingId;
    public TextView staffName;
    public TextView paymentId;
    private Resources resources;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Check HISTORY INFLATER");
        final View view = inflater.inflate(R.layout.user_history_details_card, container, false);
        extras = this.getArguments();
        // Set up the tool bar
        setUpToolbar(view);
        resources = container.getResources();

        toolbar.setTitle(extras.getString(Constants.HISTORY_TRANSACTION_DATE));
        initViews(view);
        setViewElements(extras);
        return view;
    }

    private void setUpToolbar(View view) {
        toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }

    private void initViews(View view) {
        spaBusinessName = view.findViewById(R.id.label_business_name);
        labelAppointmentStatus = view.findViewById(R.id.label_appointment_status);
        address = view.findViewById(R.id.label_business_address);
        serviceName = view.findViewById(R.id.label_service_name);

        amount = view.findViewById(R.id.amount);
        dtiNo = view.findViewById(R.id.label_dti_no);
        startTime = view.findViewById(R.id.start_time);
        endTime = view.findViewById(R.id.end_time);
        appointmentDate = view.findViewById(R.id.label_appointment_date);
        appointmentId = view.findViewById(R.id.appointment_id);
        bookingId = view.findViewById(R.id.booking_id);
        staffName = view.findViewById(R.id.staff_name);
        paymentId = view.findViewById(R.id.payment_id);
    }

    private void setViewElements(Bundle bundle) {
        spaBusinessName.setText(bundle.getString(Constants.HISTORY_BUSINESS_NAME));
        String appointmentSts = bundle.getString(Constants.HISTORY_APPOINTMENT_STATUS);
        if (appointmentSts.equalsIgnoreCase("completed")) {
            labelAppointmentStatus.setBackgroundColor(resources.getColor(R.color.label_completed_status));
        } else if (appointmentSts.equalsIgnoreCase("noshow") || appointmentSts.equalsIgnoreCase("no show")) {
            appointmentSts = "no show";
            labelAppointmentStatus.setBackgroundColor(resources.getColor(R.color.label_no_show_status));
        }
        labelAppointmentStatus.setText(appointmentSts);


        address.setText(bundle.getString(Constants.HISTORY_BUSINESS_ADDRESS));
        serviceName.setText(bundle.getString(Constants.HISTORY_SERVICE_NAME));

        amount.setText("Amount paid: " + bundle.getString(Constants.HISTORY_SERVICE_AMOUNT));
        dtiNo.setText("DTI no: " + bundle.getString(Constants.HISTORY_BUSINESS_DTI_NO));
        startTime.setText("start time: " + bundle.getString(Constants.HISTORY_APPOINTMENT_START_TIME));
        endTime.setText("end time: " + bundle.getString(Constants.HISTORY_APPOINTMENT_END_TIME));
        appointmentDate.setText("Date: " + bundle.getString(Constants.HISTORY_APPOINTMENT_DATE));
        appointmentId.setText("Appointment ID: " + bundle.getString(Constants.HISTORY_APPOINTMENT_ID));
        bookingId.setText("Booking ID: " + bundle.getString(Constants.HISTORY_BOOKING_ID));
        staffName.setText("Staff: " + bundle.getString(Constants.HISTORY_BUSINESS_STAFF_NAME));
        paymentId.setText("Payment ID: " + bundle.getString(Constants.HISTORY_TRANSACTION_PAYMENT_ID));
    }

}
