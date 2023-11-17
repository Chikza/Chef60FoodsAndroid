package com.example.chef60foods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReservationViewActivity extends AppCompatActivity {
    String name, email, phone, address, numberOfPersons;
    String reservationDate, reservationTime, numberOfTables, reserverName;
    Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_view);

        Intent i = getIntent();
        User user = (User) getIntent().getSerializableExtra("user");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reservationRef = database.getReference("reservations");

        reserverName = i.getStringExtra("reserverName");
        numberOfPersons = i.getStringExtra("numberOfPersons");
        numberOfTables = i.getStringExtra("num");
        reservationDate = i.getStringExtra("reservation");
        reservationTime = i.getStringExtra("time");

        btnCancel= findViewById(R.id.btnCancel);
        TextView textView = findViewById(R.id.tv3);

        textView.setText(
                "Reserver's Name: " + reserverName + "\n\nNumber Of Persons: " + numberOfPersons + "\n\nNumber Of Tables: " + numberOfTables + "\n\nReservation Date: " + reservationDate + "\n\nReservation Time: " + reservationTime
        );
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reservationRef.child(reserverName + "_" + reservationDate + "_" + reservationTime).removeValue();

                Toast.makeText(ReservationViewActivity.this, "Your reservation has been cancelled: You will only receive 80% of your money back", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}