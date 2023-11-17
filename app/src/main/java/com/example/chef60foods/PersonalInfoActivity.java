package com.example.chef60foods;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonalInfoActivity extends AppCompatActivity {

    EditText txtPersonName, txtEmail, txtPhoneNumber, txtHomeAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        txtPersonName = findViewById(R.id.txtPersonName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtHomeAddress = findViewById(R.id.txtHomeAddress);
        Button btnSave = findViewById(R.id.btnSave);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtPersonName.getText().toString();
                String email = txtEmail.getText().toString();
                String phone = txtPhoneNumber.getText().toString();
                String address = txtHomeAddress.getText().toString();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    Toast.makeText(PersonalInfoActivity.this, "Please Enter All the Fields", Toast.LENGTH_SHORT).show();
                } else if (phone.length() != 10) {
                    Toast.makeText(PersonalInfoActivity.this, "Phone number must be 10 digits", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the email or phone number already exists
                    checkEmailAndPhoneExistence(email, phone, new OnCheckExistenceListener() {
                        @Override
                        public void onExistenceChecked(boolean emailExists, boolean phoneExists) {
                            if (emailExists) {
                                Toast.makeText(PersonalInfoActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                            } else if (phoneExists) {
                                Toast.makeText(PersonalInfoActivity.this, "Phone number already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                // Email and phone number do not exist, proceed with adding the user
                                User user = new User(name, email, phone, address);

                                DatabaseReference newUserRef = usersRef.push();
                                newUserRef.setValue(user);

                                Intent i = new Intent(PersonalInfoActivity.this, TableReservationsActivity.class);
                                i.putExtra("user", user);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onError(DatabaseError databaseError) {
                            // Handle error during the existence check
                            Toast.makeText(PersonalInfoActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    // Helper method to check if email and phone number already exist
    private void checkEmailAndPhoneExistence(String email, String phone, OnCheckExistenceListener listener) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    boolean emailExists = dataSnapshot.exists();

                    usersRef.orderByChild("phone").equalTo(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                boolean phoneExists = dataSnapshot.exists();

                                // Notify the listener with the existence status
                                listener.onExistenceChecked(emailExists, phoneExists);
                            } catch (Exception e) {
                                // Handle error during the phone existence check
                                listener.onError(DatabaseError.fromException(e));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle error during the phone existence check
                            listener.onError(databaseError);
                        }
                    });
                } catch (Exception e) {
                    // Handle error during the email existence check
                    listener.onError(DatabaseError.fromException(e));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error during the email existence check
                listener.onError(databaseError);
            }
        });
    }

    private interface OnCheckExistenceListener {
        void onExistenceChecked(boolean emailExists, boolean phoneExists);

        void onError(DatabaseError databaseError);
    }
}