package com.example.aplicacioncitaconmemin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, pass;
    Button login;
    private UserDB userDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.pass);
        userDB = new UserDB(this);


    }
    public void abrirRegistro(View v){
        Intent intentito = new Intent(this, RegisterActivity.class);
        startActivity(intentito);
    }

    public void login(View v){
        boolean intento = userDB.tryLogin(username.getText() + "", pass.getText() + "");
        if (intento){
            Toast.makeText(this, "Iniciado de sesión correcto", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Iniciado de sesión incorrecto", Toast.LENGTH_SHORT).show();
        }
    }


}
