package com.example.aplicacioncitaconmemin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendDialog extends AppCompatDialogFragment {

    private ListView friends;
    private ArrayAdapter<UserInformation> usernames;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialogfriend,null);
        friends = v.findViewById(R.id.friendList);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        fillListView();
        friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserInformation item = usernames.getItem(position);
                String firstName = item.getFirstName();
                String lastName = item.getLastName();
                String location = item.getLocation();
                String age = item.getAge();
                Log.wtf("tag", "Nombre: " + firstName + " Apellido: " + lastName + " Location: " + location + " Edad: " + age);
            }
        });
        builder.setView(v).setTitle("Friends")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Aqui podemos regresar datos del dialog al fragment
            }
        });

        return builder.create();
    }

    private void fillListView(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<UserInformation>> t = new GenericTypeIndicator<List<UserInformation>>() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                };
                List<UserInformation> lista = dataSnapshot.child(user.getUid()).child("Friends").getValue(t);
                if (lista != null){
                    usernames = new ArrayAdapter<UserInformation>(getActivity(), android.R.layout.simple_list_item_1, lista);
                    friends.setAdapter(usernames);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
