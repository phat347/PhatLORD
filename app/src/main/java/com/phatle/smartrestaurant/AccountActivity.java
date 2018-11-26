package com.phatle.smartrestaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    TextView toolbarTitle,userName,userEmail;
    ImageView backBtn,userImg;
    String IntentUsername;
    String IntentPhotoURL,IntentEmail;
    private FirebaseAuth auth;
    private RestaurantItemAdapter3 mAdapter;
    private List<RestaurantDrawerItem> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        userName = findViewById(R.id.tv_username);
        userEmail = findViewById(R.id.tv_email);
        userImg = findViewById(R.id.picture);
        toolbarTitle = findViewById(R.id.toolbar_title);
        backBtn = findViewById(R.id.btn_menu);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        toolbarTitle.setText(R.string.profile);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        Intent intent = AccountActivity.this.getIntent();
        IntentPhotoURL = intent.getStringExtra("PhotoURL");
        IntentUsername = intent.getStringExtra("Username");
        IntentEmail = intent.getStringExtra("Email");
        mList = (List<RestaurantDrawerItem>) intent.getSerializableExtra("ListBookmark");
        if(user != null)
        {
            IntentUsername = user.getDisplayName();
            userName.setText(user.getDisplayName());
            userEmail.setText(user.getEmail());
        }
        if(IntentEmail != null)
        {
            userEmail.setText(IntentEmail);
        }
        if (IntentUsername != null)
        {
            userName.setText(IntentUsername);
        }
        if (IntentPhotoURL == null)
        {
            Picasso.with(AccountActivity.this)
                    .load(R.drawable.user)
                    .into(userImg);
        }
        else if (IntentPhotoURL.equals(""))
        {
            Picasso.with(AccountActivity.this)
                    .load(R.drawable.user)
                    .into(userImg);
        }
        else if (IntentPhotoURL != null)
        {
            Picasso.with(AccountActivity.this)
                    .load(IntentPhotoURL) //extract as User instance method
                    .transform(new CropCircleTransformation())
                    .resize(100, 100)
                    .into(userImg);
        }
        mAdapter = new RestaurantItemAdapter3(mList,this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        mAdapter.setListener(new RestaurantItemAdapter3.InterfaceItemClick() {
            @Override
            public void onItemClick(RestaurantDrawerItem item) {
                Intent intentResDetail = new Intent(AccountActivity.this,RestaurantDetail.class);
                intentResDetail.putExtra("IntentUserName",IntentUsername);
                intentResDetail.putExtra("IntentUserPhoto",IntentPhotoURL);
                intentResDetail.putExtra("RestaurantDetail", (Serializable) item);
                startActivity(intentResDetail);
            }

            @Override
            public void onDirectClick(RestaurantDrawerItem item) {
                Intent intentResDetail = new Intent(AccountActivity.this,DirectActivity.class);
                intentResDetail.putExtra("IntentUserName",IntentUsername);
                intentResDetail.putExtra("IntentUserPhoto",IntentPhotoURL);
                intentResDetail.putExtra("RestaurantDetail", (Serializable) item);
                startActivity(intentResDetail);
            }
        });
    }
}
