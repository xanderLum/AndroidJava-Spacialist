//package com.spacialist.ui.main;
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.FragmentActivity;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.spacialist.R;
//
//public class MapsFragment extends FragmentActivity implements OnMapReadyCallback {
//
//    private int LOCATION_REQUEST_CODE = 100;
//    private double wayLatitude = 0.0, wayLongitude = 0.0;
//    private LatLng userCurrentLocation;
//
//    private GoogleMap mMap;
//    private FusedLocationProviderClient fusedLocationClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        if (ContextCompat.checkSelfPermission(MapsFragment.this,
//                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(MapsFragment.this,
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(MapsFragment.this, "You have already granted this permission!",
//                    Toast.LENGTH_SHORT).show();
//
//        } else {
//            requestStoragePermission();
//        }
//
//        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
//            if (location != null) {
//                wayLatitude = location.getLatitude();
//                wayLongitude = location.getLongitude();
//                userCurrentLocation = new LatLng(wayLatitude, wayLongitude);
//                /*txtLocation.setText(String.format(Locale.US, "%s -- %s", wayLatitude, wayLongitude));*/
//
//                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                        .findFragmentById(R.id.map);
//                mapFragment.getMapAsync(this);
//            }
//        });
//    }
//
//    private void requestStoragePermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION)) {
//
//            new AlertDialog.Builder(this)
//                    .setTitle("Permission needed")
//                    .setMessage("This permission is needed because of this and that")
//                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(MapsFragment.this,
//                                    new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
//                                            Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
//                        }
//                    })
//                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .create().show();
//
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == LOCATION_REQUEST_CODE)  {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mMap.setMinZoomPreference(15.0f);
//        mMap.setMaxZoomPreference(20.0f);
//
//        LatLng userLoc = userCurrentLocation;
////        Marker userLocMarker = new MarkerOptions().position(userLoc).title("User current location marker");
//
//        mMap.addMarker(new MarkerOptions().position(userLoc).title("User current location marker"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLoc));
//
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        builder.include(userLoc);
////        for (Marker marker : markers) {
////            builder.include(marker.getPosition());
////        }
//        LatLngBounds bounds = builder.build();
//
////        int padding = 0; // offset from edges of the map in pixels
////        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 15));
//
////        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
////            @Override
////            public void onMapLoaded() {
////                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
////            }
////        });
//    }
//}
