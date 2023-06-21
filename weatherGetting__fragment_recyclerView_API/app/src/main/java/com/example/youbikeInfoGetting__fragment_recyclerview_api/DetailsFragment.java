package com.example.youbikeInfoGetting__fragment_recyclerview_api;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";
    private static final String ARG_BICYCLE_INFO = "bicycle-info";

    // TODO: Rename and change types of parameters
    private int mPosition;
    private String mBicycleInfo;
    private MapView mapView;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pos Parameter 1.
     * @param bicycleInfo Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(int pos, String bicycleInfo) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, pos);
        args.putString(ARG_BICYCLE_INFO, bicycleInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_POSITION);
            mBicycleInfo = getArguments().getString(ARG_BICYCLE_INFO);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        TextView txt = rootView.findViewById(R.id.detailView);
        mapView = rootView.findViewById(R.id.mapView);
        if(mapView != null){
            System.out.println("not null");
            mapView.getMapAsync(this::onMapReady);
            mapView.onCreate(savedInstanceState);
        }

        try {
            JSONArray infoArray = new JSONArray(mBicycleInfo);
            txt.setText("Detail Information：\n" +
                    "sno：" + infoArray.getJSONObject(mPosition).getString("sno") + "\n" +
                    "sna：" + infoArray.getJSONObject(mPosition).getString("sna") + "\n" +
                    "tot：" + infoArray.getJSONObject(mPosition).getString("tot") + "\n" +
                    "sbi：" + infoArray.getJSONObject(mPosition).getString("sbi") + "\n" +
                    "sarea：" + infoArray.getJSONObject(mPosition).getString("sarea") + "\n" +
                    "mday：" + infoArray.getJSONObject(mPosition).getString("mday") + "\n" +
                    "ar：" + infoArray.getJSONObject(mPosition).getString("ar") + "\n" +
                    "sareaen：" + infoArray.getJSONObject(mPosition).getString("sareaen") + "\n" +
                    "snaen：" + infoArray.getJSONObject(mPosition).getString("snaen") + "\n" +
                    "bemp：" + infoArray.getJSONObject(mPosition).getString("bemp") + "\n" +
                    "act：" + infoArray.getJSONObject(mPosition).getString("act") + "\n" );

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return rootView;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        double lat = 0, lng = 0;

        try {
            JSONArray mJsonArray = new JSONArray(mBicycleInfo);
            lat = mJsonArray.getJSONObject(mPosition).getDouble("lat");
            lng = mJsonArray.getJSONObject(mPosition).getDouble("lng");

            System.out.println(String.valueOf(lat)+"; "+String.valueOf(lng));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        LatLng latLng = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Mark"));
        CameraUpdateFactory.newLatLngZoom(latLng, 2);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}