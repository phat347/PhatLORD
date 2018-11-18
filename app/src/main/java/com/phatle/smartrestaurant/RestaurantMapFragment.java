package com.phatle.smartrestaurant;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMapFragment extends Fragment implements OnMapReadyCallback {

    public GoogleMap mMap;
    private MapView mapView;
    private FusedLocationProviderClient mFusedLocationClient;
    List<LatLng> mListLocation = new ArrayList<>();

    Location myLocation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_googlemap, container, false);
        mapView = view.findViewById(R.id.google_map);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        mapView.getMapAsync(this);
        if (!isGspTurnOn(getContext())) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Dịch vụ định vị tắt")
                    .setContentText("Bạn chưa bật dịch vụ định vị. Vui lòng bật định vị để sử dụng chức năng này")
                    .setConfirmText("Cài đặt")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(viewIntent);
                        }
                    })
                    .setCancelText("Thoát")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapsInitializer.initialize(getContext());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            RxPermissions rxPermissions = new RxPermissions(getActivity());

            rxPermissions.request(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(granted -> {
                if (granted) { // Always true pre-M
                    // I can control the camera now
                    showAllRestaurant();
                } else {
                    // Oups permission denied
                    return;
                }
            });

        }
        showAllRestaurant();
    }

    public void showAllRestaurant() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Logic to handle location object
                    myLocation = location;
                    Movecamera(mMap, myLocation.getLatitude(),myLocation.getLongitude());
                }
            }
        });
        mListLocation.clear();
        mListLocation.add(new LatLng(10.7696543, 106.65701));
        mListLocation.add(new LatLng(10.762503, 106.659343));

        for (int i = 0; i <mListLocation.size() ; i++) {
            AddMarker(mListLocation.get(i).latitude,mListLocation.get(i).longitude);
        }

//        LatLng BkhoaUniversity = new LatLng(10.7696543, 106.65701);
//        Target mTarget1 = new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                mMap.addMarker(new MarkerOptions()
//                        .position(BkhoaUniversity)
//                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
//                        .title("Restaurant 1")
//                );
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//                Log.d("picasso", "onBitmapFailed");
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        };
//        Picasso.with(getContext())
//                .load(R.drawable.img_res1)
//                .resize(200,200)
//                .centerCrop()
//                .transform(new CircleBubbleTransformation())
//                .into(mTarget1);
//
//
//        Movecamera(mMap,10.762503,106.659343);
//
//        LatLng location = new LatLng(10.762503,106.659343);
//
//
//        Target mTarget = new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                mMap.addMarker(new MarkerOptions()
//                        .position(location)
//                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
//                        .title("Restaurant 1")
//                );
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//                Log.d("picasso", "onBitmapFailed");
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        };
//        Picasso.with(getContext())
//                .load(R.drawable.img_res1)
//                .resize(200,200)
//                .centerCrop()
//                .transform(new CircleBubbleTransformation())
//                .into(mTarget);
    }

    public void AddMarker(double lat, double lng)
    {
        LatLng location = new LatLng(lat,lng);
        Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                        .title("Restaurant 1")
                );
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.d("picasso", "onBitmapFailed");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.with(getContext())
                .load(R.drawable.img_res1)
                .resize(200,200)
                .centerCrop()
                .transform(new CircleBubbleTransformation())
                .into(mTarget);
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

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lon))
        );
    }
    public static boolean isGspTurnOn(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        return gps_enabled;
    }
}
