package com.example.chef60foods;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TableReservationsActivity extends AppCompatActivity {

    Calendar myCalendar;
    EditText editText, chooseTime, txtTables, txtPersons, txtReserverName;
    String name, email, phone, address;
    Button btnSubmit;
    DatabaseReference reservationsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_reservations);

        User user = (User) getIntent().getSerializableExtra("user");

        btnSubmit = findViewById(R.id.btnSubmit);
        txtReserverName = findViewById(R.id.txtReserverName);
        txtPersons = findViewById(R.id.txtPersons);
        txtTables = findViewById(R.id.txtTables);
        editText = (EditText) findViewById(R.id.txtReservation);
        EditText chooseTime = findViewById(R.id.etChooseTime);
        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TableReservationsActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(TableReservationsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String formattedTime = String.format(Locale.US, "%02d:%02d", hourOfDay, minutes);
                        chooseTime.setText(formattedTime);
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });

        reservationsRef = FirebaseDatabase.getInstance().getReference("reservations");


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateDateTime()) {
                    int numberOfPersons;
                    int numTables;

                    try {
                        numberOfPersons = Integer.parseInt(txtPersons.getText().toString());
                        numTables = Integer.parseInt(txtTables.getText().toString());

                        if (numberOfPersons > 10) {
                            Toast.makeText(TableReservationsActivity.this, "Number of persons cannot exceed 10.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (numTables > 2) {
                            Toast.makeText(TableReservationsActivity.this, "Number of tables cannot exceed 2.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    } catch (NumberFormatException e) {
                        Toast.makeText(TableReservationsActivity.this, "Please enter valid numbers for persons and tables.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent i = new Intent(TableReservationsActivity.this, FinalActivity.class);
                    i.putExtra("name", name);
                    i.putExtra("email", email);
                    i.putExtra("phone", phone);
                    i.putExtra("address", address);
                    i.putExtra("reserverName", txtReserverName.getText().toString());
                    i.putExtra("numberOfPersons", txtPersons.getText().toString());
                    i.putExtra("num", txtTables.getText().toString());
                    i.putExtra("reservation", editText.getText().toString());
                    i.putExtra("time", chooseTime.getText().toString());
                    startActivity(i);
                }
            }
        });
    }

    private boolean validateDateTime() {
        Calendar currentDate = Calendar.getInstance();
        if (myCalendar.before(currentDate)) {
            Toast.makeText(TableReservationsActivity.this, "Please choose a date from today onwards.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void updateLabel(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(dateFormat.format(myCalendar.getTime()));
    }
}


