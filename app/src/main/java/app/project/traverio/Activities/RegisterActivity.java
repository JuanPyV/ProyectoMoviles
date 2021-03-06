package app.project.traverio.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import app.project.traverio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText username, pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.usernameInicio);
        pass = findViewById(R.id.passInicio);
        mAuth = FirebaseAuth.getInstance();
    }

    public void register(View v){
        createAccount(username.getText().toString(), pass.getText().toString());
    }

    private void createAccount(String email, String password) {
        Toast.makeText(RegisterActivity.this, "Creating account...", Toast.LENGTH_SHORT).show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success");

                    Toast.makeText(RegisterActivity.this, "Created account successfully",
                            Toast.LENGTH_SHORT).show();
                    moveToLogin();
                    //updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegisterActivity.this, "Could register the user",
                            Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            }
        });
    }

    private void moveToLogin(){
        Intent intentito = new Intent(this, MainActivity.class);
        startActivity(intentito);
    }


    public void cerrarRegistro(View v){
        //finish();
        moveToLogin();
    }


}
