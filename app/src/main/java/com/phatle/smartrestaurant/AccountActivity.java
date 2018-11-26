package com.phatle.smartrestaurant;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.storage.UploadTask;
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
    FirebaseUser user;
    private List<RestaurantDrawerItem> mList = new ArrayList<>();

    private StorageReference mStorage;
    private FirebaseDatabase mDatabase;
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
        user = auth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
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
        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentChoseImg = new Intent();
                intentChoseImg.setType("image/*");
                intentChoseImg.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentChoseImg,"Select Picture"),69);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==69&&resultCode==RESULT_OK){
            Uri uri=data.getData();
            Picasso.with(AccountActivity.this)
                    .load(uri) //extract as User instance method
                    .transform(new CropCircleTransformation())
                    .resize(100, 100)
                    .into(userImg);
            if(user != null)
            {
                String userId = user.getUid();
                StorageReference filepath = mStorage.child("Images").child(uri.getLastPathSegment());
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                IntentPhotoURL = uri.toString();
//                                Picasso.with(AccountActivity.this)
//                                        .load(IntentPhotoURL) //extract as User instance method
//                                        .transform(new CropCircleTransformation())
//                                        .resize(100, 100)
//                                        .into(userImg);
                                Log.i("Phat",uri.toString());
                                Toast bread = Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_SHORT);
                                bread.show();
                            }
                        });
//                        Log.i("uploaded", mDatabase.getReference("Users").child(user.getUid()).child("image").setValue(uri).toString());
//                        mDatabase.getReference("Users").child(user.getUid()).child("image").setValue(uri).toString();

                    }
                });
            }
//            StorageReference filepath = mStorage.child("Images").child(uri.getLastPathSegment());
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
//                    newStudent.child("image").setValue(downloadUrl);
//                }
//            });
        }
    }
}
