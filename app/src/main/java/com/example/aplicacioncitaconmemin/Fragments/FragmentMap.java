package com.example.aplicacioncitaconmemin.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.aplicacioncitaconmemin.Dialogs.InfoMapDialog;
import com.example.aplicacioncitaconmemin.Dialogs.InfoPlaceDialog;
import com.example.aplicacioncitaconmemin.AuxiliaryClasses.MarkerPlaceInformation;
import com.example.aplicacioncitaconmemin.AuxiliaryClasses.PlaceInformation;
import com.example.aplicacioncitaconmemin.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentMap extends Fragment implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback {

    GoogleMap map;
    MapView mapView;
    private ArrayList<MarkerPlaceInformation> markers;
    private LocationRequest locationRequest;

    private long UPDATE = 10 * 1000;
    private long FASTEST = 2 * 1000;
    /*
    private Marker markerGdl;
    private Marker markerMty;
    private Marker markerCDMX;
    private Marker markerAmeca;
    private Marker markerColima;
    private Marker markerPuebla;
    private Marker markerMazatlan;
    */


       @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = v.findViewById(R.id.mapa);
        markers = new ArrayList<>();
        //loadIntoDatabaseDummy(); solo ejecutar si la base de datos esta vacia
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        FloatingActionButton fab = v.findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                InfoPlaceDialog place = new InfoPlaceDialog();
                place.show(getFragmentManager(), "place");
            }
        });
        return v;

    }

    public void loadIntoDatabaseDummy(){
           //para poder cargar un dato al menos a la base de datos, cuando no exista ningun otro
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Places");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<PlaceInformation> lista = new ArrayList<>();
                lista.add(new PlaceInformation(
                        20.6720375,
                        -103.33893962,
                        "Guadalajara",
                        "https://picsum.photos/1280/720?random=1",
                        "Ciudad de la Torta Ahogada",
                        "Guadalajara, Jalisco"));
                dataSnapshot.getRef().setValue(lista);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void grantLocation(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, 0);
        } else{
            map.setMyLocationEnabled(true);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] p, int[] r){
        //solo estamos checando el unico permiso que pedimos y tenemos
        if (requestCode == 0 && r[0] == PackageManager.PERMISSION_GRANTED){
            map.setMyLocationEnabled(true);
        }
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(Objects.requireNonNull(getActivity()));
        map = googleMap;
        grantLocation();

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationServices.getFusedLocationProviderClient(getActivity()).requestLocationUpdates(locationRequest, new LocationCallback(){
            public void onLocationResult(LocationResult locationResult){
                LatLng place = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 20));
            }
        }, Looper.myLooper());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Places");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<PlaceInformation>> t = new GenericTypeIndicator<List<PlaceInformation>>() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                };
                List<PlaceInformation> lugares = dataSnapshot.getValue(t);
                if (lugares != null){
                    for (int i = 0; i < lugares.size(); i++){
                        LatLng place = new LatLng(lugares.get(i).getLatitude(), lugares.get(i).getLongitude());
                        Marker placeMarker = map.addMarker(new MarkerOptions()
                                .position(place)
                                .title(lugares.get(i).getTitle())
                                .snippet(lugares.get(i).getSnippet()));
                        MarkerPlaceInformation markerPlaceInformation = new MarkerPlaceInformation(placeMarker, lugares.get(i));
                        markers.add(markerPlaceInformation);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*
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
        */
        map.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
           for (int i = 0; i < markers.size(); i++){
               if (markers.get(i).getMarker().equals(marker)){
                   InfoMapDialog dialogInfoMap = new InfoMapDialog();
                   PlaceInformation placeInformation = markers.get(i).getPlaceInformation();
                   double average = 0;
                   if (placeInformation.getPersonRatings() != null){
                        for (int j = 0; j < placeInformation.getPersonRatings().size(); j++){
                            average += placeInformation.getPersonRatings().get(j).getRating();
                        }
                        average /= placeInformation.getPersonRatings().size();
                   }
                   Log.wtf("calificacion", "el rating es de: " + average);
                   dialogInfoMap.newInstance(
                           placeInformation.getTitle(),
                           placeInformation.getSnippet(),
                           average,
                           placeInformation.getImageLink(),
                           placeInformation.getLatitude(),
                           placeInformation.getLongitude()).show(getFragmentManager(), "infoMap dialog");
                   break;
               }
           }
        /*if(marker.equals(markerGdl)){
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
        */
    }

}
