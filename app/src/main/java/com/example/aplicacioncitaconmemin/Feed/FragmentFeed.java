package com.example.aplicacioncitaconmemin.Feed;

import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplicacioncitaconmemin.R;
import com.example.aplicacioncitaconmemin.UserInformation;
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

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class FragmentFeed extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    AdapterFeed adapterFeed;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private Button btnPost;
    private EditText statusUpdateF, imageURL;

    ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction == ItemTouchHelper.LEFT){
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = database.getReference();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<List<ModelFeed>> t = new GenericTypeIndicator<List<ModelFeed>>() {
                            @Override
                            public int hashCode() {
                                return super.hashCode();
                            }
                        };
                        List<ModelFeed> lista = dataSnapshot.child("Posts").getValue(t);
                        try{
                            if (user.getUid().equals(lista.get(lista.size() - 1 - viewHolder.getAdapterPosition()).getUID())){
                                lista.remove(lista.size() - 1 - viewHolder.getAdapterPosition());
                                Toast.makeText(getActivity(), "Mensaje eliminado", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(getActivity(), "No puedes eliminar un mensaje que no es tuyo", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        databaseReference.child("Posts").setValue(lista);
                        populateRecyclerView();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //modelFeedArrayList.remove(viewHolder.getAdapterPosition());
                //adapterFeed.notifyDataSetChanged();

            }else if(direction == ItemTouchHelper.RIGHT){
                Log.wtf("edit", "EDITAR TEXTITO");
                adapterFeed.notifyDataSetChanged();

            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.RED)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_sweep_black_24dp)
                    .addSwipeLeftLabel("Borrar")
                    .addSwipeRightBackgroundColor(Color.GREEN)
                    .addSwipeRightActionIcon(R.drawable.ic_edit_black_24dp)
                    .addSwipeRightLabel("Editar")
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        btnPost = v.findViewById(R.id.btnPost);
        statusUpdateF = v.findViewById(R.id.statusUpdateF);
        imageURL= v.findViewById(R.id.imageURL);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPost();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        populateRecyclerView();

        return v;
    }

    public void addNewPost(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation userData = dataSnapshot.child("Users").child(user.getUid()).child("UserInformation").getValue(UserInformation.class);
                java.util.Date date = new java.util.Date();
                ModelFeed newPost = new ModelFeed(userData.getFirstName() + userData.getLastName() , statusUpdateF.getText().toString(), date.toString(),
                        R.drawable.batman, imageURL.getText().toString(), user.getUid());
                GenericTypeIndicator<List<ModelFeed>> t = new GenericTypeIndicator<List<ModelFeed>>() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                };
                List<ModelFeed> lista = dataSnapshot.child("Posts").getValue(t);
                if (lista == null){
                    lista = new ArrayList<>();
                    lista.add(newPost);
                } else{
                    lista.add(newPost);
                }
                databaseReference.child("Posts").setValue(lista);
                populateRecyclerView();
                statusUpdateF.setText("");
                imageURL.setText("");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void populateRecyclerView() {
        modelFeedArrayList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Posts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<ModelFeed>> t = new GenericTypeIndicator<List<ModelFeed>>() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                };
                List<ModelFeed> lista = dataSnapshot.getValue(t);
                if (lista != null){
                    for (int i = lista.size() - 1; i >= 0; i--){
                        ModelFeed newItem = lista.get(i);
                        modelFeedArrayList.add(newItem);
                    }
                    //adapterFeed.notifyDataSetChanged();
                    adapterFeed = new AdapterFeed(getActivity(), modelFeedArrayList);
                    recyclerView.setAdapter(adapterFeed);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*
        java.util.Date date=new java.util.Date();
        ModelFeed modelFeed = new ModelFeed("Sajin Maharjan","The cars we drive say a lot about us." , date.toString(),
                R.drawable.batman, R.drawable.img_post1);

        modelFeedArrayList.add(modelFeed);
        modelFeed = new ModelFeed("Karun Shrestha", "Don't be afraid of your fears. They're not there to scare you. They're there to \n" +
                "let you know that something is worth it.", date.toString(), R.drawable.joseph_stalin, 0);

        modelFeedArrayList.add(modelFeed);
        modelFeed = new ModelFeed("Lakshya Ram", "That reflection!!!", date.toString(),
                R.drawable.charlie_chaplin, R.drawable.img_post2);
        modelFeedArrayList.add(modelFeed);
        adapterFeed.notifyDataSetChanged();
        */
    }
}
