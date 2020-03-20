package com.example.aplicacioncitaconmemin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText username, pass;
    private UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.pass);
        userDB = new UserDB(this);
    }

    public void register(View v){
        boolean register = userDB.registerUser(username.getText() + "", pass.getText() + "", "555555555");
        if (register){
            Toast.makeText(this, "Registro completado", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Ese usuario ya ha sido escogido, selecciona otro porfavor", Toast.LENGTH_SHORT).show();
        }

    }

    public void resetDB(View v){
        userDB.resetDB();
    }

    public void cerrarRegistro(View v){
        finish();
    }


}
