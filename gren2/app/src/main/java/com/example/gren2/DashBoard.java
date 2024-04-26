package com.example.gren2;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gren2.Add_user_details;
import com.example.gren2.R;


public class DashBoard extends AppCompatActivity {

    private Button userDetailsButton;
    private Button sendDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        // Initialize buttons
        userDetailsButton = findViewById(R.id.user_details);
        sendDataButton = findViewById(R.id.send_data);

        // Set click listeners for buttons
        userDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBoard.this, Add_user_details.class);
                startActivity(i);
            }
        });

        sendDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBoard.this, send_images.class);
                startActivity(i);
            }
        });
    }

    // Method to handle "Add user details" button click
    private void addUserDetails() {
        // Implement your logic for adding user details here
    }

    // Method to handle "Send images to the users" button click
    private void sendImagesToUsers() {
        // Implement your logic for sending images to users here
    }
}
