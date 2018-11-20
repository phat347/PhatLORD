package com.phatle.smartrestaurant;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class OverviewFragment extends Fragment{

    RestaurantDrawerItem IntentItem;
    ImageView restaurantImage, clockTime;
    TextView name, type, status1, status2, overallRating, clockTimeStatus,decription;
    ImageView dollar1, dollar2, dollar3, dollar4;
    FrameLayout itemLayout;
    LinearLayout btnDirect,btnPhone;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        Intent intent = getActivity().getIntent();
        IntentItem = (RestaurantDrawerItem) intent.getSerializableExtra("RestaurantDetail");
        restaurantImage = view.findViewById(R.id.restaurant_img);
        name = view.findViewById(R.id.restaurant_name);
        type = view.findViewById(R.id.restaurant_type);
        status1 = view.findViewById(R.id.restaurant_status1);
        status2 = view.findViewById(R.id.restaurant_status2);
        overallRating = view.findViewById(R.id.restaurant_score);
        dollar1 = view.findViewById(R.id.dollar1);
        dollar2 = view.findViewById(R.id.dollar2);
        dollar3 = view.findViewById(R.id.dollar3);
        dollar4 = view.findViewById(R.id.dollar4);
        itemLayout = view.findViewById(R.id.itemLayout);
        clockTime = view.findViewById(R.id.res_time_img);
        clockTimeStatus = view.findViewById(R.id.res_time_text);
        decription = view.findViewById(R.id.restaurant_description);
        decription.setText(IntentItem.getDecription());
        btnPhone = view.findViewById(R.id.btn_phone_call);
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + IntentItem.getPhoneNumber()));
                startActivity(intent);
            }
        });
        btnDirect = view.findViewById(R.id.btn_direct);
        btnDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResDetail = new Intent(getActivity(),DirectActivity.class);
                intentResDetail.putExtra("RestaurantDetail", (Serializable) IntentItem);
                startActivity(intentResDetail);
            }
        });
        Picasso.with(getContext())
                .load(IntentItem.getImgRes())
                .into(restaurantImage);
        name.setText(IntentItem.getName());
        type.setText(IntentItem.getType());

        if(IntentItem.isStatus())
        {
            Picasso.with(getContext())
                    .load(R.drawable.clock_time)
                    .into(clockTime);
            clockTimeStatus.setTextColor(getResources().getColor(R.color.green));
        }
        else {
            Picasso.with(getContext())
                    .load(R.drawable.clock_time_inactive)
                    .into(clockTime);
            clockTimeStatus.setTextColor(getResources().getColor(R.color.gray));
        }

        String openTime = IntentItem.getOpenTime();

        String[] openTimeformat = openTime.split(":");
        String openTimeHours = openTimeformat[0];
        String openTimeMins = openTimeformat[1];

        String AmOrPm;
        String OpenTimeformated;
        if(Integer.parseInt(openTimeHours)==0)
        {
            AmOrPm = "AM";
            OpenTimeformated = openTime + AmOrPm;
        }
        else if (Integer.parseInt(openTimeHours)==12)
        {
            AmOrPm = "PM";
            OpenTimeformated = openTime + AmOrPm;
        }
        else if(Integer.parseInt(openTimeHours) > 12)
        {
            AmOrPm = "PM";
            int x = Integer.parseInt(openTimeHours) -12;
            OpenTimeformated = x+":"+openTimeMins + AmOrPm;
        }
        else {
            AmOrPm = "AM";
            OpenTimeformated = openTime + AmOrPm;
        }


        String closeTime = IntentItem.getCloseTime();

        String[] closeTimeformat = closeTime.split(":");
        String closeTimeHours = closeTimeformat[0];
        String closeTimeMins = closeTimeformat[1];

        String AmOrPm2;
        String  CloseTimeformated;
        if(Integer.parseInt(closeTimeHours)==0)
        {
            AmOrPm2 = "AM";
            CloseTimeformated = closeTime + AmOrPm2;
        }
        else if (Integer.parseInt(closeTimeHours)==12)
        {
            AmOrPm2 = "PM";
            CloseTimeformated = closeTime + AmOrPm2;
        }
        else if(Integer.parseInt(closeTimeHours) > 12)
        {
            AmOrPm2 = "PM";
            int x = Integer.parseInt(closeTimeHours) -12;
            CloseTimeformated = x+":"+closeTimeMins + AmOrPm2;

        }
        else {
            AmOrPm2 = "AM";
            CloseTimeformated = closeTime + AmOrPm2;
        }



        clockTimeStatus.setText( OpenTimeformated+" - "+CloseTimeformated);


        int score = (int)(IntentItem.getOverallRating()*10)/10;
        int point = (int)(IntentItem.getOverallRating()*10)%10;



        if(IntentItem.isStatus())
        {
            status1.setText("Open now");
            status1.setTextColor(Color.rgb(0,128,0));
            status2.setText("    •    " + IntentItem.getDistance() + "m from you    •    " + IntentItem.getLocation() + ", Vietnam");
        }
        else
        {
            status1.setText("Closed");
            status1.setTextColor(Color.RED);
            status2.setText("    •    " + IntentItem.getDistance() + "m from you    •    " + IntentItem.getLocation() + ", Vietnam");
        }
        if(point != 0) {
            overallRating.setText(score + "." + point);
        }
        else overallRating.setText(score+"");

        switch (IntentItem.getPriceRating()){
            case 0:
                dollar1.setImageResource(R.drawable.ic_dollarsign_inactive);
                dollar2.setImageResource(R.drawable.ic_dollarsign_inactive);
                dollar3.setImageResource(R.drawable.ic_dollarsign_inactive);
                dollar4.setImageResource(R.drawable.ic_dollarsign_inactive);
                break;
            case 1:
                dollar1.setImageResource(R.drawable.ic_dollarsign_inactive);
                dollar2.setImageResource(R.drawable.ic_dollarsign_inactive);
                dollar3.setImageResource(R.drawable.ic_dollarsign_inactive);
                dollar4.setImageResource(R.drawable.ic_dollarsign);
                break;
            case 2:
                dollar1.setImageResource(R.drawable.ic_dollarsign_inactive);
                dollar2.setImageResource(R.drawable.ic_dollarsign_inactive);
                dollar3.setImageResource(R.drawable.ic_dollarsign);
                dollar4.setImageResource(R.drawable.ic_dollarsign);
                break;
            case 3:
                dollar1.setImageResource(R.drawable.ic_dollarsign_inactive);
                dollar2.setImageResource(R.drawable.ic_dollarsign);
                dollar3.setImageResource(R.drawable.ic_dollarsign);
                dollar4.setImageResource(R.drawable.ic_dollarsign);
                break;
            case 4:
                dollar1.setImageResource(R.drawable.ic_dollarsign);
                dollar2.setImageResource(R.drawable.ic_dollarsign);
                dollar3.setImageResource(R.drawable.ic_dollarsign);
                dollar4.setImageResource(R.drawable.ic_dollarsign);
                break;
            default:
                dollar1.setImageResource(R.drawable.ic_dollarsign);
                dollar2.setImageResource(R.drawable.ic_dollarsign);
                dollar3.setImageResource(R.drawable.ic_dollarsign);
                dollar4.setImageResource(R.drawable.ic_dollarsign);
                break;
        }


        return view;
    }
}
