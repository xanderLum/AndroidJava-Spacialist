package com.spacialist.ui.main;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.material.card.MaterialCardView;
import com.spacialist.R;

public class SpaBusinessCardViewHolder extends RecyclerView.ViewHolder {

    public MaterialCardView businessCard;
    public NetworkImageView businessLogo;
    public TextView spaBusinessId;
    public TextView spaBusinessName;
    public TextView address;
//    public TextView location;
    public TextView businessLogoURI;

    public SpaBusinessCardViewHolder(@NonNull View itemView) {
        super(itemView);
        businessLogoURI = itemView.findViewById(R.id.business_logo_URI);
        spaBusinessId = itemView.findViewById(R.id.business_id);
        businessCard = itemView.findViewById(R.id.businessCard);
        businessLogo = itemView.findViewById(R.id.business_image);
        spaBusinessName = itemView.findViewById(R.id.business_name);
        address = itemView.findViewById(R.id.business_address);
//        location = itemView.findViewById(R.id.business_location);
    }
}
