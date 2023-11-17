package com.example.chef60foods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FinalActivity extends AppCompatActivity {

    String name, email, phone, address, numberOfPersons;
    String reservationDate, reservationTime, numberOfTables, reserverName;
    Button btnPayNow, btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        TextView textView = findViewById(R.id.tv3);
        btnPayNow = findViewById(R.id.btnPayNow);
        btnEdit = findViewById(R.id.btnEdit);
        Intent i = getIntent();
        User user = (User) getIntent().getSerializableExtra("user");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reservationRef = database.getReference("reservations");

        reserverName = i.getStringExtra("reserverName");
        numberOfPersons = i.getStringExtra("numberOfPersons");
        numberOfTables = i.getStringExtra("num");
        reservationDate = i.getStringExtra("reservation");
        reservationTime = i.getStringExtra("time");

        textView.setText(
                "Reserver's Name: " + reserverName + "\n\nNumber Of Persons: " + numberOfPersons + "\n\nNumber Of Tables: " + numberOfTables + "\n\nReservation Date: " + reservationDate + "\n\nReservation Time: " + reservationTime
        );

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FinalActivity.this, TableReservationsActivity.class);
                startActivity(i);
            }
        });


        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Reservation reservation = new Reservation(reserverName, numberOfPersons, numberOfTables, reservationDate, reservationTime);

                DatabaseReference newReservationRef = reservationRef.push();
                newReservationRef.setValue(reservation);

                Intent i = new Intent(FinalActivity.this, PaymentCheckoutActivity.class);
                startActivity(i);
            }
        });

    }
}