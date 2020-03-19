package com.example.aplicacioncitaconmemin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText username, pass;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.pass);



    }
    public void abrirRegistro(View v){
        Intent intentito = new Intent(this, RegisterActivity.class);

        startActivity(intentito);
    }


}
