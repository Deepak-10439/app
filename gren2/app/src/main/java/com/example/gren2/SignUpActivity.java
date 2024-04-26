package com.example.gren2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    //widgets
    EditText firstName, lastName, email, create_password;
    Button create;

    //Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    //Firebase Connection

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.First_Name);
        lastName = findViewById(R.id.Last_Name);
        email = findViewById(R.id.Email_Mobile);
        create_password = findViewById(R.id.create_password);

        // Find the create button by its ID
        create = findViewById(R.id.create);

        firebaseAuth = FirebaseAuth.getInstance();

        // Set OnClickListener for the create button
        create.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(email.getText().toString())
                    && !TextUtils.isEmpty(firstName.getText().toString())
                    && !TextUtils.isEmpty(lastName.getText().toString())
                    && !TextUtils.isEmpty(create_password.getText().toString())) {

                String first_name = firstName.getText().toString().trim();
                String last_name = lastName.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String pass = create_password.getText().toString().trim();

                CreateUserEmailAccount(first_name, last_name, Email, pass);
                Intent i = new Intent(SignUpActivity.this, DashBoard.class);
                startActivity(i);
            } else {
                Toast.makeText(SignUpActivity.this, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void CreateUserEmailAccount
            (String first_name,
             String last_name,
             String email,
             String pass){
        if (!TextUtils.isEmpty(first_name)
            && !TextUtils.isEmpty(last_name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            firebaseAuth.createUserWithEmailAndPassword(
                    email, pass
            ).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Account is created successfully",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}