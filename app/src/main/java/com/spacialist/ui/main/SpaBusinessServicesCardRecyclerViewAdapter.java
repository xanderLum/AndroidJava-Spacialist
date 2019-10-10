package com.spacialist.ui.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.ImageRequester;
import com.spacialist.data.dto.SpaServiceEntry;
import com.spacialist.data.dto.StaffDTO;
import com.spacialist.data.dto.StaffParcelable;
import com.spacialist.service.Utility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */
public class SpaBusinessServicesCardRecyclerViewAdapter extends RecyclerView.Adapter<SpaBusinessServicesCardViewHolder> {

    private List<SpaServiceEntry> serviceList;
    private List<StaffDTO> staffList;
    private ImageRequester imageRequester;
    private Bundle bundle;
    private Resources resources;

    private String selectedDateTime;

    SpaBusinessServicesCardRecyclerViewAdapter(List<SpaServiceEntry> serviceList, List<StaffDTO> staffList) {
        this.serviceList = serviceList;
        this.staffList = staffList;
        imageRequester = ImageRequester.getInstance();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public SpaBusinessServicesCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spa_business_service_card, parent, false);
        resources = parent.getResources();

        if (bundle != null) {
            selectedDateTime = bundle.getString(Constants.SELECTED_DATE_TIME);
            System.out.println("Booking DateTime: " + selectedDateTime);
        } else {
            System.out.println("BUNDLE IS EMPTY!");
        }
        return new SpaBusinessServicesCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SpaBusinessServicesCardViewHolder holder, int position) {
        if (serviceList != null && position < serviceList.size()) {
            SpaServiceEntry service = serviceList.get(position);
            holder.serviceId.setText(service.serviceId);
            holder.serviceName.setText(service.serviceName);
            holder.serviceDescription.setText(service.serviceDescription);
            holder.serviceType.setText(service.serviceType);
            holder.price.setText(Constants.PESO_SIGN + service.price.toString());
            int hr = Utility.getHrOfDate(service.serviceDuration);
            int min = Utility.getMinsOfDate(service.serviceDuration);
            StringBuilder sb = new StringBuilder();
            sb.append(hr + " hr and " + min + " mins");
            holder.serviceDuration.setText(sb.toString());
            holder.intHrDuration.setText(String.valueOf(hr));
            holder.intMinDuration.setText(String.valueOf(min));
            StaffDTO staff = getStaffAvailable(String.valueOf(holder.serviceId.getText()));
            if (staff == null) {
                holder.serviceCard.setCardBackgroundColor(resources.getColor(R.color.text_color_dirtwhite));
            } else {
                //place Staff list as parcel to transfer and carry to next page/fragment
                holder.staffParcelList = convertStaffDTOToParcel(holder.serviceId.getText().toString());
            }
        }

        holder.serviceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountToPay = holder.price.getText().toString().substring(1);
                final String totalPayableStrPHP = String.valueOf(Double.parseDouble(amountToPay)
                        + Double.parseDouble(Constants.BOOKING_FEE_TEN_PESOS));
                Double totalPayable = Utility.convertPesoToUSD(Double.parseDouble(amountToPay)
                        + Double.parseDouble(Constants.BOOKING_FEE_TEN_PESOS));
                final String totalPayableStrUSD = String.valueOf(totalPayable);

                final AppCompatActivity mainActivity = (MainActivity) view.getContext();
                if (getStaffAvailable(String.valueOf(holder.serviceId.getText())) == null) {
                    Toast.makeText(mainActivity, "No available therapist for this service for your booking time.", Toast.LENGTH_LONG).show();
                } else {
                    Fragment staffGridFragment = new StaffGridFragment();
                    bundle.putString(Constants.SELECTED_DATE_TIME, selectedDateTime);
                    bundle.putString(Constants.SELECTED_SERVICE_NAME, "" + holder.serviceName.getText());
                    bundle.putString(Constants.SELECTED_SERVICE_DESCRIPTION, "" + holder.serviceDescription.getText());
                    bundle.putString(Constants.SELECTED_SERVICE_PRICE, "" + holder.price.getText());
                    bundle.putString(Constants.SELECTED_SERVICE_DURATION, "" + holder.serviceDuration.getText());
                    bundle.putString(Constants.COMPUTED_HR_DURATION, "" + holder.intHrDuration.getText());
                    bundle.putString(Constants.COMPUTED_MIN_DURATION, "" + holder.intMinDuration.getText());
                    bundle.putString(Constants.SELECTED_SERVICE_ID, "" + holder.serviceId.getText());
                    /*bundle.putString(Constants.AUTO_ASSIGNED_STAFF_ID, "" + holder.autoAssignedStaffId.getText());
                    bundle.putString(Constants.AUTO_ASSIGNED_STAFF_NAME, "" + holder.autoAssignedStaffName.getText());
                    bundle.putString(Constants.AUTO_ASSIGNED_STAFF_SERVICE_ID, "" + holder.autoAssignedStaffServiceId.getText());*/
                    bundle.putString(Constants.COMPUTED_TOTAL_FEES_IN_PHP, "" + totalPayableStrPHP);
                    bundle.putString(Constants.COMPUTED_TOTAL_FEES_IN_USD, "" + totalPayableStrUSD);
                    List<StaffParcelable> staffParcelList = convertStaffDTOToParcel(String.valueOf(holder.serviceId.getText()));
                    Log.i("STAFF SIZE", "equals " + staffParcelList.size());
                    setStaffParcelableToBundle(staffParcelList);
                    staffGridFragment.setArguments(bundle);
                    ((MainActivity) mainActivity).navigateTo(staffGridFragment, false);

                    //display staff list available for user to select
                   /* StringBuilder sb = new StringBuilder();
                    sb.append(holder.serviceName.getText()).append(" for " + holder.serviceDuration.getText())
                            .append(" on " + selectedDateTime);
                    sb.append("\n");
                    sb.append("Service Fee: " + holder.price.getText());
                    sb.append("\n");
                    sb.append("Transaction fee: " + Constants.PESO_SIGN + Constants.BOOKING_FEE_TEN_PESOS);
                    sb.append("\n");
                    sb.append("Total fees: " + Constants.PESO_SIGN + totalPayableStrPHP);
                    sb.append("\n");
                    sb.append("At " + bundle.getString(Constants.SELECTED_BUSINESS_NAME));
                    sb.append("\n");
                    sb.append("Address: " + bundle.getString(Constants.SELECTED_BUSINESS_ADDRESS));

                    new MaterialAlertDialogBuilder(mainActivity, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Book Service")
                            .setMessage("" + sb.toString())
                            .setCancelable(true)
                            .setPositiveButton("Book", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Fragment paymentFragment = new PaymentFragment();
                                    bundle.putString(Constants.SELECTED_DATE_TIME, selectedDateTime);
                                    bundle.putString(Constants.SELECTED_SERVICE_NAME, "" + holder.serviceName.getText());
                                    bundle.putString(Constants.SELECTED_SERVICE_DESCRIPTION, "" + holder.serviceDescription.getText());
                                    bundle.putString(Constants.SELECTED_SERVICE_PRICE, "" + holder.price.getText());
                                    bundle.putString(Constants.SELECTED_SERVICE_DURATION, "" + holder.serviceDuration.getText());
                                    bundle.putString(Constants.COMPUTED_HR_DURATION, "" + holder.intHrDuration.getText());
                                    bundle.putString(Constants.COMPUTED_MIN_DURATION, "" + holder.intMinDuration.getText());
                                    bundle.putString(Constants.AUTO_ASSIGNED_STAFF_ID, "" + holder.autoAssignedStaffId.getText());
                                    bundle.putString(Constants.SELECTED_SERVICE_ID, "" + holder.serviceId.getText());
                                    bundle.putString(Constants.AUTO_ASSIGNED_STAFF_NAME, "" + holder.autoAssignedStaffName.getText());
                                    bundle.putString(Constants.AUTO_ASSIGNED_STAFF_SERVICE_ID, "" + holder.autoAssignedStaffServiceId.getText());
                                    bundle.putString(Constants.COMPUTED_TOTAL_FEES_IN_PHP, "" + totalPayableStrPHP);
                                    bundle.putString(Constants.COMPUTED_TOTAL_FEES_IN_USD, "" + totalPayableStrUSD);
                                    paymentFragment.setArguments(bundle);
                                    ((MainActivity) mainActivity).navigateTo(paymentFragment, false);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();*/
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (serviceList != null) {
//            System.out.println("GOT RESULTS NOW!");
            return serviceList.size();
        } else {
            System.out.println("INITIAL IS 0");
            return 0;
        }
    }

    private StaffDTO getStaffAvailable(String serviceId) {
        StaffDTO staffAvailable = null;
        for (StaffDTO staff : staffList) {
            if (serviceId.equalsIgnoreCase(staff.getServiceId())) {
                staffAvailable = staff;
                System.out.println("STAFF found for serviceID: " + serviceId);
                break;
            }
        }

        return staffAvailable;
    }

    private void setStaffParcelableToBundle(List<StaffParcelable> staffParcelList) {
        ArrayList<StaffParcelable> staffParcel = new ArrayList<>();
        staffParcel.addAll(staffParcelList);
        System.out.println("STAFF PARCEL SIZE: " + staffParcel.size());
        bundle.putParcelableArrayList(Constants.STAFF_PARCEL, staffParcel);
    }

    private List<StaffParcelable> convertStaffDTOToParcel(String serviceId) {
        List<StaffParcelable> staffParcel = new LinkedList<>();
        StaffParcelable item;
        for (StaffDTO staff : staffList) {
            if (staff.getServiceId().equalsIgnoreCase(serviceId)) {
                item = new StaffParcelable(staff.getStaffServiceId(),
                        staff.getServiceId(), staff.getStaffId(), staff.getFirstName(),
                        staff.getStaffLogo());
                staffParcel.add(item);
            }
        }

        return staffParcel;
    }

}
