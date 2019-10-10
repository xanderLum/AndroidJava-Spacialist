package com.spacialist.ui.nav;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.material.card.MaterialCardView;
import com.spacialist.R;

public class UserHistoryCardViewHolder extends RecyclerView.ViewHolder {

    public MaterialCardView historyCard;
    public NetworkImageView businessLogo;
    public TextView spaBusinessName;
    public TextView labelTransactionDate;
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

    public UserHistoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        historyCard = itemView.findViewById(R.id.history_card);
        spaBusinessName = itemView.findViewById(R.id.label_business_name);
        address = itemView.findViewById(R.id.label_business_address);
        serviceName = itemView.findViewById(R.id.label_service_name);
        labelTransactionDate = itemView.findViewById(R.id.label_transaction_date);
        labelAppointmentStatus = itemView.findViewById(R.id.label_appointment_status);
        appointmentDate = itemView.findViewById(R.id.label_appointment_date);
        dtiNo = itemView.findViewById(R.id.dti_no);
        amount = itemView.findViewById(R.id.amount);
        startTime = itemView.findViewById(R.id.start_time);
        endTime = itemView.findViewById(R.id.end_time);
        appointmentId = itemView.findViewById(R.id.appointment_id);
        bookingId = itemView.findViewById(R.id.booking_id);
        staffName = itemView.findViewById(R.id.staff_name);
        paymentId = itemView.findViewById(R.id.payment_id);
    }
}
