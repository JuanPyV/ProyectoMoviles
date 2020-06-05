package com.example.aplicacioncitaconmemin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText username, pass;
    Button login;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.usernameInicio);
        pass = findViewById(R.id.passInicio);
        if (username == null || pass == null){
            Log.wtf("a", "e we, es null");
        }
        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void abrirRegistro(View v){
        Intent intentito = new Intent(this, RegisterActivity.class);
        startActivity(intentito);
    }

    public void prueba(View v){
        Intent intentito = new Intent(this, Home.class);
        startActivity(intentito);
    }

    public void login(View v){
        Toast.makeText(MainActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();
        signIn(username.getText().toString(), pass.getText().toString());
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(MainActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    moveToHome();
                    //updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(MainActivity.this, "User or password incorrect.",
                            Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            }
        });
    }

    private void moveToHome(){
        Intent intentito = new Intent(this, Home.class);
        startActivity(intentito);
    }


}
