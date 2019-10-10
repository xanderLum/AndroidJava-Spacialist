package com.spacialist.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.databinding.DataBindingUtil;
//import androidx.databinding.ViewDataBinding;
/*import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;*/
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.DatabindContext;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.ImageRequester;
import com.spacialist.data.dto.SpaBusinessEntry;
import com.spacialist.data.dto.SpaServiceEntry;
import com.spacialist.ui.login.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */
public class SpaBusinessCardRecyclerViewAdapter extends RecyclerView.Adapter<SpaBusinessCardViewHolder> {

    private List<SpaBusinessEntry> businessEntries;
    private ImageRequester imageRequester;
    private Bundle bundle;

    private View layoutView;

    SpaBusinessCardRecyclerViewAdapter(List<SpaBusinessEntry> businessEntries) {
        this.businessEntries = businessEntries;

        imageRequester = ImageRequester.getInstance();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public SpaBusinessCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spa_business_card, parent, false);
        SpaBusinessCardViewHolder spaBusinessCardViewHolder = new SpaBusinessCardViewHolder(layoutView);
        return spaBusinessCardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SpaBusinessCardViewHolder holder, int position) {
        if (businessEntries != null && position < businessEntries.size()) {
            SpaBusinessEntry spaBusiness = businessEntries.get(position);
            holder.spaBusinessId.setText("" + spaBusiness.busId);
            holder.spaBusinessName.setText(spaBusiness.name);
            holder.address.setText(spaBusiness.address);
            holder.businessLogoURI.setText(Constants.IP + spaBusiness.url);
            imageRequester.setImageFromUrl(holder.businessLogo, Constants.IP + spaBusiness.url);
        }

        /*serviceViewModel.getSearchResult().observe(lifecycleOwner, new Observer<SearchResult>() {
            @Override
            public void onChanged(@Nullable SearchResult searchResult) {
                if (searchResult == null) {
                    return;
                }
                if (searchResult.getError() != null) {
                    showSearchFailed(searchResult.getError());
                }
                if (searchResult.getServiceList() != null) {
                    serviceEntries = searchResult.getServiceList();
                    updateUiWithServices(serviceEntries, holder);
                }
            }
        });

        serviceViewModel.getIsLoading().observe(lifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    processDialog = new ProgressDialog(context);
                    processDialog.setMessage("Please  Wait ...");
                    processDialog.setCancelable(false);
                    processDialog.show();
                } else {
                    if (processDialog != null && processDialog.isShowing()) {
                        processDialog.dismiss();
                    }
                }
            }
        });*/

        holder.businessCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (MainActivity) view.getContext();
                Fragment servicesFragment = new SpaBusinessServicesFragment();

                if (bundle != null) {
                    String selectedDateTime = bundle.getString(Constants.SELECTED_DATE_TIME);
                    System.out.println("SPA BUSINESS Booking DateTime: " + selectedDateTime);
                } else {
                    System.out.println("SPA BUSINESS BUNDLE IS EMPTY!");
                }
                bundle.putString(Constants.SELECTED_BUSINESS_ID, "" + holder.spaBusinessId.getText());
                bundle.putString(Constants.SELECTED_BUSINESS_NAME, "" + holder.spaBusinessName.getText());
                bundle.putString(Constants.SELECTED_BUSINESS_ADDRESS, "" + holder.address.getText());
                bundle.putString(Constants.SELECTED_BUSINESS_LOGO_URI, "" + holder.businessLogoURI.getText());
                servicesFragment.setArguments(bundle);
                ((MainActivity) activity).navigateTo(servicesFragment, true);
            }
        });
    }

   /* private void updateUiWithServices(List<SpaServiceEntry> serviceEntries, SpaBusinessCardViewHolder holder) {
        if (processDialog != null && processDialog.isShowing()) {
            processDialog.dismiss();
        }

        AppCompatActivity activity = (MainActivity) context;
        Fragment servicesFragment = new SpaBusinessServicesFragment();

        if (bundle != null) {
            String selectedDateTime = bundle.getString(Constants.SELECTED_DATE_TIME);
            System.out.println("SPA BUSINESS Booking DateTime: " + selectedDateTime);
        } else {
            System.out.println("SPA BUSINESS BUNDLE IS EMPTY!");
        }
        bundle.putString(Constants.SELECTED_BUSINESS_ID, "" + holder.spaBusinessId.getText());
        bundle.putString(Constants.SELECTED_BUSINESS_NAME, "" + holder.spaBusinessName.getText());
        bundle.putString(Constants.SELECTED_BUSINESS_ADDRESS, "" + holder.address.getText());
        bundle.putString(Constants.SELECTED_BUSINESS_LOGO_URI, "" + holder.businessLogoURI.getText());
//        servicesFragment.setArguments(bundle);

        ArrayList<SpaServiceEntry> arrayList = new ArrayList<>();
        arrayList.addAll(serviceEntries);
        bundle.putParcelableArrayList(Constants.SEARCH_SERVICES_LIST_RESULTS, arrayList);
        servicesFragment.setArguments(bundle);

        ((MainActivity) activity).navigateTo(servicesFragment, true);
    }*/



    @Override
    public int getItemCount() {
        return businessEntries != null ? businessEntries.size() : 0;
    }

}
