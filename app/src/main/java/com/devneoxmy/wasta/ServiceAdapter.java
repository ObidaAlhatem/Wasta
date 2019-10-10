package com.devneoxmy.wasta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class ServiceAdapter extends ArrayAdapter<Offer> {

    public ServiceAdapter(Context context, List<Offer> services) {
        super(context, 0, services);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_offer_item, parent, false);
        }

        Offer currentOffer = getItem(position);

        TextView serviceName = listItemView.findViewById(R.id.serviceName);
        serviceName.setText(currentOffer.getServiceName());

        TextView serviceDetails = listItemView.findViewById(R.id.serviceDetails);
        serviceDetails.setText(currentOffer.getServiceDetails());

        RatingBar ratingBar = listItemView.findViewById(R.id.simpleRatingBar);
        ratingBar.setNumStars(currentOffer.getRatingBar());

        return listItemView;
    }
}
