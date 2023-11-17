package com.example.chef60foods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button signUpButton = findViewById(R.id.sign_up_btn);
        EditText usernameEditText = findViewById(R.id.username_sign_up);
        EditText passwordEditText = findViewById(R.id.password_sign_up);

        auth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username and password are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new user in Firebase Authentication
            auth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "Account registration successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                // If sign up fails, display a message to the user.
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthUserCollisionException e) {
                                    Toast.makeText(RegistrationActivity.this, "User with this email already exists", Toast.LENGTH_SHORT).show();
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    Toast.makeText(RegistrationActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(RegistrationActivity.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        });

        TextView loginBtn = findViewById(R.id.login_text_btn);
        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}