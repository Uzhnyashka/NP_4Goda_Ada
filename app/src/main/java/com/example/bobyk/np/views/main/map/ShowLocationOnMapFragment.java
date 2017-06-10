package com.example.bobyk.np.views.main.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/11/17.
 */

public class ShowLocationOnMapFragment extends Fragment {

    @Bind(R.id.mapView)
    MapView mMapView;

    private double mLatitude;
    private double mLongitude;

    private GoogleMap googleMap;

    public static ShowLocationOnMapFragment newInstance(Double latitude, Double longitude) {
        Bundle args = new Bundle();
        ShowLocationOnMapFragment fragment = new ShowLocationOnMapFragment();
        fragment.setArguments(args);
        fragment.setLatitude(latitude);
        fragment.setLongitude(longitude);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_on_map, null);

        ButterKnife.bind(this, view);
        mMapView.onCreate(savedInstanceState);
        initMap();

        return view;
    }

    private void initMap() {

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                googleMap.clear();
                LatLng delivery = new LatLng(mLatitude, mLongitude);
                googleMap.addMarker(new MarkerOptions().position(delivery).title("Driver"));

                CameraPosition cameraPosition = new CameraPosition.Builder().target(delivery).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }

    private void setLatitude(Double latitude) {
        mLatitude = latitude.doubleValue();
    }

    private void setLongitude(Double longitude) {
        mLongitude = longitude.doubleValue();
    }

}
