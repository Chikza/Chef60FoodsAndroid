package com.example.chef60foods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CardTakeIn extends AppCompatActivity {

    EditText txtHolderName, txtCardNumber, txtSecurityCode, txtExpirationDate;
    Button btnPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_take_in);

        txtHolderName = findViewById(R.id.txtHolderName);
        txtCardNumber = findViewById(R.id.txtCardNumber);
        txtSecurityCode = findViewById(R.id.txtSecurityCode);
        txtExpirationDate = findViewById(R.id.txtExpirationDate);
        btnPayment = findViewById(R.id.btnPayment);

        txtCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (count > 0 && (count % 5 == 0)) {
                    txtCardNumber.append(" ");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtSecurityCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String securityCode = charSequence.toString();

                if (!isValidSecurityCode(securityCode)) {
                    txtSecurityCode.setError("Invalid security code");
                } else {
                    txtSecurityCode.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CardTakeIn.this, PaymentCheckoutActivity.class);
                startActivity(i);
            }
        });
    }

    private boolean isValidSecurityCode(String securityCode) {
        return securityCode.length() == 3 || securityCode.length() == 4 && securityCode.matches("\\d+");
    }
}