package com.spacialist.ui.nav;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.ImageRequester;
import com.spacialist.data.dto.HistoryParceable;
import com.spacialist.service.Utility;
import com.spacialist.ui.main.MainActivity;

import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */
public class UserHistoryCardRecyclerViewAdapter extends RecyclerView.Adapter<UserHistoryCardViewHolder> {

    private List<HistoryParceable> userHistoryDTOS;
    private ImageRequester imageRequester;
    private Bundle bundle;

    private View layoutView;
    private Resources resources;

    public UserHistoryCardRecyclerViewAdapter(List<HistoryParceable> userHistoryDTOS) {
        this.userHistoryDTOS = userHistoryDTOS;
        imageRequester = ImageRequester.getInstance();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public UserHistoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_card, parent, false);
        UserHistoryCardViewHolder userHistoryCardViewHolder = new UserHistoryCardViewHolder(layoutView);
        resources = parent.getResources();
        return userHistoryCardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final UserHistoryCardViewHolder holder, int position) {
        if (userHistoryDTOS != null && position < userHistoryDTOS.size()) {
            HistoryParceable userHistoryDTO = userHistoryDTOS.get(position);
            String transactionDate = Utility.convertToTransactionDateFromDBString(userHistoryDTO.getTransactionDate());

            String appointmentDate = Utility.convertFromYYYYMMDD(userHistoryDTO.getAppointmentDate());
            String startTime = Utility.convertToTimeString(userHistoryDTO.getStartTime());
            String endTime = Utility.convertToTimeString(userHistoryDTO.getEndTime());

            holder.labelTransactionDate.setText("" + transactionDate);
            holder.spaBusinessName.setText(userHistoryDTO.getBusName());
            holder.address.setText(userHistoryDTO.getBus_address());
            holder.amount.setText(userHistoryDTO.getAmount());
            holder.appointmentDate.setText(appointmentDate);
            holder.dtiNo.setText(userHistoryDTO.getDtiNo());
            holder.serviceName.setText(userHistoryDTO.getServiceName());
            holder.startTime.setText(startTime);
            holder.endTime.setText(endTime);
            holder.bookingId.setText(userHistoryDTO.getTransactionId());
            holder.appointmentId.setText(userHistoryDTO.getAppointmentId());
            holder.paymentId.setText(userHistoryDTO.getPaymentId());
            holder.staffName.setText(userHistoryDTO.getFirstname());
            String appointmentSts = userHistoryDTO.getAppointmentStatus();
            if (appointmentSts.equalsIgnoreCase("completed")) {
                holder.labelAppointmentStatus.setBackgroundColor(resources.getColor(R.color.label_completed_status));
            } else if (appointmentSts.equalsIgnoreCase("noshow") || appointmentSts.equalsIgnoreCase("no show")) {
                appointmentSts = "no show";
                holder.labelAppointmentStatus.setBackgroundColor(resources.getColor(R.color.label_no_show_status));
            }

            holder.labelAppointmentStatus.setText(appointmentSts);
        }

        holder.historyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (MainActivity) view.getContext();
                Fragment historyDetails = new HistoryDetailsFragment();

                if (bundle != null) {
                    String selectedDateTime = bundle.getString(Constants.SELECTED_DATE_TIME);
                    System.out.println("SPA BUSINESS Booking DateTime: " + selectedDateTime);
                } else {
                    System.out.println("SPA BUSINESS BUNDLE IS EMPTY!");
                }

                bundle.putString(Constants.HISTORY_BOOKING_ID, "" + holder.bookingId.getText());
                bundle.putString(Constants.HISTORY_APPOINTMENT_ID, "" + holder.appointmentId.getText());
                bundle.putString(Constants.HISTORY_BUSINESS_NAME, "" + holder.spaBusinessName.getText());
                bundle.putString(Constants.HISTORY_BUSINESS_ADDRESS, "" + holder.address.getText());
                bundle.putString(Constants.HISTORY_TRANSACTION_DATE, "" + holder.labelTransactionDate.getText());
                bundle.putString(Constants.HISTORY_APPOINTMENT_DATE, "" + holder.appointmentDate.getText());
                bundle.putString(Constants.HISTORY_APPOINTMENT_STATUS, "" + holder.labelAppointmentStatus.getText());
                bundle.putString(Constants.HISTORY_APPOINTMENT_START_TIME, "" + holder.startTime.getText());
                bundle.putString(Constants.HISTORY_APPOINTMENT_END_TIME, "" + holder.endTime.getText());
                bundle.putString(Constants.HISTORY_BUSINESS_DTI_NO, "" + holder.dtiNo.getText());
                bundle.putString(Constants.HISTORY_SERVICE_NAME, "*" + holder.serviceName.getText());
                bundle.putString(Constants.HISTORY_BUSINESS_STAFF_NAME, "" + holder.staffName.getText());
                bundle.putString(Constants.HISTORY_SERVICE_AMOUNT, "" + holder.amount.getText());
                bundle.putString(Constants.HISTORY_TRANSACTION_PAYMENT_ID, "" + holder.paymentId.getText());
                historyDetails.setArguments(bundle);
                ((MainActivity) activity).navigateTo(historyDetails, true);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (userHistoryDTOS != null) {
//            System.out.println("HISTORY GOT RESULTS NOW!");
            return userHistoryDTOS.size();
        } else {
            System.out.println("HISTORY IS 0");
            return 0;
        }
    }
}
