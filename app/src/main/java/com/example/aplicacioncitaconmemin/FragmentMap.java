package com.example.aplicacioncitaconmemin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class FragmentMap extends Fragment implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback {

    GoogleMap map;
    MapView mapView;
    private Marker markerGdl;
    private Marker markerMty;
    private Marker markerCDMX;
    private Marker markerAmeca;
    private Marker markerColima;
    private Marker markerPuebla;
    private Marker markerMazatlan;


       @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = v.findViewById(R.id.mapa);

        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(Objects.requireNonNull(getActivity()));
        map = googleMap;

        LatLng gdl = new LatLng(20.6720375, -103.33893962);
        LatLng mty = new LatLng(25.6802019, -100.3152586);
        LatLng cdmx = new LatLng(19.3205562, -99.1517011);
        LatLng ameca = new LatLng(20.548649, -104.045049);
        LatLng colima = new LatLng(19.2429, -103.720137);
        LatLng puebla = new LatLng(19.0412967, -98.206199599999);
        LatLng mazatlan = new LatLng(23.249414, -106.411140);

        markerGdl = map.addMarker(new MarkerOptions().position(gdl).title("Guadalajara").snippet("Guadalajara, Jalisco").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        markerMty = map.addMarker(new MarkerOptions().position(mty).title("Monterrey").snippet("Monterrey, Nuevo Leon"));
        markerCDMX = map.addMarker(new MarkerOptions().position(cdmx).title("Ciudad de Mexico").snippet("Ciudad de Mexico"));
        markerAmeca = map.addMarker(new MarkerOptions().position(ameca).title("Ameca").snippet("Ameca, Jalisco").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        markerColima = map.addMarker(new MarkerOptions().position(colima).title("Colima").snippet("Colima, Colima").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        markerPuebla = map.addMarker(new MarkerOptions().position(puebla).title("Prueba Puebla").snippet("Puebla"));
        markerMazatlan = map.addMarker(new MarkerOptions().position(mazatlan).title("Prueba Mazatlan").snippet("Mazatlan, Sinaloa"));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cdmx,5));

        map.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if(marker.equals(markerGdl)){
            InfoMapDialog dialogInfoMap = new InfoMapDialog();
            dialogInfoMap.newInstance(marker.getTitle(),marker.getSnippet(),"https://picsum.photos/1280/720?random=1").show(getFragmentManager(), "infoMap dialog");
        }
        else if(marker.equals(markerMty)){
            InfoMapDialog dialogInfoMap = new InfoMapDialog();
            dialogInfoMap.newInstance(marker.getTitle(),marker.getSnippet(),"https://picsum.photos/1280/720?random=2").show(getFragmentManager(), "infoMap dialog");
        }
        else if(marker.equals(markerCDMX)){
            InfoMapDialog dialogInfoMap = new InfoMapDialog();
            dialogInfoMap.newInstance(marker.getTitle(),marker.getSnippet(),"https://picsum.photos/1280/720?random=3").show(getFragmentManager(), "infoMap dialog");
        }
        else if(marker.equals(markerAmeca)){
            InfoMapDialog dialogInfoMap = new InfoMapDialog();
            dialogInfoMap.newInstance(marker.getTitle(),marker.getSnippet(),"https://picsum.photos/1280/720?random=4").show(getFragmentManager(), "infoMap dialog");
        }
        else if(marker.equals(markerColima)){
            InfoMapDialog dialogInfoMap = new InfoMapDialog();
            dialogInfoMap.newInstance(marker.getTitle(),marker.getSnippet(),"https://picsum.photos/1280/720?random=5").show(getFragmentManager(), "infoMap dialog");
        }
        else if(marker.equals(markerPuebla)){
            InfoMapDialog dialogInfoMap = new InfoMapDialog();
            dialogInfoMap.newInstance(marker.getTitle(),marker.getSnippet(),"https://picsum.photos/1280/720?random=6").show(getFragmentManager(), "infoMap dialog");
        }
        else if(marker.equals(markerMazatlan)){
            InfoMapDialog dialogInfoMap = new InfoMapDialog();
            dialogInfoMap.newInstance(marker.getTitle(),marker.getSnippet(),"https://picsum.photos/1280/720?random=7").show(getFragmentManager(), "infoMap dialog");
        }

    }
}
