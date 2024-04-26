package com.example.gren2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button signUpButton, login;
    private EditText emailEt, passEt;

    //Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });


        login = findViewById(R.id.login_button);
        emailEt = findViewById(R.id.email);
        passEt = findViewById(R.id.password);


        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(v -> loginEmailPassUser(emailEt.getText().toString().trim(), passEt.getText().toString().trim()));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loginEmailPassUser(String email, String pwd){

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)){

            firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    Intent i = new Intent(MainActivity.this, DashBoard.class);
                    startActivity(i);
                }
            });
        }
    }
}