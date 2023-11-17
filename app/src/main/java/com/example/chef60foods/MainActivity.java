package com.example.chef60foods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnReserveNow;
    Button btnReserveNow2;
    Button btnReserveNow3;
    Button btnReserveNow4;
    Button btnReserveNow5;
    Button btnReserveNow6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnReserveNow = findViewById(R.id.btnReserveNow);
        btnReserveNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
                i.putExtra("info", "Burger");
                startActivity(i);
            }
        });

        btnReserveNow2 = findViewById(R.id.btnReserveNow2);
        btnReserveNow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
                i.putExtra("info", "Fried Chicken");
                startActivity(i);
            }
        });

        btnReserveNow3 = findViewById(R.id.btnReserveNow3);
        btnReserveNow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
                i.putExtra("info", "Pizza");
                startActivity(i);
            }
        });

        btnReserveNow4 = findViewById(R.id.btnReserveNow4);
        btnReserveNow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
                i.putExtra("info", "Dagwood Sandwiches");
                startActivity(i);
            }
        });

        btnReserveNow5 = findViewById(R.id.btnReserveNow5);
        btnReserveNow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
                i.putExtra("info", "Desserts");
                startActivity(i);
            }
        });

        btnReserveNow6 = findViewById(R.id.btnReserveNow6);
        btnReserveNow6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
                i.putExtra("info", "Beverages");
                startActivity(i);
            }
        });

    }
}