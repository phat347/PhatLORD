package com.phatle.smartrestaurant;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantDetail extends AppCompatActivity {

    RestaurantDrawerItem IntentItem;
    ImageView restaurantImage;
    TextView name, type, status1, status2, overallRating, toolbarTitle;
    ImageView dollar1, dollar2, dollar3, dollar4, backBtn;
    FrameLayout itemLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        Intent intent = RestaurantDetail.this.getIntent();
        IntentItem = (RestaurantDrawerItem) intent.getSerializableExtra("RestaurantDetail");
        restaurantImage = findViewById(R.id.restaurant_img);
        name = findViewById(R.id.restaurant_name);
        type = findViewById(R.id.restaurant_type);
        status1 = findViewById(R.id.restaurant_status1);
        status2 = findViewById(R.id.restaurant_status2);
        overallRating = findViewById(R.id.restaurant_score);
        dollar1 = findViewById(R.id.dollar1);
        dollar2 = findViewById(R.id.dollar2);
        dollar3 = findViewById(R.id.dollar3);
        dollar4 = findViewById(R.id.dollar4);
        itemLayout = findViewById(R.id.itemLayout);
        toolbarTitle = findViewById(R.id.toolbar_title);
        backBtn = findViewById(R.id.btn_menu);
        restaurantImage.setImageResource(IntentItem.getImgRes());
        name.setText(IntentItem.getName());
        toolbarTitle.setText(IntentItem.getName());
        type.setText(IntentItem.getType());


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
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
