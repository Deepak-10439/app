package com.example.gren2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Add_user_details extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uploadedImageUrl;

    EditText first, email, mobile;
    Button input, upload;

    Uri imageUri;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_details);

        first = findViewById(R.id.First);
        email = findViewById(R.id.Email);
        mobile = findViewById(R.id.mobile);

        input = findViewById(R.id.btn_input_image);
        upload = findViewById(R.id.btn_upload);

        input.setOnClickListener(v -> openGallery());

        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        upload.setOnClickListener(v -> {
            uploadImageToFirebase();
            // After uploading the image, upload data to Firestore
            uploadDataToFirestore();
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Now you can do whatever you want with the selected image URI
            Toast.makeText(this, "Image selected: " + imageUri.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImageToFirebase() {
        if (imageUri != null) {
            // Generating a random name for the image file
            String imageName = UUID.randomUUID().toString();
            StorageReference imageRef = storageReference.child("images/" + imageName);

            // Uploading the image to Firebase Storage
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully
                        Toast.makeText(Add_user_details.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        // Get the download URL of the uploaded image
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Store the download URL in the global variable
                            uploadedImageUrl = uri;
                            // You can now save the download URL or perform any other actions with it
                        }).addOnFailureListener(e -> {
                            // Handle any errors getting the download URL
                            Toast.makeText(Add_user_details.this, "Failed to get image download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors that may occur during the upload process
                        Toast.makeText(Add_user_details.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadDataToFirestore() {
        String firstName = first.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userMobile = mobile.getText().toString().trim();

        // Check if any field is empty
        if (firstName.isEmpty() || userEmail.isEmpty() || userMobile.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new user object
        Map<String, Object> user = new HashMap<>();


        // If imageUri is not null, add image URL from Firebase Storage to the user object
        if (uploadedImageUrl != null) {

            user.put("first", firstName);
            user.put("email", userEmail);
            user.put("mobile", userMobile);
            user.put("imageUrl", uploadedImageUrl.toString());

            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(documentReference -> {
                        // DocumentSnapshot added with ID: documentReference.getId()
                        Toast.makeText(Add_user_details.this, "User details added to Firestore", Toast.LENGTH_SHORT).show();
                        // Reset fields after successful upload
                        resetFields();
                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors that may occur during the upload process
                        Toast.makeText(Add_user_details.this, "Failed to add user details to Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
//        else {
//            // If no image is selected, add the user object to Firestore without the imageUrl field
//            uploadUserToFirestore(user);
//        }
    }

    private void resetFields() {
        // Reset EditText fields after successful upload
        first.setText("");
        email.setText("");
        mobile.setText("");
        // Clear the uploaded image URL
        uploadedImageUrl = null;
    }
    private void uploadUserToFirestore(Map<String, Object> user) {
        // Add the user object to Firestore
        db.collection("users").add(user).addOnSuccessListener(documentReference -> {
            // DocumentSnapshot added with ID: documentReference.getId()
            Toast.makeText(Add_user_details.this, "User details added to Firestore", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            // Handle any errors that may occur during the upload process
            Toast.makeText(Add_user_details.this, "Failed to add user details to Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

}

