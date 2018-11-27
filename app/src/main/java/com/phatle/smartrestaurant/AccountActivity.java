package com.phatle.smartrestaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.ExifInterface;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.storage.UploadTask;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    TextView toolbarTitle,userName,userEmail;
    ImageView backBtn,userImg,btnEit,btnSave;
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
        btnEit = findViewById(R.id.btn_edit);
        btnSave = findViewById(R.id.btn_save);
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
            if(user.getPhotoUrl()!= null)
            {
                IntentPhotoURL = user.getPhotoUrl().toString();
            }
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
//                    .resize(100, 100)
                    .into(userImg);
        }
        btnEit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user != null){
                    Intent intentChoseImg = new Intent();
                    intentChoseImg.setType("image/*");
                    intentChoseImg.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intentChoseImg,"Select Picture"),69);
                }
                else {
                    ErrorDialog dialog = new ErrorDialog(AccountActivity.this, "Lỗi", "App chưa hỗ trợ tính năng này với tài khoản Facebook hoặc Google",R.drawable.ic_warning);
                    dialog.setupOkButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(true);
                }


            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user != null){
                    ErrorDialog dialog = new ErrorDialog(AccountActivity.this, "Chưa chọn hình ảnh", "Vui lòng chọn hình ảnh",R.drawable.ic_warning);
                    dialog.setupOkButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(true);
                }
                else {
                    ErrorDialog dialog = new ErrorDialog(AccountActivity.this, "Lỗi", "App chưa hỗ trợ tính năng này với tài khoản Facebook hoặc Google",R.drawable.ic_warning);
                    dialog.setupOkButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(true);
                }


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
            String x = uri.toString();
            int y = getCameraPhotoOrientation(this,uri);
            Picasso.with(AccountActivity.this)
                    .load(uri) //extract as User instance method
                    .transform(new CropCircleTransformation())
                    .into(userImg);
            if(user != null)
            {
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final SweetAlertDialog pDialog = new SweetAlertDialog(AccountActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("Loading");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        String userId = user.getUid();
                        StorageReference filepath = mStorage.child("Images");
                        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        IntentPhotoURL = uri.toString();
                                        pDialog.dismiss();
//                                Picasso.with(AccountActivity.this)
//                                        .load(IntentPhotoURL) //extract as User instance method
//                                        .transform(new CropCircleTransformation())
////                                        .resize(100, 100)
//                                        .into(userImg);
                                        Log.i("Phat",uri.toString());
                                        Toast bread = Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_SHORT);
                                        bread.show();
                                        Intent data = new Intent();
                                        data.putExtra("IntentPhotoURL",IntentPhotoURL);
                                        setResult(Activity.RESULT_OK,data);
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setPhotoUri(uri)
                                                .build();

                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast bread = Toast.makeText(getApplicationContext(),"UserProfileUdated",Toast.LENGTH_SHORT);
                                                            bread.show();
                                                        }
                                                    }
                                                });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pDialog.dismiss();
                                        ErrorDialog dialog = new ErrorDialog(AccountActivity.this, "Upload lỗi", "Vui lòng kiểm tra kết nối Internet của bạn và thử lại",R.drawable.ic_warning);
                                        dialog.setupOkButton("OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.show();
                                        dialog.setCanceledOnTouchOutside(false);

                                    }
                                });
//                        Log.i("uploaded", mDatabase.getReference("Users").child(user.getUid()).child("image").setValue(uri).toString());
//                        mDatabase.getReference("Users").child(user.getUid()).child("image").setValue(uri).toString();

                            }
                        });
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
    public static int getCameraPhotoOrientation(@NonNull Context context, Uri imageUri) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            ExifInterface exif = new ExifInterface(
                    imageUri.getPath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case 0:
                    rotate = 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = -270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = -180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }
}
