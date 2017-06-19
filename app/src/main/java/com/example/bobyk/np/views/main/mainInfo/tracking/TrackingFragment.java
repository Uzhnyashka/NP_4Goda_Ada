package com.example.bobyk.np.views.main.mainInfo.tracking;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.bobyk.np.R;
import com.example.bobyk.np.models.main.Point;
import com.example.bobyk.np.models.main.SnappedPoint;
import com.example.bobyk.np.presenters.main.mainInfo.tracking.TrackingPresenter;
import com.example.bobyk.np.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/6/17.
 */

public class TrackingFragment extends Fragment implements TrackingView {

    private TrackingPresenter mPresenter;

    @Bind(R.id.mapView)
    MapView mMapView;
    @Bind(R.id.et_delivery_number)
    EditText mDeliveryNumberEditText;
    boolean ok = false;

    private GoogleMap googleMap;

    public static TrackingFragment newInstance() {
        Bundle args = new Bundle();
        TrackingFragment fragment = new TrackingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new TrackingPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, null);

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

    @OnClick(R.id.btn_find)
    public void onFindClick() {
        googleMap.clear();
        ok = false;
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        mPresenter.findDelivery(mDeliveryNumberEditText.getText().toString());
    }

    @Override
    public void setDeliveryPoint(List<Point> points) {
        mPresenter.loadRoutePoints(points);
    }

    @Override
    public void successLoadRoutePoints(List<List<LatLng>> points) {
        drawRoute(points);
    }

    private void drawRoute(final List<List<LatLng>> points) {
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

//                LatLng delivery = new LatLng(mLatitude, mLongitude);
//                googleMap.addMarker(new MarkerOptions().position(delivery).title(mLabel));
////
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(delivery).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                int k = 0;
                List<Polyline> polylines = new ArrayList<Polyline>();
                for (int i = 0; i < points.size(); i++) {
                    Polyline polyline = googleMap.addPolyline(new PolylineOptions()
                            .width(10)
                            .color(ContextCompat.getColor(getActivity(), R.color.routeColor))
                            .geodesic(true)
                            .zIndex(10));

                    polylines.add(polyline);
                }
                for (List<LatLng> latLngs : points) {
                    k++;
                    if (k == 1) {
                        LatLng delivery = latLngs.get(0);
                        googleMap.addMarker(new MarkerOptions().position(delivery).title("Start"));
                    }

                    polylines.get(k - 1).setPoints(latLngs);

                    if (k == points.size()) {
                        LatLng delivery = latLngs.get(points.size() - 1);
                        googleMap.addMarker(new MarkerOptions().position(latLngs.get(latLngs.size() - 1)).title("Delivery").icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(delivery).zoom(12).build()));
                    }
                }

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                googleMap.setMyLocationEnabled(true);
            }
        });
    }


    @Override
    public void onError() {
        Utils.showToastMessage(getActivity(), "Error find delivery");
    }

    @Override
    public void setRecipientLocation(LatLng point) {
        ok = true;
        googleMap.addMarker(new MarkerOptions().position(point).title("Destination").icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }
}
