package com.spacialist.ui.main;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.ImageRequester;
import com.spacialist.data.dto.StaffParcelable;

import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */
public class StaffCardRecyclerViewAdapter extends RecyclerView.Adapter<StaffCardViewHolder> {
    List<StaffParcelable> staffParcel;
    private ImageRequester imageRequester;
    private Bundle bundle;
    private Resources resources;

    private String selectedDateTime;

    StaffCardRecyclerViewAdapter(List<StaffParcelable> staffParcel) {
        this.staffParcel = staffParcel;
        imageRequester = ImageRequester.getInstance();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public StaffCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_card, parent, false);
        resources = parent.getResources();

        if (bundle != null) {
            selectedDateTime = bundle.getString(Constants.SELECTED_DATE_TIME);
            System.out.println("Booking DateTime: " + selectedDateTime);
        } else {
            System.out.println("BUNDLE IS EMPTY!");
        }
        return new StaffCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull final StaffCardViewHolder holder, int position) {
        if (staffParcel != null && position < staffParcel.size()) {
            StaffParcelable staff = staffParcel.get(position);
            holder.serviceId.setText(staff.serviceId);
            holder.firstName.setText(staff.firstName);
            holder.staffId.setText(staff.staffId);
            holder.staffServiceId.setText(staff.staffServiceId);
            imageRequester.setImageFromUrl(holder.staffLogo, Constants.IP + staff.staffLogo);
        }

        holder.staffCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hr = Integer.parseInt(bundle.getString(Constants.COMPUTED_HR_DURATION));
                int min = Integer.parseInt(bundle.getString(Constants.COMPUTED_MIN_DURATION));

                final AppCompatActivity mainActivity = (MainActivity) view.getContext();
                StringBuilder durationBldr = new StringBuilder();
                durationBldr.append(hr + " hr and " + min + " mins");

                //display staff list available for user to select
                StringBuilder sb = new StringBuilder();
                sb.append(bundle.getString(Constants.SELECTED_SERVICE_NAME)).append(" for " + durationBldr.toString())
                        .append(" on \n" + selectedDateTime);
                sb.append("\n\n");
                sb.append("Service Fee: \t\t\t\t\t\t\t\t\t\t\t\t\t\t" + bundle.getString(Constants.SELECTED_SERVICE_PRICE));
                sb.append("\n");
                sb.append("Transaction fee: \t\t\t\t\t\t\t\t\t\t\t" + Constants.PESO_SIGN + Constants.BOOKING_FEE_TEN_PESOS);
                sb.append("\n");
                sb.append("Total fees: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + Constants.PESO_SIGN + bundle.getString(Constants.COMPUTED_TOTAL_FEES_IN_PHP));
                sb.append("\n");
                sb.append("At " + bundle.getString(Constants.SELECTED_BUSINESS_NAME));
                sb.append("\n");
                sb.append("Address: " + bundle.getString(Constants.SELECTED_BUSINESS_ADDRESS));
                sb.append("\n");
                sb.append("With therapist: " + holder.firstName.getText());
                bundle.putString(Constants.SELECTED_ASSIGNED_STAFF_ID, "" + holder.staffId.getText());
                bundle.putString(Constants.SELECTED_ASSIGNED_STAFF_NAME, "" + holder.firstName.getText());
                bundle.putString(Constants.SELECTED_ASSIGNED_STAFF_SERVICE_ID, "" + holder.staffServiceId.getText());

                new MaterialAlertDialogBuilder(mainActivity, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Book Service")
                        .setMessage("" + sb.toString())
                        .setCancelable(true)
                        .setPositiveButton("Book", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Fragment paymentFragment = new PaymentFragment();

                                paymentFragment.setArguments(bundle);
                                ((MainActivity) mainActivity).navigateTo(paymentFragment, false);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (staffParcel != null) {
            System.out.println("staffParcel GOT RESULTS NOW!");
            return staffParcel.size();
        } else {
            System.out.println("INITIAL IS 0");
            return 0;
        }
    }

}
