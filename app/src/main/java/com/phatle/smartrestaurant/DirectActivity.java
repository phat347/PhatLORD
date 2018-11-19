package com.phatle.smartrestaurant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.phatle.smartrestaurant.GoogleMapModel.GoogleMapResponse;
import com.phatle.smartrestaurant.Retrofit2GoogleMap.ApiUtils2;
import com.phatle.smartrestaurant.Retrofit2GoogleMap.SOService2;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashSet;
import java.util.Set;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DirectActivity extends AppCompatActivity implements OnMapReadyCallback{

    public GoogleMap mMap;
    private MapView mapView;
    private FusedLocationProviderClient mFusedLocationClient;
    Set<Target> protectedFromGarbageCollectorTargets = new HashSet<>();
    Location myLocation;

    RestaurantDrawerItem IntentItem;
    private SOService2 mService;
    TextView toolbarTitle,mDuration,mDistance;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct);
        toolbarTitle = findViewById(R.id.toolbar_title);
        backBtn = findViewById(R.id.btn_menu);
        mapView = findViewById(R.id.google_map);
        mDistance = findViewById(R.id.distance_map);
        mDuration = findViewById(R.id.duration_map);
        Intent intent = DirectActivity.this.getIntent();
        IntentItem = (RestaurantDrawerItem) intent.getSerializableExtra("RestaurantDetail");
        toolbarTitle.setText(IntentItem.getName());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        mapView.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        mService = ApiUtils2.getSOService();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapsInitializer.initialize(getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            RxPermissions rxPermissions = new RxPermissions(this);

            rxPermissions.request(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(granted -> {
                if (granted) { // Always true pre-M
                    // I can control the camera now
                    showDirection();
                } else {
                    // Oups permission denied
                    return;
                }
            });

        }
        showDirection();
    }
    public void AddMarker(double lat, double lng,String imgRes,String name)
    {

        LatLng location = new LatLng(lat,lng);
        Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                //Garbage collector làm cho hàm BitmapLoad không được gọi ở lần đầu -> remove garbage collector
                protectedFromGarbageCollectorTargets.remove(this);
                mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                        .title(name)

                );

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.d("picasso", "onBitmapFailed");
                protectedFromGarbageCollectorTargets.remove(this);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        protectedFromGarbageCollectorTargets.add(mTarget);
        Picasso.with(getApplicationContext())
                .load(imgRes)
                .resize(200,200)
                .centerCrop()
                .transform(new CircleBubbleTransformation())

                .into(mTarget);
    }
    public void showDirection()
    {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Logic to handle location object
                    myLocation = location;
//                    Movecamera(mMap, myLocation.getLatitude(),myLocation.getLongitude());
                    mMap.clear();
                    AddMarker(IntentItem.getLat(),IntentItem.getLng(),IntentItem.getImgRes(),IntentItem.getName());
                    String origin = myLocation.getLatitude()+","+myLocation.getLongitude();
                    String destination = IntentItem.getLat()+","+IntentItem.getLng();
                    final SweetAlertDialog pDialog = new SweetAlertDialog(DirectActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    mService.getAnswers(origin,destination,"AIzaSyA8hxps20aOEzJoU4LsrR1ClrbdXuMzj-o","vi")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<GoogleMapResponse>() {
                                @Override
                                public void onCompleted() {
                                    pDialog.dismiss();
                                }

                                @Override
                                public void onError(Throwable e) {

                                    Log.d("Phat","onErrorLoadAPI");
                                    new SweetAlertDialog(DirectActivity.this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("Không thể lấy dữ liệu!")
                                            .setContentText("Kiểm tra kết nối mạng hoặc vui lòng thử lại sau")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismissWithAnimation();
                                                }
                                            })
                                            .show();
                                }

                                @Override
                                public void onNext(GoogleMapResponse googleMapResponse) {
                                    pDialog.dismiss();
                                    Log.d("Phat","onSuccessLoadAPI");
                                    String status = googleMapResponse.getStatus();
                                    Log.d("Phat","   "+status);
                                    String polyline = googleMapResponse.getRoutes().get(0).getOverviewPolyline().getPoints();
                                    Log.d("Phat","   "+ polyline);

                                    PolylineOptions options = new PolylineOptions();
                                    options.color(Color.parseColor("#42b72a"));
                                    options.width(6);
                                    options.addAll(PolyUtil.decode(polyline));
                                    mMap.addPolyline(options);

                                    LatLng Southwest = new LatLng(googleMapResponse.getRoutes().get(0).getBounds().getSouthwest().getLat(),googleMapResponse.getRoutes().get(0).getBounds().getSouthwest().getLng());
                                    LatLng Northeast = new LatLng(googleMapResponse.getRoutes().get(0).getBounds().getNortheast().getLat(),googleMapResponse.getRoutes().get(0).getBounds().getNortheast().getLng());

                                    LatLngBounds.Builder latlngBuilder = new LatLngBounds.Builder();
                                    latlngBuilder.include(Northeast);
                                    latlngBuilder.include(Southwest);
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBuilder.build(), 100));

                                    String distance = googleMapResponse.getRoutes().get(0).getLegs().get(0).getDistance().getText();
                                    String duration = googleMapResponse.getRoutes().get(0).getLegs().get(0).getDuration().getText();

                                    mDistance.setText("  ("+distance+")");
                                    mDuration.setText(duration);

                                }
                            });
                }
            }
        });


    }
    public void Movecamera(GoogleMap googleMap, double lat, double lon) {

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lon))
                .zoom(16)                   // Sets the zoom
                .bearing(0)                // Sets to east
                .tilt(0)                   // Sets to 30 degrees
                .build();                   // Creates a CameraPosition
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        // Need user permisson (above)

    }
}
